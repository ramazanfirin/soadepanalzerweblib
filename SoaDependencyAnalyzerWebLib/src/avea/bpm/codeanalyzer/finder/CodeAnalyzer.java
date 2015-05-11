package avea.bpm.codeanalyzer.finder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import avea.bpm.codeanalyzer.clazzloader.JarClassLoader;
import avea.bpm.codeanalyzer.finder.cmd.AbstractCommand;
import avea.bpm.codeanalyzer.finder.cmd.Command;
import avea.bpm.codeanalyzer.jtag.JDoc;
import avea.bpm.codeanalyzer.jtag.JTag;
import avea.bpm.codeanalyzer.jtag.JTagParseException;
import avea.bpm.codeanalyzer.logger.AnalyzerLogger;
import avea.bpm.codeanalyzer.model.BCtrlMethod;
import avea.bpm.codeanalyzer.model.BusinessControl;
import avea.bpm.codeanalyzer.model.CCtrlMethod;
import avea.bpm.codeanalyzer.model.CoreControl;
import avea.bpm.codeanalyzer.model.Process;
import avea.bpm.codeanalyzer.model.ProcessInfo;
import avea.bpm.codeanalyzer.model.ServiceInfo;
import avea.bpm.codeanalyzer.model.base.Control;
import avea.bpm.codeanalyzer.model.base.Method;
import avea.bpm.codeanalyzer.model.factory.ProcessFactory;
import avea.bpm.codeanalyzer.model.factory.ProcessInfoFactory;
import avea.bpm.codeanalyzer.model.factory.ServiceInfoFactory;
import avea.bpm.codeanalyzer.model.factory.control.BusinessControlFactory;
import avea.bpm.codeanalyzer.model.factory.control.ControlFactory;
import avea.bpm.codeanalyzer.model.factory.control.CoreControlFactory;
import avea.bpm.codeanalyzer.model.factory.method.BCtrlMethodFactory;
import avea.bpm.codeanalyzer.model.factory.method.CCtrlMethodFactory;
import avea.bpm.codeanalyzer.model.factory.method.MethodFactory;
import avea.bpm.codeanalyzer.util.FileUtil;
import avea.bpm.codeanalyzer.util.JavaCodeUtil;
import avea.bpm.codeanalyzer.util.StringUtil;
import avea.bpm.codeanalyzer.util.StringUtil.StringIndex;
import avea.bpm.codeanalyzer.xmlgen.XmlObjectSampleGenerator;

import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;

public class CodeAnalyzer implements ProgramState
{
	private XmlObjectSampleGenerator xmlGenerator;
	
	private Collection jpdFiles;
	
	private TreeSet processSet;
	
	private HashMap businessControlMap;
	private HashMap bctrlMethodMap;
	
	private HashMap coreControlMap;
	private HashMap cctrlMethodMap;
	
	private HashMap errorCodeMap;
	
	public void doAll(String workdir, String corePath, String processPath) throws Exception
	{
		doAll(workdir, corePath, processPath, processPath);
	}
	public void doAll(String workdir, String corePath, String businessPath, String processPath) throws Exception
	{
		AnalyzerLogger.logInfo("Starting analyze...");
		AnalyzerLogger.logInfo("Finding error codes...");
		findErrorCodes(corePath);
		
		AnalyzerLogger.logInfo("Finding service controls and methods...");
		setCoreControlMap( new HashMap() );
		setCctrlMethodMap( new HashMap() );
		findControlsAndMethods(corePath, getServiceControlMap(), getCctrlMethodMap(), 
				CoreControlFactory.getInstance(), CCtrlMethodFactory.getInstance(), new AbstractCommand(){});
		AnalyzerLogger.logInfo("Found "+getServiceControlMap().size()+" service controls." );
		
		Command postBusinessControlCommand = new PostBusinessControlCommand();
		setBusinessControlMap( new HashMap() );
		setBctrlMethodMap( new HashMap() );

		AnalyzerLogger.logInfo("Finding business controls and methods...");
		findControlsAndMethods(processPath, getBusinessControlMap(), getBctrlMethodMap(), 
				BusinessControlFactory.getInstance(), BCtrlMethodFactory.getInstance(), postBusinessControlCommand);
		AnalyzerLogger.logInfo("Found "+getBusinessControlMap().size()+" core controls." );
		
		AnalyzerLogger.logInfo("Finding process files." );
		jpdFiles = FileUtil.findFilesInDirAndSubDirs(processPath, "jpd");
		
		AnalyzerLogger.logInfo("Finding processes." );
		setProcessSet( new TreeSet() );
		findProcesses(workdir, processPath);
	}

