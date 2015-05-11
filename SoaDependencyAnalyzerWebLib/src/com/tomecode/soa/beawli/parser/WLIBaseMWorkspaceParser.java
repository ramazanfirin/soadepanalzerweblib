package com.tomecode.soa.beawli.parser;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import avea.bpm.arch.model.Process;
import avea.bpm.arch.model.Service;
import avea.bpm.codeanalyzer.runner.ConsoleRunner;
import avea.bpm.codeanalyzer.runner.CoreRunner;

import com.tomecode.soa.beawli.project.WliBaseProject;
import com.tomecode.soa.beawli.services.WliBaseProcess;
import com.tomecode.soa.beawli.services.WliBaseService;
import com.tomecode.soa.beawli.workspace.WliBaseMultiWorkspace;
import com.tomecode.soa.beawli.workspace.WliBaseWorkspace;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.ResourceJCA;
import com.tomecode.soa.ora.osb10g.services.UnknownService;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJca;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.protocols.jca.JCAAdapterType;
import com.tomecode.soa.services.jca.JCABase;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

public final class WLIBaseMWorkspaceParser extends AbstractParser {
	private final WLIBaseProjectParser projectParser;

	public WLIBaseMWorkspaceParser() {
		this.projectParser = new WLIBaseProjectParser();
	}

	public final void parse(WliBaseMultiWorkspace multiWorkspace, List<String> workspacePaths) {
		for (String path : workspacePaths) {
			File f = new File(path);
			multiWorkspace.addWorkspace(parse2(f));
		}

		analyzeDependnecies(multiWorkspace);
	}

	private final WliBaseWorkspace parse2(File file) {
		WliBaseWorkspace workspace = new WliBaseWorkspace(file.getName(), file);

		if (containsProjectFiles(file)) {
			WliBaseProject project = this.projectParser.parseProject(file);
			workspace.addProject(project);
		} else {
			File[] files = file.listFiles();
			if (files != null) {
				for (File projectFile : files) {
					if (projectFile.isDirectory()) {
						WliBaseProject project = this.projectParser.parseProject(projectFile);
						workspace.addProject(project);
					}
				}
			}
		}
		postParseEndpoints(workspace);
		return workspace;
	}

