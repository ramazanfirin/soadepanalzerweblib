package avea.bpm.codeanalyzer.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

//import avea.bpm.arch.dao.BaseDAO;
//import avea.bpm.arch.dao.ProcessDAO;
//import avea.bpm.arch.dao.SampleElementValueDAO;
//import avea.bpm.arch.dao.ServiceDAO;
//import avea.bpm.arch.dao.StencilDAO;
import avea.bpm.arch.model.Process;
import avea.bpm.arch.model.SampleElementValue;
import avea.bpm.arch.model.Service;
import avea.bpm.arch.model.Stencil;
import avea.bpm.codeanalyzer.finder.CodeAnalyzer;
import avea.bpm.codeanalyzer.iwis.IwisConfigModel;
import avea.bpm.codeanalyzer.iwis.IwisConfigModelBuilder;
import avea.bpm.codeanalyzer.iwis.model.Attribute;
import avea.bpm.codeanalyzer.iwis.model.Conversion;
import avea.bpm.codeanalyzer.logger.AnalyzerLogger;
import avea.bpm.codeanalyzer.model.CCtrlMethod;
import avea.bpm.codeanalyzer.model.CoreControl;
import avea.bpm.codeanalyzer.util.CollectionUtil;
import avea.bpm.codeanalyzer.util.ObjectFilter;
import avea.bpm.codeanalyzer.xmlgen.XmlObjectSampleGenerator;

import com.avea.bpmn.visio.stencil.VisioStencilGenerator;
import com.avea.bpmn.visio.stencil.bean.VisioStencilBean;
import com.bea.xml.XmlException;

public class CoreRunner {

	public Map<String, List> run(String workdir, String iwisConfigFilePath) throws Exception, IOException {
		
		AnalyzerLogger.logInfo("READING SAMPLE XML ELEMENT VALUES.");
		HashMap registry = new HashMap();
		/*
		List sampleList = SampleElementValueDAO.getInstance().readAll();
		for (Iterator iter = sampleList.iterator(); iter.hasNext();) {
			SampleElementValue element = (SampleElementValue) iter.next();
			registry.put(element.getElementName(), element.getElementValue());
		}
		*/
		XmlObjectSampleGenerator generator = new XmlObjectSampleGenerator(registry);
		
		AnalyzerLogger.logInfo("ANALYZING CODE.");
		CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
		codeAnalyzer.setXmlGenerator(generator);
		codeAnalyzer.doAll(workdir, workdir+"/BPMBase/BPMBase", workdir+"/BPMProcesses");
		
		IwisConfigModel configModel = new IwisConfigModelBuilder().build(iwisConfigFilePath);
		HashMap serviceMap = transformServices(codeAnalyzer, configModel);
		Map<String, List> processMap = transformCodeModelToResultModel(serviceMap, codeAnalyzer, configModel);
		
		return processMap;
		/*
		BaseDAO processDAO = ProcessDAO.getInstance();
		AnalyzerLogger.logInfo("DELETING OLD RESULTS FROM DB.");
		processDAO.deleteAll();
		AnalyzerLogger.logInfo("WRITING NEW RESULTS TO DB.");
		processDAO.saveAll(processList);
		ServiceDAO.getInstance().saveOrUpdateAll(new ArrayList(serviceMap.values()));
		
		Stencil stencil = generateStencil(codeAnalyzer);
		StencilDAO.getInstance().save(stencil);
//		FileUtil.writeFile("avea_bpmn.vsx", visioStencilXmlStr);
		
		AnalyzerLogger.logInfo("ALL DONE.");
		*/
	}
	
	private HashMap transformServices(CodeAnalyzer codeAnalyzer, IwisConfigModel configModel) 
	{
		HashMap serviceMap = new HashMap();
		for (Iterator iter = codeAnalyzer.getCctrlMethodMap().keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			CCtrlMethod ctrlMethod = (CCtrlMethod)codeAnalyzer.getCctrlMethodMap().get(key);
			
			Service service = new Service();
			service.setMethodName( ctrlMethod.getName() );
			service.setControlName( ctrlMethod.getControl().getName() );
			service.setControlPack( ctrlMethod.getControl().getPack() );
			service.setMethodSignature( ctrlMethod.getSignature() );
			
			service.setName( ctrlMethod.getServiceInfo().getName() );
			service.setDescription( ctrlMethod.getServiceInfo().getDescription() );
			service.setOwner( ctrlMethod.getServiceInfo().getOwner() );
			
			String iwisName = ctrlMethod.getServiceInfo().getIwisName();
			service.setIwisName( iwisName );
			
			if ( iwisName != null ) {
				avea.bpm.codeanalyzer.iwis.model.Service iwisService = findIwisServicebyName(iwisName, configModel);
				if ( iwisService != null ) {
					service.setDescription(iwisService.getDescription());
					service.setVersion(iwisService.getVersion());
					String backendImplementation = findServiceBackendImplementation(iwisService, configModel);
					service.setBackendImplementation(backendImplementation);
				}
			}
			serviceMap.put(key, service);
		}
		return serviceMap;
	}

