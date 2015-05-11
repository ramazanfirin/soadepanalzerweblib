package com.tomecode.soa.ora.suite10g.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.tomecode.soa.ora.suite10g.esb.EsbGrp;
import com.tomecode.soa.ora.suite10g.esb.EsbOperation;
import com.tomecode.soa.ora.suite10g.esb.EsbRoutingRule;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc.ServiceSubType;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc.ServiceType;
import com.tomecode.soa.ora.suite10g.esb.EsbSys;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.ora.suite10g.esb.protocols.db.DBAdapter;
import com.tomecode.soa.ora.suite10g.esb.protocols.db.FTPAdapter;
import com.tomecode.soa.ora.suite10g.esb.protocols.db.FileAdapter;
import com.tomecode.soa.ora.suite10g.esb.protocols.db.JMSAdapter;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Parser for Oracle 10g ESB services
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Ora10gEsbParser extends AbstractParser {

	/**
	 * Constructor
	 */
	public Ora10gEsbParser() {

	}

	/**
	 * parse {@link Ora10gEsbProject}
	 * 
	 * @param projectFolder
	 * @return
	 * @throws ServiceParserException
	 */
	public final Ora10gEsbProject parse(File projectFolder) throws ServiceParserException {
		Ora10gEsbProject esbProject = new Ora10gEsbProject(projectFolder, true);
		return parse(esbProject);
	}

	/**
	 * parse {@link Ora10gEsbProject} as ZIP file
	 * 
	 * @param projectZip
	 * @return
	 * @throws ServiceParserException
	 */
	public final Ora10gEsbProject parseZip(File projectZip) throws ServiceParserException {
		Ora10gEsbProject esbProject = new Ora10gEsbProject(projectZip, false);
		parseZip2(projectZip, esbProject);
		return esbProject;
	}

	/**
	 * parse {@link Ora10gEsbProject} as ZIP file
	 * 
	 * @param projectZip
	 * @param esbProject
	 * @throws ServiceParserException
	 */
	private final void parseZip2(File projectZip, Ora10gEsbProject esbProject) throws ServiceParserException {
		int index = projectZip.getName().lastIndexOf(".");
		if (index != -1) {
			esbProject.setName(projectZip.getName().substring(0, index));
		} else {
			esbProject.setName(projectZip.getName());
		}

		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(projectZip);
			Enumeration<? extends ZipEntry> e = zipFile.entries();

			while (e.hasMoreElements()) {
				ZipEntry zipEntry = e.nextElement();

				if (!zipEntry.isDirectory()) {
					if (zipEntry.getName().endsWith(".esbsys")) {
						EsbSys esbSys = parseEsbSysZip(new File(projectZip.getPath() + File.separator + zipEntry.toString()), zipFile.getInputStream(zipEntry));
						esbSys.setOwnerEsbProject(esbProject);
						esbProject.addBasicEsbNode(esbSys);
					} else if (zipEntry.getName().endsWith(".esbgrp")) {
						parseEsbGrpZip(new File(projectZip.getPath() + File.separator + zipEntry.toString()), zipFile.getInputStream(zipEntry), esbProject);
					} else if (zipEntry.getName().endsWith(".esbsvc")) {
						parseEsbSvcZip(new File(projectZip.getPath() + File.separator + zipEntry.toString()), zipFile.getInputStream(zipEntry), esbProject);
					}
				}

			}

		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * parse {@link Ora10gEsbProject}
	 * 
	 * @param esbProject
	 * @return
	 * @throws ServiceParserException
	 */
	public final Ora10gEsbProject parse(Ora10gEsbProject esbProject) throws ServiceParserException {
		if (esbProject.isFolder()) {
			// Ora10gEsbProject esbProject = new
			// Ora10gEsbProject(findProjectName(projectFolder), projectFolder);
			esbProject.setName(findProjectName(esbProject.getFile()));

			List<File> findedFiles = new ArrayList<File>();
			findEsbsvcFiles(esbProject.getFile(), findedFiles, ".esbsys");

			for (File esbSysFile : findedFiles) {
				EsbSys esbSys = parseEsbSysProject(esbSysFile);
				esbSys.setOwnerEsbProject(esbProject);
				esbProject.addBasicEsbNode(esbSys);
			}

			findedFiles.clear();
			findEsbsvcFiles(esbProject.getFile(), findedFiles, ".esbgrp");

			for (File esbGrpFile : findedFiles) {
				parseEsbGrpProject(esbGrpFile, esbProject);
			}
			findedFiles.clear();
			findEsbsvcFiles(esbProject.getFile(), findedFiles, ".esbsvc");

			for (File esbSvcFile : findedFiles) {
				parseEsbSvcProject(esbSvcFile, esbProject);
			}

		} else {
			parseZip2(esbProject.getFile(), esbProject);
		}

		return esbProject;
	}

	/**
	 * parse {@link EsbGrp} from project folder
	 * 
	 * @param esbGrpFile
	 * @param esbProject
	 * @throws ServiceParserException
	 */
	private final void parseEsbGrpProject(File esbGrpFile, Ora10gEsbProject esbProject) throws ServiceParserException {
		parseEsbGrp(esbGrpFile, parseXml(esbGrpFile), esbProject);
	}

	/**
	 * parse {@link EsbGrp} from zip
	 * 
	 * @param esbGrpFile
	 * @param zipStream
	 * @param esbProject
	 * @throws ServiceParserException
	 */
	private final void parseEsbGrpZip(File esbGrpFile, InputStream zipStream, Ora10gEsbProject esbProject) throws ServiceParserException {
		parseEsbGrp(esbGrpFile, parseXml(zipStream), esbProject);
	}

	/**
	 * parse {@link EsbGrp}
	 * 
	 * @param esbGrpFile
	 * @param eService
	 * @param esbProject
	 */
	private final void parseEsbGrp(File esbGrpFile, Element eService, Ora10gEsbProject esbProject) {
		EsbGrp esbGrp = new EsbGrp(esbGrpFile, eService.attributeValue("name"), eService.attributeValue("qname"));
		esbGrp.setProject(esbProject);
		Element parent = eService.element("parent");
		if (parent != null) {

			if ("serviceGroup".equals(parent.attributeValue("type"))) {
				EsbGrp grp = esbProject.findEsbGrpByQname(parent.attributeValue("qname"));
				if (grp != null) {
					grp.addBasicEsbNode(esbGrp);
				} else {
					esbProject.addBasicEsbNode(esbGrp);
				}
			} else if ("system".equals(parent.attributeValue("type"))) {
				EsbSys sys = esbProject.findEsbSysByQname(parent.attributeValue("qname"));
				if (sys != null) {
					sys.addBasicEsbNode(esbGrp);
				} else {
					esbProject.addBasicEsbNode(esbGrp);
				}
			} else {
				esbProject.addBasicEsbNode(esbGrp);
			}

		}
	}

	/**
	 * parse {@link EsbSys}
	 * 
	 * @param esbSysFile
	 * @return
	 * @throws ServiceParserException
	 */
	private final EsbSys parseEsbSysProject(File esbSysFile) throws ServiceParserException {
		Element eService = parseXml(esbSysFile);
		return new EsbSys(esbSysFile, eService.attributeValue("name"), eService.attributeValue("qname"));
	}

	/**
	 * parse {@link EsbSys} from zip
	 * 
	 * @param file
	 * @param zipStream
	 * @return
	 * @throws ServiceParserException
	 */
	private final EsbSys parseEsbSysZip(File file, InputStream zipStream) throws ServiceParserException {
		Element eService = parseXml(zipStream);
		return new EsbSys(file, eService.attributeValue("name"), eService.attributeValue("qname"));
	}

	/**
	 * trying find esb project name
	 * 
	 * @param projectFolder
	 * @return
	 */
	private final String findProjectName(File projectFolder) {

		File[] files = projectFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".jpr")) {
					return file.getName().substring(0, file.getName().indexOf(".jpr"));
				}
			}
		}
		return projectFolder.getName();
	}

	/**
	 * find all *.esbsvc in project dir
	 * 
	 * @param projectDir
	 * @param findedFiles
	 */
	private final void findEsbsvcFiles(File projectDir, List<File> findedFiles, String fileTypeName) {
		File[] files = projectDir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				findEsbsvcFiles(file, findedFiles, fileTypeName);
			}
			if (file.isFile() && file.getName().endsWith(fileTypeName)) {
				findedFiles.add(file);
			}
		}
	}

	/**
	 * parse {@link EsbSvc} from {@link Project}
	 * 
	 * @param file
	 * @param esbProject
	 * @throws ServiceParserException
	 */
	private final void parseEsbSvcProject(File file, Ora10gEsbProject esbProject) throws ServiceParserException {
		parseEsbSvc(file, parseXml(file), esbProject);
	}

	/**
	 * parse {@link EsbSvc} from zip
	 * 
	 * @param file
	 * @param zipStream
	 * @param esbProject
	 * @throws ServiceParserException
	 */
	private final void parseEsbSvcZip(File file, InputStream zipStream, Ora10gEsbProject esbProject) throws ServiceParserException {
		parseEsbSvc(file, parseXml(zipStream), esbProject);
	}

	/**
	 * parse {@link EsbSvc}
	 * 
	 * @param file
	 * @throws ServiceParserException
	 */
	private final void parseEsbSvc(File file, Element eService, Ora10gEsbProject esbProject) throws ServiceParserException {
		EsbSvc esb = new EsbSvc(file, eService.attributeValue("name"), eService.attributeValue("qname"));
		esb.setProject(esbProject);

		esb.setServiceType(ServiceType.parse(eService.attributeValue("serviceType")));
		esb.setServiceSubType(ServiceSubType.parse(eService.attributeValue("serviceSubType")));
		esb.setTypeDescription(eService.attributeValue("typeDescription"));

		Element eServiceDefinition = eService.element("serviceDefinition");
		if (eServiceDefinition != null) {
			esb.setWsdlURL(eServiceDefinition.elementTextTrim("wsdlURL"));

			parseServicEndpoints(esb);

			Element eEndpointDefinition = eServiceDefinition.element("endpointDefinition");
			if (eEndpointDefinition != null) {
				esb.setConcreteWSDLURL(eEndpointDefinition.elementTextTrim("concreteWSDLURL"));
				esb.setSoapEndpointURI(eEndpointDefinition.elementTextTrim("soapEndpointURI"));
			}
		}
		parseOperations(eService, esb);

		Element parent = eService.element("parent");
		if (parent != null) {
			if ("serviceGroup".equals(parent.attributeValue("type"))) {
				EsbGrp esbGrp = esbProject.findEsbGrpByQname(parent.attributeValue("qname"));
				esbGrp.addBasicEsbNode(esb);
			} else if ("system".equals(parent.attributeValue("type"))) {
				EsbSys esbSys = esbProject.findEsbSysByQname(parent.attributeValue("qname"));
				if (esbSys != null) {
					esbSys.addBasicEsbNode(esb);
				} else {
					// TODO: doriesiet parsovanie systemu
					EsbSys esbSysNew = new EsbSys(parent.attributeValue("qname"));
					esbSysNew.addBasicEsbNode(esb);
					esbProject.addBasicEsbNode(esbSysNew);
				}
			}
		}

		// /parsovanie inbound adapteru
		Element eInvocation = eService.element("invocation");
		if (eInvocation != null) {
			Element eInterface = eInvocation.element("interface");
			if (eInterface != null) {
				esb.setWsdlURL(eInterface.elementTextTrim("wsdlURL"));
				parseServicEndpoints(esb);
			}

			Element eTargetService = eInvocation.element("targetService");
			if (eTargetService != null) {
				esb.setTargetServiceQName(eTargetService.attributeValue("qname"));
			}
		}
	}

	private final void parseServicEndpoints(EsbSvc esbSvc) {
		File file = null;
		if (esbSvc.getWsdlURL() != null) {
			try {
				file = new File(new URL(esbSvc.getWsdlURL()).getPath());
			} catch (Exception e) {
				file = new File(esbSvc.getProject().getFile() + File.separator + esbSvc.getWsdlURL());
			}
		}

		if (file != null) {
			try {
				parseEndpointType(esbSvc, parseXml(file));
			} catch (ServiceParserException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * parse endpoint type
	 * 
	 * @param esbSvc
	 * @param element
	 */
	private final void parseEndpointType(EsbSvc esbSvc, Element element) {
		if (esbSvc.getServiceSubType() == ServiceSubType.DB) {
			parseEndpointDB(esbSvc, element);
		} else if (esbSvc.getServiceSubType() == ServiceSubType.FTP) {
			parseEndpointFTP(esbSvc, element);
		} else if (esbSvc.getServiceSubType() == ServiceSubType.JMS) {
			parseEndpointJMS(esbSvc, element);
		} else if (esbSvc.getServiceSubType() == ServiceSubType.File) {
			parseEndpointFILE(esbSvc, element);
		} else if (esbSvc.getServiceSubType() == ServiceSubType.DEFAULT) {
			parseEndpointNamespaceType(esbSvc, element);
		}

	}

	/**
	 * parse targetNamespace
	 * 
	 * @param esbSvc
	 * @param element
	 */
	private final void parseEndpointNamespaceType(EsbSvc esbSvc, Element element) {
		String targetNamespace = element.attributeValue("targetNamespace");
		if (targetNamespace != null) {
			if (targetNamespace.contains("/pcbpel/adapter/jms/")) {
				parseEndpointJMS(esbSvc, element);
			} else if (targetNamespace.contains("/pcbpel/adapter/ftp/")) {
				parseEndpointFTP(esbSvc, element);
			} else if (targetNamespace.contains("/pcbpel/adapter/db/")) {
				parseEndpointDB(esbSvc, element);
			} else if (targetNamespace.contains("/pcbpel/adapter/file/")) {
				parseEndpointFILE(esbSvc, element);
			}
		}
	}

	/**
	 * parse endpoint for {@link FileAdapter}
	 * 
	 * @param esbSvc
	 * @param element
	 */
	private final void parseEndpointFILE(EsbSvc esbSvc, Element element) {
		Element eService = element.element("service");
		if (eService != null) {
			Element ePort = eService.element("port");
			if (ePort != null) {
				Element eAddress = ePort.element("address");
				if (eAddress != null) {
					FileAdapter fileAdapter = new FileAdapter(eAddress.attributeValue("location"));
					esbSvc.setEndpoint(fileAdapter);
					fileAdapter.setParentService(esbSvc);

					Element ebinding = element.element("binding");
					if (ebinding != null) {
						Element eOperation = ebinding.element("operation");
						if (eOperation != null) {
							Element eOperation2 = eOperation.element("operation");
							if (eOperation2 != null) {
								fileAdapter.setPhysicalDirectory(eOperation2.attributeValue("PhysicalDirectory"));
								fileAdapter.setActivationSpec(eOperation2.attributeValue("ActivationSpec"));
								fileAdapter.setDeleteFile(eOperation2.attributeValue("DeleteFile"));
								fileAdapter.setPollingFrequency(eOperation2.attributeValue("PollingFrequency"));
								fileAdapter.setMinimumAge(eOperation2.attributeValue("MinimumAge"));
								fileAdapter.setQpaqueSchema(eOperation2.attributeValue("OpaqueSchema"));
								fileAdapter.setFileNamingConvention(eOperation2.attributeValue("FileNamingConvention"));

							}
						}
					}
				}
			}
		}
	}

	/**
	 * parse endpoint for {@link DBAdapter}
	 * 
	 * @param esbSvc
	 * @param element
	 */
	private final void parseEndpointDB(EsbSvc esbSvc, Element element) {
		Element eService = element.element("service");
		if (eService != null) {
			Element ePort = eService.element("port");
			if (ePort != null) {
				Element eAddress = ePort.element("address");
				if (eAddress != null) {

					DBAdapter database = new DBAdapter(eAddress.attributeValue("location"));
					database.setUiConnectionName(eAddress.attributeValue("UIConnectionName"));
					database.setManagedConnectionFactory(eAddress.attributeValue("ManagedConnectionFactory"));

					List<?> attrList = eAddress.attributes();
					if (attrList != null) {
						for (Object o : attrList) {
							Attribute attribute = (Attribute) o;
							if (attribute.getName().contains("DriverClassName")) {
								database.setDriverClassName(attribute.getValue());
							} else if (attribute.getName().contains("PlatformClassName")) {
								database.setPlatformClassName(attribute.getValue());
							} else if (attribute.getName().contains("ConnectionString")) {
								database.setConnectionString(attribute.getValue());
							} else if (attribute.getName().contains("UserName")) {
								database.setUserName(attribute.getValue());
							}
						}
					}

					esbSvc.setEndpoint(database);
					database.setParentService(esbSvc);
				}
			}
		}
	}

	/**
	 * parse endpoint for {@link FTPAdapter}
	 * 
	 * @param esbSvc
	 * @param element
	 */
	private final void parseEndpointFTP(EsbSvc esbSvc, Element element) {
		Element eService = element.element("service");
		if (eService != null) {
			Element ePort = eService.element("port");
			if (ePort != null) {
				Element eAddress = ePort.element("address");
				if (eAddress != null) {
					FTPAdapter ftpRepository = new FTPAdapter(eAddress.attributeValue("location"));
					esbSvc.setEndpoint(ftpRepository);
					ftpRepository.setParentService(esbSvc);

					Element ebinding = element.element("binding");
					if (ebinding != null) {
						Element eOperation = ebinding.element("operation");
						if (eOperation != null) {
							Element eOperation2 = eOperation.element("operation");
							if (eOperation2 != null) {
								ftpRepository.setFileType(eOperation2.attributeValue("FileType"));
								ftpRepository.setPhysicalDirectory(eOperation2.attributeValue("PhysicalDirectory"));
								ftpRepository.setInteractionSpec(eOperation2.attributeValue("InteractionSpec"));
								ftpRepository.setFileNamingConvention(eOperation2.attributeValue("FileNamingConvention"));
								ftpRepository.setNumberMessages(eOperation2.attributeValue("NumberMessages"));
								ftpRepository.setQpaqueSchema(eOperation2.attributeValue("OpaqueSchema"));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * parse endpoint for {@link JMSAdapter}
	 * 
	 * @param esbSvc
	 * @param element
	 */
	private final void parseEndpointJMS(EsbSvc esbSvc, Element element) {
		Element eService = element.element("service");
		if (eService != null) {
			Element ePort = eService.element("port");
			if (ePort != null) {
				Element eAddress = ePort.element("address");
				if (eAddress != null) {
					JMSAdapter jmsAdapter = new JMSAdapter(eAddress.attributeValue("location"));
					jmsAdapter.setManagedConnectionFactory(eAddress.attributeValue("ManagedConnectionFactory"));
					List<?> attrList = eAddress.attributes();
					if (attrList != null) {
						for (Object o : attrList) {
							Attribute attribute = (Attribute) o;
							if (attribute.getName().contains("ConnectionFactoryLocation")) {
								jmsAdapter.setConnectionFactoryLocation(attribute.getValue());
							} else if (attribute.getName().contains("FactoryProperties")) {
								jmsAdapter.setFactoryProperties(attribute.getValue());
							} else if (attribute.getName().contains("IsTopic")) {
								jmsAdapter.setIsTopic(attribute.getValue());
							} else if (attribute.getName().contains("IsTransacted")) {
								jmsAdapter.setIsTransacted(attribute.getValue());
							} else if (attribute.getName().contains("Username")) {
								jmsAdapter.setUsername(attribute.getValue());
							}
						}
					}

					esbSvc.setEndpoint(jmsAdapter);
					jmsAdapter.setParentService(esbSvc);
				}
			}
		}
	}

	/**
	 * parse {@link EsbOperation}
	 * 
	 * @param eService
	 * @param esb
	 */
	@SuppressWarnings("unchecked")
	private final void parseOperations(Element eService, EsbSvc esb) {
		Element eOperations = eService.element("operations");
		if (eOperations != null) {
			List<Element> operations = eOperations.elements("operationInfo");
			if (operations != null) {
				for (Element operation : operations) {
					EsbOperation esbOperation = new EsbOperation(operation.attributeValue("qname"), operation.attributeValue("wsdlOperation"), operation.attributeValue("mep"));
					esb.addEsbOperation(esbOperation);
					parseRoutingRule(esbOperation, operation);
				}
			}
		}
	}

	/**
	 * parse {@link EsbRoutingRule}
	 * 
	 * @param esbOperation
	 * @param operation
	 */
	@SuppressWarnings("unchecked")
	private final void parseRoutingRule(EsbOperation esbOperation, Element operation) {
		Element eRoutingRules = operation.element("routingRules");
		if (eRoutingRules != null) {
			List<Element> routingRules = eRoutingRules.elements("routingRule");
			if (routingRules != null) {
				for (Element routingRule : routingRules) {

					Element targetOperation = routingRule.element("targetOperation");
					if (targetOperation != null) {
						EsbRoutingRule esbRoutingRule = new EsbRoutingRule(targetOperation.attributeValue("qname"), targetOperation.attributeValue("serviceQName"));
						esbOperation.addRoutingRule(esbRoutingRule);
					}
				}
			}

		}
	}

	/**
	 * parse WSDL and find BPEL
	 * 
	 * @param partnerLinkBinding
	 */
	public final String createEsbSystemUrl(PartnerLinkBinding partnerLinkBinding) {
		try {
			URL url = new URL(partnerLinkBinding.getWsdlLocation());

			if (url.getFile().startsWith("/esb/wsil/")) {
				return url.getFile().replace("/esb/wsil/", "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * convert WSDL to qName
	 * 
	 * @param wsdlLocation
	 * @return
	 */
	public final String convertWsdlToQname(URL url) {
		try {
			if (url.getFile().startsWith("/esb/wsil/")) {
				String urlQname = url.getFile().replace("/esb/wsil/", "");
				if (urlQname.endsWith("?wsdl")) {
					return urlQname.substring(0, urlQname.lastIndexOf("?wsdl")).replace("/", ".");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
