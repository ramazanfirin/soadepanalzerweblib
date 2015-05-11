package avea.bpm.codeanalyzer.xmlgen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;

import avea.bpm.codeanalyzer.logger.AnalyzerLogger;

import com.bea.xml.XmlObject;


public class XmlObjectSampleGenerator {

	private HashMap sampleRegistry;

	public XmlObjectSampleGenerator(){
		this.sampleRegistry = new HashMap();
	}

	public XmlObjectSampleGenerator(HashMap sampleRegistry){
		this.sampleRegistry = sampleRegistry;
	}

	public XmlObject populateXmlObject(String clazzName) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Class clazz = Class.forName(clazzName);
		return populateXmlObject(clazz);
	}
	
	public XmlObject populateXmlObject(Class clazz) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		XmlObject xmlObject = (XmlObject) newXmlBeanInstance(clazz);
		return populateXmlObject(xmlObject);
	}
	
	public XmlObject populateXmlObject(XmlObject xmlObject) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		
		Class clazz = xmlObject.getClass();
		
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodName = method.getName(); 
			if ( methodName.startsWith("set") ) {
				String addMethodName;
				if ( methodName.endsWith("Array") ) {
					addMethodName = "add"+methodName.substring(3, methodName.length()-5);
				}
				else {
					addMethodName = "addNew"+methodName.substring(3);
					try {
						clazz.getMethod(addMethodName, new Class[0]);
					} catch (NoSuchMethodException e) {
						
						Class parameterClazz = method.getParameterTypes()[0];
						if ( parameterClazz.isPrimitive() ) {
							Object samplePrimitive = SampleUtil.getPrimitiveNumberInstance(parameterClazz);
							if ( samplePrimitive != null )
								method.invoke(xmlObject, new Object[]{samplePrimitive});
							else
								AnalyzerLogger.logWarning("<unknown_type_1> "+parameterClazz+" </unknown_type_1>");
						}
						else {
							String superName = parameterClazz.getSuperclass().getName();
							if ( "com.bea.xml.XmlObject".equals( superName ) ) {
								XmlObject innerObject = populateXmlObject(parameterClazz);
								method.invoke(xmlObject, new Object[]{innerObject});
							}
							else if ( "java.lang.Number".equals( superName ) ) {
								Number number = SampleUtil.getNumberInstance(parameterClazz);
								String elementName = methodName.substring(3, methodName.length()).toUpperCase(Locale.ENGLISH);
								if ( sampleRegistry.containsKey(elementName) ) {
									String value = (String) sampleRegistry.get(elementName);
									number = SampleUtil.getNumberInstance(parameterClazz, value);
								}
								method.invoke(xmlObject, new Object[]{number});
							}
							else if ( "java.lang.String".equals( parameterClazz.getName() ) )
							{
								Object obj = "string";
								String elementName = methodName.substring(3, methodName.length()).toUpperCase(Locale.ENGLISH);
								if ( sampleRegistry.containsKey(elementName) )
									obj = sampleRegistry.get(elementName);
								method.invoke(xmlObject, new Object[]{obj});
							}
							else {
		//						TODO handle unknown types
								AnalyzerLogger.logWarning("<unknown_type_2>" +
															"<type>"+parameterClazz+"</type>" +
															"<super>"+superName+"</super>" +
														  "</unknown_type_2>");
							}
						}
					}
				}
			}
			else if ( methodName.startsWith("addNew") ) {
				XmlObject innerObject = (XmlObject) method.invoke(xmlObject, new Object[0]);
				populateXmlObject(innerObject);
			}
			else if ( methodName.startsWith("add") ) {
				Class parameterClazz = method.getParameterTypes()[0];
				if ( parameterClazz.isPrimitive() ) {
					Object samplePrimitive = SampleUtil.getPrimitiveNumberInstance(parameterClazz);
					if ( samplePrimitive != null )
						method.invoke(xmlObject, new Object[]{samplePrimitive});
					else
						AnalyzerLogger.logWarning("<unknown_type_3> "+clazz+" </unknown_type_3>");
				}
				else {
					String superName = parameterClazz.getSuperclass().getName();
					if ( "com.bea.xml.XmlObject".equals( superName ) ) {
						XmlObject innerObject = populateXmlObject(parameterClazz);
						method.invoke(xmlObject, new Object[]{innerObject});
					}
					else if ( "java.lang.Number".equals( superName ) ) {
						Number number = SampleUtil.getNumberInstance(parameterClazz);
						method.invoke(xmlObject, new Object[]{number});
					}
					else if ( "java.lang.String".equals( parameterClazz.getName() ) )
					{
						Object obj = " ";
						method.invoke(xmlObject, new Object[]{obj});
					}
					else {
//						TODO handle unknown types
						AnalyzerLogger.logWarning("<unknown_type_4>" +
													"<type>"+parameterClazz+"</type>" +
													"<super>"+superName+"</super>" +
												  "</unknown_type_4>");
					}
				}
			}
		}
		
		return xmlObject;
	}
	
	public Object newXmlBeanInstance(Class clazz) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException 
	{
		Method invokeMethod = null;
		
		Method[] methods = clazz.getDeclaredClasses()[0].getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if ( "newInstance".equals( method.getName() ) &&
					method.getParameterTypes().length == 0) {
				invokeMethod = method;
				break;
			}
		}
		return invokeMethod.invoke(null, new Object[0]);
	}
}