	private void findErrorCodes(String corePath) throws IOException {
		this.errorCodeMap = new HashMap();
		 
		String source = FileUtil.readFile(corePath+"/AveaBase/avea/base/general/BpmConst.java");
		CompilationUnit compilationUnit = parseSource(source);
		TypeDeclaration typeDeclaration = (TypeDeclaration) compilationUnit.types().get(0);
		
		List decl = typeDeclaration.bodyDeclarations();
		for (Iterator iter = decl.iterator(); iter.hasNext();) {
			FieldDeclaration declaration = (FieldDeclaration) iter.next();
			if ( declaration.getModifiers() == 25 ) {
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) declaration.fragments().get(0);
				String name = fragment.getName().toString();
				if ( name.startsWith("ERROR_")) {
					String tokenStr = ((NumberLiteral)fragment.getInitializer()).getToken();
					Integer integer = new Integer(tokenStr);
					this.errorCodeMap.put(name, integer);
				}
			}
		}		
	}
	
	private void findProcesses(String workdir, String path) throws IOException, JTagParseException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
		
		Collection files = FileUtil.findFilesInDirAndSubDirs(path, ".jpd");
		
		for (Iterator iter = files.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			
			String source = FileUtil.readFile(file);
			CompilationUnit compilationUnit = parseSource(source);
			TypeDeclaration typeDeclaration = (TypeDeclaration) compilationUnit.types().get(0);
			
			checkProcess(typeDeclaration.getName().toString());
			
			if ( !typeDeclaration.isLocalTypeDeclaration() ) {
				Process process = ProcessFactory.generateInstanceByName(typeDeclaration.getName().toString());
				
				File appDir = findProcessAppDir(file);
				process.setPath( appDir.getName());
				//process.setCvsHierarchy(cvsHierarchy);
				
				JDoc processDoc = JDoc.getInstance( typeDeclaration.getJavadoc() );
				ProcessInfo processInfo = ProcessInfoFactory.generateInstance(processDoc);

				//Find request and response types of process
				//& Populate sample input and output xml s
				
				String requestType = findRequestType(source, typeDeclaration.getName().toString());
				if ( requestType != null ) 
				{
					String responseType = requestType.replaceAll("RequestDocument", "ResponseDocument");
					
					String jarPath = appDir.getPath()+"/APP-INF/lib/AveaSchema.jar";
					
					String serviceJarPath = workdir+"/BPMBase/BPMBase/APP-INF/lib/AveaSchemaService.jar";
					JarClassLoader jarClazzLoader = new JarClassLoader();
					jarClazzLoader.addInternalJar(serviceJarPath);
					jarClazzLoader.addInternalJar(jarPath);
					AnalyzerLogger.logWarning("ProcessName --->>>> " + path + " *** " + process.getName() );
					System.out.println("ProcessName --->>>> " + path + " *** " + process.getName() );
					AnalyzerLogger.logWarning("-----------------------------------------------------------------------------------------------------------------------------");
					System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
					
			        String input = generateSampleXml(requestType, jarClazzLoader);
			        processInfo.setInput(input);
			        
			        String ouput = generateSampleXml(responseType, jarClazzLoader);
			        processInfo.setOutput(ouput);
				}
			
				//Override xdoclet return codes with found one in code
				//Risky code because assignments may be commented 
				Collection possibleReturnCodes = findPossibleReturnCodes(source);
				processInfo.setErrorCodes(possibleReturnCodes);
				
				process.setProcessInfo(processInfo);
				
				FieldDeclaration[] fields = typeDeclaration.getFields();
				for (int i = 0; i < fields.length; i++) {
					FieldDeclaration fieldDeclaration = fields[i];

					if ( fieldDeclaration.getJavadoc() != null )
					{
						JDoc doc = JDoc.getInstance( fieldDeclaration.getJavadoc() );
						JTag tag = doc.getTag("@common:control");
						if ( tag != null ) {
							String controlQualifiedName = fieldDeclaration.getType().toString();
							if ( controlQualifiedName.startsWith("avea.bpm.controls") ) {
								String controlVariableName = fieldDeclaration.fragments().get(0).toString();
		
								source = JavaCodeUtil.removeDoubleSlashedComments(source);
								StringIndex[] foundStringIndices = JavaCodeUtil.findMethodInvocations(source, controlVariableName);
								BusinessControl businessControl = (BusinessControl)getBusinessControlMap().get(controlQualifiedName+"Impl");
								if ( businessControl == null) {
									AnalyzerLogger.logWarning("<cant_find>\t<in_process>"+process.getName()+"</in_process>"
												+"<control>"+controlQualifiedName+"</control></cant_find>");
								}
								else {
									for (int j = 0; j < foundStringIndices.length; j++) {
										StringIndex stringIndex = foundStringIndices[j];
										String methodName = stringIndex.getString().trim();
										BCtrlMethod bctrlMethod = (BCtrlMethod) businessControl.getMethodByName(methodName);
										if ( bctrlMethod == null ) {
											AnalyzerLogger.logWarning("<cant_find>\t<in_process>"+process.getName()+"</in_process>"
											+"<method>"+businessControl.getName()+"."+methodName+"</method></cant_find>");
										}
										else {
											process.addBCMethod((BCtrlMethod) bctrlMethod);
											bctrlMethod.addProcess(process);
										}
									}
								}
							}
						}
					}
				}
				getProcessSet().add(process);
			}
		}
		//Set Process Info for referencing processes
		for (Iterator iter = getProcessSet().iterator(); iter.hasNext();) {
			Process process = (Process) iter.next();
			ProcessInfo processInfo = process.getProcessInfo();
			if ( !process.getName().endsWith("Validation") ) {
				ProcessInfo validationProcessInfo = findValidationProcessInfo(process.getName());
				if ( validationProcessInfo != null ) {
					processInfo.getErrorCodes().addAll( validationProcessInfo.getErrorCodes() );
				}
			}
			if(processInfo!=null){
				String returnCodesStr = StringUtil.collectionToDelimetedString(processInfo.getErrorCodes(), ", ");
				processInfo.setReturnCodes( returnCodesStr );
			}
		}
	}
	
	private String findRequestType(String source, String processName) {
		String requestType = null;
		String regex = "[= ]{1,1}[\\w]{1,}RequestDocument\\.Factory\\.parse\\(";
		Matcher matcher = Pattern.compile(regex).matcher(source);
		if ( matcher.find() )
		{
			String match = matcher.group();
			requestType = match.replaceAll("[= ]{1,1}", "");
			requestType = requestType.replaceAll("\\.Factory\\.parse\\(", "");
		}
		else
		{
			regex = " (avea\\.bpm\\.schemas\\.){0,1}[\\w]{1,}ProcessRequestDocument[\\s]{1,}requestDoc;";
			matcher = Pattern.compile(regex).matcher(source);
			if ( matcher.find() ) {
				String match = matcher.group();
				requestType = match.replaceAll("[\\s]{1,}requestDoc;", "");
				requestType = requestType.replaceAll(" (avea\\.bpm\\.schemas\\.){0,1}", "");
			}
			else
			{
				regex = " (avea\\.bpm\\.schemas\\.){0,1}[\\w]{1,}ProcessRequestDocument[\\s]{1,}validationRequestDoc;";
				matcher = Pattern.compile(regex).matcher(source);
				if ( matcher.find() ) {
					String match = matcher.group();
					requestType = match.replaceAll("[\\s]{1,}validationRequestDoc;", "");
					requestType = requestType.replaceAll(" (avea\\.bpm\\.schemas\\.){0,1}", "");
				}
				else
					AnalyzerLogger.logWarning("<cant_find_request_type>"+processName+"</cant_find_request_type>");
			}
		}
		return requestType;
	}
	private String generateSampleXml(String type, JarClassLoader jarClazzLoader) {
		String str = "";
		try {
			Class theClass = Class.forName("avea.bpm.schemas."+type, true, jarClazzLoader);
			XmlObject xmlObject = getXmlGenerator().populateXmlObject(theClass);
			AnalyzerLogger.logInfo("theClass :"+theClass.getName());
			XmlOptions options = new XmlOptions();
			options.setSavePrettyPrint();
			str = xmlObject.xmlText(options);
		}
		catch (Throwable throwable) {
			try {
				System.out.println(throwable.getCause());
				System.out.println(throwable.getMessage());
				type = type.replaceAll("Process", "ValidationProcess");
				Class theClass = Class.forName("avea.bpm.schemas."+type, true, jarClazzLoader);

				XmlObject xmlObject = getXmlGenerator().populateXmlObject(theClass);
				
				XmlOptions options = new XmlOptions();
				options.setSavePrettyPrint();
				str = xmlObject.xmlText(options).replaceAll("ValidationProcess", "Process");
			} catch (Throwable t) {
				AnalyzerLogger.logWarning("type -->>>>>>>>>>>>>>>>>>>>>>>>" + type);
				System.out.println("type -->>>>>>>>>>>>>>>>>>>>>>>>" + type);
				AnalyzerLogger.logWarning("Exception ----------------------->" +t);
				System.out.println("Exception ----------------------->" +t);
				AnalyzerLogger.logWarning("generateSampleXml", t);
			}
		}
        return str;
	}
	private Collection findPossibleReturnCodes(String source) {

		TreeSet returnCodes = new TreeSet();
		returnCodes.add(new Integer(0));
		for (Iterator iter = this.errorCodeMap.keySet().iterator(); iter.hasNext();) {
			String errorCodeName = (String) iter.next();
			if ( source.indexOf( "BpmConst."+errorCodeName) != -1 )
				returnCodes.add(this.errorCodeMap.get(errorCodeName));
		}
		returnCodes.add(new Integer(9999));
		
		return returnCodes;
	}
	private ProcessInfo findValidationProcessInfo(String processName) {
		ProcessInfo validationProcessInfo = null;
		for (Iterator iter = getProcessSet().iterator(); iter.hasNext();) {
			Process process = (Process) iter.next();
			if ( (processName+"Validation").equals(process.getName()) ) {
				ProcessInfo processInfo = process.getProcessInfo();
				validationProcessInfo = processInfo;
				break;
			}
		}
		return validationProcessInfo;
	}

	private File findProcessAppDir(File processFile) {
		File currentDir = processFile.getParentFile();
		FileFilter appDirFilter = FileUtil.getFileFilter(".work");
		FileFilter appDirAppinfFilter = FileUtil.getDirectoryFilter("APP-INF");
		while ( currentDir.listFiles(appDirFilter).length == 0 
				&& currentDir.listFiles(appDirAppinfFilter).length == 0 ) {
			currentDir = currentDir.getParentFile();
			if ( currentDir == null ) {
				AnalyzerLogger.logError("Cant find process application dir for: "+processFile);
				System.exit(-1);
			}
		}
		return currentDir;
	}
	
	private String findProcessCvsHierarchy(File appDir) {
		File currentDir = appDir.getParentFile();
		Stack stack = new Stack();
		stack.push( currentDir.getName() );
		
		
		while ( !"BPMProcesses".equals( currentDir.getName() ) ) {
			stack.push( currentDir.getName() );
			currentDir = currentDir.getParentFile();
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append( stack.pop() );
		for (Iterator iter = stack.iterator(); iter.hasNext();) {
			String dirName = (String) iter.next();
			buffer.append("."+dirName);
		}
		return buffer.toString();
	}

	private void checkProcess(String name) {
		for (Iterator iter = jpdFiles.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			String fileName = file.getName();
			if ( fileName.indexOf(name) != -1 ) {
				iter.remove();
				break;
			}
		}		
	}

	private void findControlsAndMethods(String path, HashMap controlMap, Map methodMap, ControlFactory controlFactory, MethodFactory methodFactory, Command command) throws Exception 
	{
		Collection controlFiles = FileUtil.findFilesInDirAndSubDirs(path, "Impl.jcs");
		
		for (Iterator iter = controlFiles.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			
			String source = FileUtil.readFile(file);
			CompilationUnit compilationUnit = parseSource(source);
			TypeDeclaration typeDeclaration = (TypeDeclaration) compilationUnit.types().get(0);

			Control control = controlFactory.generateInstance(typeDeclaration.getName().toString(), compilationUnit.getPackage().getName().toString() );
			
			MethodDeclaration[] methods = typeDeclaration.getMethods();
			for (int i = 0; i < methods.length; i++) {
				MethodDeclaration methodDeclaration = methods[i];
				try {
					if ( methodDeclaration.getJavadoc() != null
							&& methodDeclaration.getName().toString().indexOf("getIWIS") == -1) 
					{
						JDoc doc = JDoc.getInstance( methodDeclaration.getJavadoc());
						JTag tag = doc.getTag("@common:operation");
						if ( tag != null ) {
							Method method = methodFactory.generateInstance(methodDeclaration.getName().toString());

							if (method instanceof CCtrlMethod) {
								CCtrlMethod ctrlMethod = (CCtrlMethod) method;
								ServiceInfo serviceInfo = ServiceInfoFactory.generateInstance(doc, compilationUnit, methodDeclaration);
								ctrlMethod.setServiceInfo(serviceInfo);
								
								String iwisServiceName = findIwisServiceName(methodDeclaration, typeDeclaration);
								serviceInfo.setIwisName(iwisServiceName);
								
								//needed for stencil
								String signature = generateSignature(methodDeclaration);
								
								ctrlMethod.setSignature( signature );
							}							
							
							method.setControl(control);
							control.addMethod(method);
							
							methodMap.put(method.getFullName(), method);
						}
					}
				} catch (JTagParseException e) {
					AnalyzerLogger.logWarning("findControlsAndMethods", e);
				} catch (StringIndexOutOfBoundsException e) {
					AnalyzerLogger.logWarning("findControlsAndMethods", e);
				} catch (NullPointerException e) {
					AnalyzerLogger.logWarning("findControlsAndMethods", e);
				}
			}
			controlMap.put(control.getQualifiedName(), control);
			
			HashMap parameters = new HashMap();
			parameters.put("control", control);
			parameters.put("compilationUnit", compilationUnit);
			parameters.put("source", source);
			command.execute(parameters);
		}
	}
	private String findIwisServiceName(MethodDeclaration methodDeclaration, TypeDeclaration typeDeclaration) {
		String bodyStr = methodDeclaration.getBody().toString();
		String iwisServiceName = null;
		
		// PATCH Iwis Parameter Call
		/*
		String methodName = methodDeclaration.getName().toString();
		int indexOfGetPrm = methodName.indexOf("getPrm");
		if ( indexOfGetPrm != -1) {
			iwisServiceName = methodName.substring(indexOfGetPrm+6);
			return iwisServiceName;
		}
		*/
		// PATCH FIX 
		
		// PATCH Iwis Call Reuse
		int indexOfXX = bodyStr.indexOf("XX(\"");
		if ( indexOfXX != -1) {
			int indexOfEndQuote = bodyStr.indexOf("\"", indexOfXX+4);
			iwisServiceName = bodyStr.substring(indexOfXX+4, indexOfEndQuote);
			return iwisServiceName;
		}
		// PATCH FIX getPrm
		
		iwisServiceName = findIwisServiceNameR(typeDeclaration, bodyStr, 3);
		return iwisServiceName;
	}
	private String findIwisServiceNameR(TypeDeclaration typeDeclaration, String bodyStr, int level) 
	{
		if (level == 0)
			return null;
		level--;
		
		String iwisServiceName = null;
		int indexOfIwisUtilsInXml = bodyStr.indexOf("IwisUtils.inXml(\"");
		if ( indexOfIwisUtilsInXml != -1) {
			int indexOfEndQuote = bodyStr.indexOf("\"", indexOfIwisUtilsInXml+17);
			iwisServiceName = bodyStr.substring(indexOfIwisUtilsInXml+17, indexOfEndQuote);
		}
		else {
			MethodDeclaration[] declarations = typeDeclaration.getMethods();
			for (int i = 0; i < declarations.length; i++) {
				MethodDeclaration methodDeclaration2 = declarations[i];
				if ( bodyStr.indexOf( methodDeclaration2.getName().toString() ) != -1)
				{
					iwisServiceName = findIwisServiceNameR(typeDeclaration, methodDeclaration2.getBody().toString(), level);
					if ( iwisServiceName != null )
						break;
				}
			}
		}
		return iwisServiceName;
	}
	private String generateSignature(MethodDeclaration methodDeclaration) {
		String signature = methodDeclaration.getReturnType2().toString();
		signature += " " + methodDeclaration.getName().toString();
		
		String parametersStr = methodDeclaration.parameters().toString();
		signature += parametersStr.replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
		
		return signature;
	}

	private CompilationUnit parseSource(String source) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);  // handles JDK 1.0, 1.1, 1.2, 1.3, 1.4, 1.5
		parser.setSource(source.toCharArray());
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		return compilationUnit;
	}
	
	
	private class PostBusinessControlCommand extends AbstractCommand
	{
		public void execute(HashMap parameters) throws IOException, JTagParseException {
			BusinessControl businessControl = (BusinessControl) parameters.get("control");
			CompilationUnit compilationUnit = (CompilationUnit) parameters.get("compilationUnit");
			String source = (String) parameters.get("source");
			TypeDeclaration typeDeclaration = (TypeDeclaration) compilationUnit.types().get(0);
			
			FieldDeclaration[] fields = typeDeclaration.getFields();
			for (int i = 0; i < fields.length; i++) {
				FieldDeclaration fieldDeclaration = fields[i];

				if ( fieldDeclaration.getJavadoc() != null ) {
					JDoc doc = JDoc.getInstance( fieldDeclaration.getJavadoc() );
					JTag tag = doc.getTag("@common:control");
					if ( tag != null ) {
						String controlQualifiedName = ((SimpleType) fieldDeclaration.getType()).getName().getFullyQualifiedName();
						if ( controlQualifiedName.startsWith("avea.base.service") 
								|| controlQualifiedName.startsWith("avea.base.tech") ) {
							String controlVariableName = fieldDeclaration.fragments().get(0).toString();
	
							source = JavaCodeUtil.removeDoubleSlashedComments(source);
							StringIndex[] foundStringIndices = JavaCodeUtil.findMethodInvocations(source, controlVariableName);
							
							CoreControl coreControl = (CoreControl)getServiceControlMap().get(controlQualifiedName+"Impl");
							if ( coreControl == null )
								AnalyzerLogger.logWarning("<no_service_control><name>"+controlQualifiedName+"Impl</name>"+
															"<in_bc>"+businessControl.getQualifiedName()+"</in_bc></no_service_control>");
							else 
							{
								for (int j = 0; j < foundStringIndices.length; j++) {
									StringIndex stringIndex = foundStringIndices[j];
									String methodName = stringIndex.getString();
									Method cctrlMethod = coreControl.getMethodByName(methodName);
									if ( methodName.startsWith("getIWIS") )
										;
									else if ( cctrlMethod == null ) {
										AnalyzerLogger.logWarning("<CCTRL_NO_METHOD><NAME>"+coreControl.getQualifiedName()+"."+methodName+"</NAME>" +
																	"<IN_BC>"+businessControl.getQualifiedName()+"</IN_BC></CCTRL_NO_METHOD>");
									}
									else {
										StringIndex businessMethodIndex = JavaCodeUtil.findMethodContainigIndex(source, stringIndex.getIndex());
										String businessMethodName = businessMethodIndex.getString();
										Method bctrlMethod = businessControl.getMethodByName(businessMethodName);
										if ( bctrlMethod == null ) {
											//FIXME core method is called from a business method that doesnt have @common:operation doclet
											AnalyzerLogger.logWarning("<BCTRL_NO_METHOD>"+businessControl.getQualifiedName()+"."+businessMethodName+"</BCTRL_NO_METHOD>");
										}
										else {
											bctrlMethod.addCalledMethod(cctrlMethod);
											cctrlMethod.addCallerMethod(bctrlMethod);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	

	public TreeSet getProcessSet() {
		return processSet;
	}

	public HashMap getCctrlMethodMap() {
		return cctrlMethodMap;
	}

	public HashMap getBctrlMethodMap() {
		return bctrlMethodMap;
	}

	public HashMap getBusinessControlMap() {
		return businessControlMap;
	}

	public HashMap getServiceControlMap() {
		return coreControlMap;
	}

	public void setCoreControlMap(HashMap coreControlMap) {
		this.coreControlMap = coreControlMap;
	}

	public void setBctrlMethodMap(HashMap bctrlMethodMap) {
		this.bctrlMethodMap = bctrlMethodMap;
	}

	public void setBusinessControlMap(HashMap businessControlMap) {
		this.businessControlMap = businessControlMap;
	}

	public void setCctrlMethodMap(HashMap cctrlMethodMap) {
		this.cctrlMethodMap = cctrlMethodMap;
	}

	public void setProcessSet(TreeSet processSet) {
		this.processSet = processSet;
	}
	
	
	public Collection getCctrlMethods() {
		return getCctrlMethodMap().values();
	}

	public Collection getBusinessControls() {
		return getBusinessControlMap().values();
	}

	public Collection getCoreControls() {
		return getServiceControlMap().values();
	}

	public Collection getBctrlMethods() {
		return getBctrlMethodMap().values();
	}
	public HashMap getErrorCodeMap() {
		return errorCodeMap;
	}
	public XmlObjectSampleGenerator getXmlGenerator() {
		return xmlGenerator;
	}
	public void setXmlGenerator(XmlObjectSampleGenerator xmlGenerator) {
		this.xmlGenerator = xmlGenerator;
	}
	
	
	
	
	public void doAllZ(String workdir, String corePath, String businessPath, String processPath) throws Exception
	{
		AnalyzerLogger.logInfo("Starting analyze...");
		AnalyzerLogger.logInfo("Finding error codes...");
		findErrorCodes(corePath);
		
		AnalyzerLogger.logInfo("Finding service controls and methods...");
		setCoreControlMap( new HashMap() );
		setCctrlMethodMap( new HashMap() );
		findControlsAndMethods(corePath, getServiceControlMap(), getCctrlMethodMap(), 
				CoreControlFactory.getInstance(), CCtrlMethodFactory.getInstance(), new AbstractCommand(){});
		AnalyzerLogger.logInfo("Found "+getServiceControlMap().size()+" service controls." );
		
		Command postBusinessControlCommand = new PostBusinessControlCommand();
		setBusinessControlMap( new HashMap() );
		setBctrlMethodMap( new HashMap() );

		AnalyzerLogger.logInfo("Finding business controls and methods...");
		findControlsAndMethods(processPath, getBusinessControlMap(), getBctrlMethodMap(), 
				BusinessControlFactory.getInstance(), BCtrlMethodFactory.getInstance(), postBusinessControlCommand);
		AnalyzerLogger.logInfo("Found "+getBusinessControlMap().size()+" core controls." );
		
		AnalyzerLogger.logInfo("Finding process files." );
		jpdFiles = FileUtil.findFilesInDirAndSubDirs(processPath, "jpd");
		
		AnalyzerLogger.logInfo("Finding processes." );
		setProcessSet( new TreeSet() );
		findProcesses(workdir, processPath);
	}
	
	
	public static void main(String[] args) {
		CodeAnalyzer an = new CodeAnalyzer();
		try {
			an.doAllZ("d:/mw/workspaces/osb1030/hotfix-ws/","d:/mw/workspaces/osb1030/hotfix-ws/BPMBase/", "","d:/mw/workspaces/osb1030/hotfix-ws/CampaignManagement");
			//an.jpdFiles = FileUtil.findFilesInDirAndSubDirs("d:/mw/workspaces/osb1030/hotfix-ws/CampaignManagement", "jpd");
			//an.findProcesses("d:/mw/workspaces/osb1030/hotfix-ws","d:/mw/workspaces/osb1030/hotfix-ws/CampaignManagement");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 			
	}
}
