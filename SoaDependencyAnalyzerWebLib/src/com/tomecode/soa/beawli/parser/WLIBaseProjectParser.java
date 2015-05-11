package com.tomecode.soa.beawli.parser;

import java.io.File;

import com.tomecode.soa.beawli.project.WliBaseProject;
import com.tomecode.soa.beawli.services.WliBaseFolder;
import com.tomecode.soa.beawli.services.WliBaseProcess;
import com.tomecode.soa.beawli.services.WliBaseService;

public final class WLIBaseProjectParser {
	//private final JcaAdapterParser jcaAdapterParser;
	private final WLIBaseProxyParser proxyParser;
//	private final OraSB10gSplitJoinParser splitJoinParser;
//	private final OraSB10gBusinessServiceParser businessServiceParser;

	public WLIBaseProjectParser() {
	//	this.jcaAdapterParser = new JcaAdapterParser();
		this.proxyParser = new WLIBaseProxyParser();
//		this.splitJoinParser = new OraSB10gSplitJoinParser();
//		this.businessServiceParser = new OraSB10gBusinessServiceParser();
	}

	public final void parse(WliBaseProject project) {
		if (project.isFolder())
			executeParse(project.getFile(), project);
//		else
//			executeParserJar(project.getFile(), project);
	}

	public final WliBaseProject parseJar(File jar) {
		WliBaseProject project = new WliBaseProject(jar, false);
		//executeParserJar(jar, project);
		return project;
	}



	public final WliBaseProject parseProject(File projectFolder) {
		WliBaseProject project = new WliBaseProject(projectFolder, true);
		executeParse(projectFolder, project);
		return project;
	}

	private final void executeParse(File projectFolder, WliBaseProject project) {
		File[] files = projectFolder.listFiles();
		if (files != null)
			for (File file : files)
				if ((file.isDirectory()) && (!file.getName().equals(".settings")) && (!file.getName().equals(".workshop")) && (!file.getName().equals("APP-INF")) && (!file.getName().equals("AveaSchemaService")) && (!file.getName().equals("META-INF"))) {
					String path = file.getPath().replace(project.getFile().toString(), "");
					if (path.startsWith(File.separator)) {
						path = path.substring(1);
					}

					WliBaseFolder folder = new WliBaseFolder(project, file, path, file.getName());
					//project.getFolders().addFolder(folder);
					//parseFolders(project, folder, file);
				} else if (file.isFile()) {
					WliBaseProcess service = parseBaseProcess(file);
					if (service != null) {
					project.getProcessList().add(service);
						//project.getFolders().addToAllServices((WliBaseService)service);
					}
				}
	}

//	private final WliBaseService parseService(File file) {
//		String name = file.getName().toLowerCase();
//
//		if ((name.endsWith(".jcs")) || (name.endsWith(".proxyservice"))) {
//			try {
//				return this.proxyParser.parseJcs(file);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//
////		} else if (name.endsWith(".flow"))
////			try {
////				return this.splitJoinParser.parseSplitJoin(file);
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////		else if ((name.endsWith(".biz")) || (name.endsWith(".businessservice"))) {
////			try {
////				return this.businessServiceParser.parseBusinessService(file);
////			} catch (ServiceParserException e) {
////				e.printStackTrace();
////			}
//
//		} else if ((name.equals(".project")) || (name.equals(".classpath")) || (name.equals(".factorypath")) || (name.equals("_projectdata.locationdata")) || (name.equals("_folderdata.locationdata"))) {
//			return null;
//		}
//
//		return null;
//		//return parseResourceFile(file);
//	}

	
	private final WliBaseProcess parseBaseProcess(File file) {
		String name = file.getName().toLowerCase();

		if ((name.endsWith(".jcs")) || (name.endsWith(".proxyservice"))) {
			try {
				return this.proxyParser.parseJcs(file);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

//		} else if (name.endsWith(".flow"))
//			try {
//				return this.splitJoinParser.parseSplitJoin(file);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		else if ((name.endsWith(".biz")) || (name.endsWith(".businessservice"))) {
//			try {
//				return this.businessServiceParser.parseBusinessService(file);
//			} catch (ServiceParserException e) {
//				e.printStackTrace();
//			}

		} else if ((name.equals(".project")) || (name.equals(".classpath")) || (name.equals(".factorypath")) || (name.equals("_projectdata.locationdata")) || (name.equals("_folderdata.locationdata"))) {
			return null;
		}

		return null;
		//return parseResourceFile(file);
	}
	
	
//	private final OSBService parseResourceFile(File file) {
//		String name = file.getName().toLowerCase();
//		if ((name.endsWith(".xq")) || (name.endsWith(".xquery")))
//			return new ResourceFile(file, ServiceDependency.ServiceDependencyType.XQUERY);
//		if (name.endsWith(".wsdl"))
//			return new ResourceFile(file, ServiceDependency.ServiceDependencyType.WSDL);
//		if ((name.endsWith(".xmlschema")) || (name.endsWith(".xsd")))
//			return new ResourceFile(file, ServiceDependency.ServiceDependencyType.XML_SCHEMA);
//		if (name.endsWith(".xml"))
//			return new ResourceFile(file, ServiceDependency.ServiceDependencyType.XML);
//		if ((name.endsWith(".serviceaccount")) || (name.endsWith(".sa")))
//			return new ResourceFile(file, ServiceDependency.ServiceDependencyType.SERVICE_ACCOUNT);
//		if (name.endsWith(".archive"))
//			return new ResourceFile(file, ServiceDependency.ServiceDependencyType.ARCHIVE);
//		if (name.endsWith(".skp"))
//			return new ResourceFile(file, ServiceDependency.ServiceDependencyType.SKP);
//		if (name.endsWith(".jar"))
//			return new ResourceFile(file, ServiceDependency.ServiceDependencyType.JAR);
//		if (name.endsWith(".jca")) {
//			return new ResourceJCA(file, ServiceDependency.ServiceDependencyType.JCA, this.jcaAdapterParser.parseJca(file));
//		}
//		return new UnknownFile(file);
//	}

//	private final OSBService parseServiceInJar(File file, InputStream fileStream) {
//		String name = file.getName().toLowerCase();
//		if ((name.endsWith(".proxy")) || (name.endsWith(".proxyservice"))) {
//			try {
//				return this.proxyParser.parseProxy(file, fileStream);
//			} catch (ServiceParserException e) {
//				e.printStackTrace();
//			}

//		} else if (name.endsWith(".flow"))
//			try {
//				return this.splitJoinParser.parseSplitJoin(file, fileStream);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		else if ((name.endsWith(".biz")) || (name.endsWith("businessservice"))) {
//			try {
//				return this.businessServiceParser.parseBusinessService(file, fileStream);
//			} catch (ServiceParserException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return parseResourceFile(file);
//	}

//	private final void parseFolders(WliBaseProject project, WliBaseFolder root, File parentFile) {
//		File[] files = parentFile.listFiles();
//		if (files != null)
//			for (File file : files)
//				if ((file.isDirectory()) && (!file.getName().equals(".settings"))) {
//					WliBaseFolder folder = new WliBaseFolder(project, file, root.getPath() + "/" + file.getName(), file.getName());
//					root.addFolder(folder);
//					parseFolders(project, folder, file);
//				} else {
//					WliBaseService service = parseService(file);
//					if (service != null) {
//						//service.setFolder(root);
//						root.addService(service);
//						project.getFolders().addToAllServices(service);
//					}
//				}
//	}
}