	public final void parse(MultiWorkspace multiWorkspace) {
	/*	
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				WliBaseProject oraSB10gProject = (WliBaseProject) project;
				this.projectParser.parse(oraSB10gProject);
			}
			postParseEndpoints(workspace);
		}
		analyzeDependnecies((WliBaseMultiWorkspace) multiWorkspace);
		?/
		
	*/
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			File file = workspace.getFile();
		
			CoreRunner runner = new CoreRunner();
			try {
				Map<String, List> processMap = runner.run(file.getParent().toString(),file.getParent().toString()+"3");
				System.out.println(processMap.size());
				
				
				for (Project project : workspace.getProjects()) {
					WliBaseProject wliProject = (WliBaseProject) project;
					List<Process> pPrList =  processMap.get(wliProject.getName());
					if(null!=pPrList)
					for (Process pPr : pPrList) {
						WliBaseProcess process = new WliBaseProcess(pPr.getName());
						process.setInput( pPr.getInput());
						process.setOutput( pPr.getOutput());
						Set services = pPr.getServices();
						Iterator it =  services.iterator();
						while(it.hasNext()){
							Service srv = (Service) it.next();
							WliBaseService service = new WliBaseService();
							service.setName(srv.getIwisName());
							process.addService(service);
							process.setReturnCodes(pPr.getReturnCodes());
						}
						wliProject.addProcess(process);
					}				 
					//wliProject.addProcess(process)
					System.out.println(wliProject.getName());
					
				}

				
				
				/*
				System.out.println(processList.size());
				
				
				for(int  i=0;i<processList.size();i++){
					Process process = (Process)processList.get(i);
					System.out.println(process.getName());
					
					Set services = process.getServices();
					Iterator it =  services.iterator();
					while(it.hasNext()){
						Service srv = (Service) it.next();
						System.out.println(srv.getIwisName());
					}
					
				}
				*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

	private final void postParseEndpoints(Workspace workspace) {
//		for (Project project : workspace.getProjects()) {
//			OraSB10gProject sb10gProject = (OraSB10gProject) project;
//			for (OSBService service : sb10gProject.getServices()) {
//				if (service.getEndpointConfig() == null) {
//					continue;
//				}
//				if (service.getEndpointConfig().getProtocol() == EndpointConfig.ProviderProtocol.JCA) {
//					EndpointJca endpointJca = (EndpointJca) service.getEndpointConfig();
//
//					String projectName = parseProjectNameFormRef(endpointJca.getRefJcaFile());
//					if (projectName == null)
//						continue;
//					OraSB10gProject oraSB10gProject = findProjectByName(workspace, projectName);
//					if (oraSB10gProject == null)
//						continue;
//					String refPath = parsePathFormRef(endpointJca.getRefJcaFile());
//
//					JCABase jcaBase = findJCAinProject(endpointJca.getName(), endpointJca.getJcaAdapterType(), endpointJca.getOperation(), oraSB10gProject.getServices());
//					if (jcaBase != null)
//						endpointJca.setJCAbase(jcaBase);
//				} else {
//					service.getEndpointConfig().getProtocol();
//				}
//			}
//		}
	}

	private final JCABase findJCAinProject(String name, JCAAdapterType jcaAdapterType, String operation, List<OSBService> services) {
		for (OSBService service : services) {
			if (service.getDependencyType() == ServiceDependency.ServiceDependencyType.JCA) {
				JCABase jcaBase = ((ResourceJCA) service).getJcaBase();
				if ((jcaBase.getAdapterType() == jcaAdapterType) && (jcaBase.getName().equals(name)) && (jcaBase.containsOperation(operation))) {
					return jcaBase;
				}
			}
		}
		return null;
	}

	private final String parseProjectNameFormRef(String refJcaFile) {
		if (refJcaFile == null) {
			return null;
		}
		int index = refJcaFile.indexOf("/");
		if (index != -1) {
			return refJcaFile.substring(0, index);
		}
		return null;
	}

	private final String parsePathFormRef(String refJcaFile) {
		if (refJcaFile == null) {
			return null;
		}
		int index = refJcaFile.indexOf("/");
		if (index != -1) {
			refJcaFile = refJcaFile.substring(0, index);
			index = refJcaFile.lastIndexOf("/");
			if (index == -11) {
				return refJcaFile.substring(0, index);
			}
		}
		return null;
	}

	private final boolean containsProjectFiles(File projectFile) {
		File[] files = projectFile.listFiles();
		if (files != null) {
			for (File f : files) {
				if ((f.isDirectory()) && (f.getName().equalsIgnoreCase(".settings")))
					return true;
				if ((f.isFile()) && (f.getName().equalsIgnoreCase(".project"))) {
					return true;
				}
			}
		}
		return false;
	}

	public  OSBService findServiceWithName(MultiWorkspace multiWorkspace,String name) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;

				if (!oraSB10gProject.isFolder()) {
					for (OSBService service : oraSB10gProject.getServices()) {
						if(service.getName()!=null && service.getDependencyType() == ServiceDependency.ServiceDependencyType.PROXY_SERVICE){
							name = name.toLowerCase().replaceFirst("pl_", "");
							if( (name.equalsIgnoreCase(service.getName()) || name.equalsIgnoreCase(service.getName().toLowerCase().replace("_ps", "")) || name.equalsIgnoreCase(service.getName().toLowerCase().replace("_ws_ps", "")) ))
							return service;
						}
					}
				} else {
					for (OSBService service : oraSB10gProject.getServices()) {	
						if(service.getName()!=null && service.getDependencyType() == ServiceDependency.ServiceDependencyType.PROXY_SERVICE){
							name = name.toLowerCase().replaceFirst("pl_", "");
							if( (name.equalsIgnoreCase(service.getName()) || name.equalsIgnoreCase(service.getName().toLowerCase().replace("_ps", "")) || name.equalsIgnoreCase(service.getName().toLowerCase().replace("_ws_ps", "")) ))
							return service;
						}
					}
				}
			}
		}
		return null;
	}
	
	
	
	public final void analyzeDependnecies(WliBaseMultiWorkspace multiWorkspace) {
		
	}

	private final OSBService findServiceByPath(OraSB10gMultiWorkspace multiWorkspace, ServiceDependency serviceDependency) {
		String[] paths = serviceDependency.toString().split("/");
		String serviceName = paths[(paths.length - 1)];
		String serviceName2 = serviceName.replaceAll(" ", "_");

		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;
				for (OSBService service : oraSB10gProject.getServices()) {
					if (!service.getDependencyType().equals(serviceDependency.getType()))
						continue;
					if ((!service.getName().equals(serviceName)) && (!service.getName().replaceAll(" ", "_").equals(serviceName2)))
						continue;
					try {
						if (service.getFolder().getPath().equals(serviceDependency.getRefPathWithoutServiceName()))
							return service;
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}

		}

		return null;
	}

	private final OSBService findServiceInJarProject(OraSB10gProject project, ServiceDependency serviceDependency) {
		for (OSBService service : project.getServices()) {
			if (service.getDependencyType() != serviceDependency.getType()) {
				continue;
			}
			String serviceDepName = serviceDependency.getServiceName();
			if ((service.getName().equals(serviceDepName)) || (service.getName().replaceAll(" ", "_").equals(serviceDepName.replaceAll(" ", "_")))) {
				String refPath = serviceDependency.getRefPath();
				if (refPath == null) {
					return service;
				}

				if ((service.getFolder() != null) && (service.getFolder().getPath().equals(serviceDependency.getRefPathWithoutServiceName()))) {
					return service;
				}

			}

		}

		return null;
	}

	private final void addUnknowService(ServiceDependencies serviceDependencies, OraSB10gProject depProject, ServiceDependency serviceDependency, OSBService service) {
	
	}

	private final OSBService findService(OraSB10gProject project, String serviceName, String realPath, ServiceDependency.ServiceDependencyType type) {
		String serviceName2 = serviceName.replaceAll(" ", "_");
		for (OSBService service : project.getServices()) {
			if (service.getDependencyType() != type)
				continue;
			try {
				if (service.getOrginalName() != null && (serviceName.equals(service.getOrginalName()) || serviceName2.equals(service.getOrginalName().replaceAll(" ", "_")))) {
					if (realPath == null) {
						return service;
					}
					if (service.getFolder().getPath().equals(realPath)) {
						return service;
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	private final OraSB10gProject findProjectByName(Workspace workspace, String projectName) {
		for (Project project : workspace.getProjects()) {
			if (project.getName().equals(projectName)) {
				return (OraSB10gProject) project;
			}
		}
		return null;
	}

	public final WliBaseProject addNewProject(boolean asFolder, File path) {
		if (asFolder) {
			return this.projectParser.parseProject(path);
		}
		return this.projectParser.parseJar(path);
	}
}