	private Map<String, List>  transformCodeModelToResultModel(HashMap serviceMap, CodeAnalyzer codeAnalyzer, IwisConfigModel configModel) throws IOException, XmlException 
	{
		
		Map<String, List> processMap = new HashMap<String, List>();
	
		Set set = codeAnalyzer.getProcessSet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {			
			
			avea.bpm.codeanalyzer.model.Process codeProcess = (avea.bpm.codeanalyzer.model.Process) iter.next();
			Process process = generateProcess(codeProcess, configModel);

			Collection collection = codeProcess.getCControlMetods();
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				CCtrlMethod ctrlMethod = (CCtrlMethod) iterator.next();
				Service service = (Service) serviceMap.get(ctrlMethod.getControl().getPack()+"."+
															ctrlMethod.getControl().getName()+"."+
															ctrlMethod.getName());
				if(null != service.getIwisName())
					process.addService(service);
			}
			List processList = processMap.get(process.getProcessPath());
			if(processList==null){
				processList =  new ArrayList<Process>();
				processList.add(process);
				processMap.put(process.getProcessPath(),processList);
			}else{
				processList.add(process);
			}
		}
		return processMap;
	}

	
	
	
	
	private avea.bpm.codeanalyzer.iwis.model.Service findIwisServicebyName(final String iwisName, IwisConfigModel configModel) {
		ObjectFilter filter = new ObjectFilter() {
			public boolean accept(Object object) { 
				avea.bpm.codeanalyzer.iwis.model.Service service = (avea.bpm.codeanalyzer.iwis.model.Service)object;
				if ( service.getName().equals(iwisName) )
					return true;
				return false;
			}
		};
		avea.bpm.codeanalyzer.iwis.model.Service service 
			= (avea.bpm.codeanalyzer.iwis.model.Service)CollectionUtil.findByFilter(configModel.getServices(), filter);

		return service;
	}
	
	private String findServiceBackendImplementation(avea.bpm.codeanalyzer.iwis.model.Service service, IwisConfigModel configModel) {
		String implName = null;
		if (  service != null && service.getPool() != null && service.getIn() != null ) {
			String poolName = service.getPool().getName();
			if ( poolName != null && poolName.startsWith("IDB_") ) {
				Conversion inConversion = service.getIn();
				if ( inConversion.getAttributes().size() > 0 ) {
					Attribute attribute = (Attribute) inConversion.getAttributes().get(0);
					if ( attribute.getStringValue() != null ) {
						String str = attribute.getStringValue();
						str = str.replaceAll("SELECT ", "").replaceAll("BEGIN \\? := ", "").replaceAll("BEGIN ", "");
						int indexOfOpenParan = str.indexOf('(');
						if ( indexOfOpenParan != -1 ) {
							String serviceImpl = str.substring(0, indexOfOpenParan);
							implName = serviceImpl;
						}
					}
				}
			}
		}
		return implName;
	}

	private Stencil generateStencil(CodeAnalyzer codeAnalyzer) throws Exception {
		AnalyzerLogger.logInfo("GENERATING STENCIL.");
		List stencilBeanList = new ArrayList();
		Collection serviceControls = codeAnalyzer.getCoreControls();
		for (Iterator iter = serviceControls.iterator(); iter.hasNext();) {
			CoreControl control = (CoreControl) iter.next();
			
			VisioStencilBean bean = new VisioStencilBean();
			String ctrlName = control.getName();
			bean.setClassName( ctrlName.substring(0, ctrlName.length() - 4) );
			bean.setPackageName( control.getPack() );
			
			TreeSet methodSignatureSet = new TreeSet();
			Iterator methodsIter = control.methodsIterator();
			while (methodsIter.hasNext()) {
				CCtrlMethod method = (CCtrlMethod) methodsIter.next();
				methodSignatureSet.add( method.getSignature() );
			}
			bean.setClassMethods(new ArrayList(methodSignatureSet));
			
			stencilBeanList.add(bean);
		}
		TreeSet errorCodeSet = new TreeSet();
		HashMap errorCodeMap = codeAnalyzer.getErrorCodeMap();
		for (Iterator iter = errorCodeMap.keySet().iterator(); iter.hasNext();) {
			String code = (String) iter.next();
			Integer val = (Integer) errorCodeMap.get(code);
			errorCodeSet.add(code + " ("+val+")");
		}
		List errorCodeList = new ArrayList(errorCodeSet);
		String visioStencilXmlStr = new VisioStencilGenerator().generateVisioStencil(stencilBeanList, errorCodeList);
		
		Stencil stencil = new Stencil();
		stencil.setTextContent(visioStencilXmlStr);
		return stencil;
	}

	private static Process generateProcess(avea.bpm.codeanalyzer.model.Process codeProcess, IwisConfigModel configModel) 
	{
		Process process = new Process();
		process.setName(codeProcess.getName());
		process.setProcessPath(codeProcess.getPath());
//		process.setDescription(codeProcess.getProcessInfo().getDescription());
		process.setReturnCodes(codeProcess.getProcessInfo().getReturnCodes());
		process.setInput(codeProcess.getProcessInfo().getInput());
		process.setOutput(codeProcess.getProcessInfo().getOutput());
		
//		String iwisService = findIwisServiceName(codeProcess, configModel);
		List processServices = configModel.queryServiceByName("BPM_", "", "");
		avea.bpm.codeanalyzer.iwis.model.Service service = findIwisService(processServices, codeProcess);
		if ( service != null) {
			process.setIwisService(service.getName());
			process.setDescription(service.getDescription());
			process.setVersion(service.getVersion());
		}
		
		return process;
	}

	private static avea.bpm.codeanalyzer.iwis.model.Service findIwisService(List processServices, avea.bpm.codeanalyzer.model.Process codeProcess) {
		avea.bpm.codeanalyzer.iwis.model.Service foundService = null;
		for (Iterator iter = processServices.iterator(); iter.hasNext();) {
			avea.bpm.codeanalyzer.iwis.model.Service service = (avea.bpm.codeanalyzer.iwis.model.Service)iter.next();
			String processPath = service.getDetailedService().getName();
			if ( processPath.endsWith(codeProcess.getName()+".jpd" )) {
				foundService = service;
			}
		}
		return foundService;
	}
}
