package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;
import java.util.List;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.ResourceJCA;
import com.tomecode.soa.ora.osb10g.services.UnknownService;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJca;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.protocols.jca.JCAAdapterType;
import com.tomecode.soa.services.jca.JCABase;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle Service Bus 10g Multi Workspace parser
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class OraSB10gMWorkspaceParser extends AbstractParser {

	/**
	 * parse for Oracle Service Bus 10g projects
	 */
	private final OraSB10gProjectParser projectParser;

	public OraSB10gMWorkspaceParser() {
		projectParser = new OraSB10gProjectParser();
	}

	/**
	 * parse new multi-workspace
	 * 
	 * @param multiWorkspace
	 * @param workspacePaths
	 */
	public final void parse(OraSB10gMultiWorkspace multiWorkspace, List<String> workspacePaths) {
		for (String path : workspacePaths) {
			File f = new File(path);
			multiWorkspace.addWorkspace(parse2(f));
		}

		analyzeDependnecies(multiWorkspace);
		System.out.println("test");
	}

	private final OraSB10gWorkspace parse2(File file) {
		OraSB10gWorkspace workspace = new OraSB10gWorkspace(file.getName(), file);

		if (containsProjectFiles(file)) {
			OraSB10gProject project = projectParser.parseProject(file);
			workspace.addProject(project);
		} else {

			File[] files = file.listFiles();
			if (files != null) {
				for (File projectFile : files) {
					if (projectFile.isDirectory()) {
						OraSB10gProject project = projectParser.parseProject(projectFile);
						workspace.addProject(project);
					}
				}
			}
		}
		postParseEndpoints(workspace);
		return workspace;
	}

	/**
	 * parse {@link MultiWorkspace}
	 * 
	 * @param multiWorkspace
	 */
	public final void parse(MultiWorkspace multiWorkspace) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {

			for (Project project : workspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;
				projectParser.parse(oraSB10gProject);
			}
			postParseEndpoints(workspace);
		}
		analyzeDependnecies((OraSB10gMultiWorkspace) multiWorkspace);
	}

	/**
	 * post-parser: parse special end point (JCA,flow,etc.) in projects
	 * 
	 * @param workspace
	 */
	private final void postParseEndpoints(Workspace workspace) {
		for (Project project : workspace.getProjects()) {
			OraSB10gProject sb10gProject = (OraSB10gProject) project;
			for (OSBService service : sb10gProject.getServices()) {
				if (service.getEndpointConfig() == null) {
					continue;
				}
				if (service.getEndpointConfig().getProtocol() == ProviderProtocol.JCA) {
					EndpointJca endpointJca = (EndpointJca) service.getEndpointConfig();

					String projectName = parseProjectNameFormRef(endpointJca.getRefJcaFile());
					if (projectName != null) {

						OraSB10gProject oraSB10gProject = findProjectByName(workspace, projectName);
						if (oraSB10gProject != null) {

							String refPath = parsePathFormRef(endpointJca.getRefJcaFile());
							if (refPath != null) {
								// TODO: dokoncit parsovanie pre ref path co ak
								// nie je null!!!!!
								// System.out.println("------------------------refPath : "
								// + refPath);
							}

							JCABase jcaBase = findJCAinProject(endpointJca.getName(), endpointJca.getJcaAdapterType(), endpointJca.getOperation(), oraSB10gProject.getServices());
							if (jcaBase != null) {
								endpointJca.setJCAbase(jcaBase);
							}
						}
					}
				} else if (service.getEndpointConfig().getProtocol() == ProviderProtocol.FLOW) {
					// TODO: parsovanie FLOW v OSB 10g/11g
					// projectParser.getBasicServiceParser().parseFlowAdapter(service);
				}
			}
		}

	}

	/**
	 * find {@link JCABase} adapter in project
	 * 
	 * @param name
	 *            JCA adapter name
	 * @param jcaAdapterType
	 *            JCA adapter type
	 * @param operation
	 *            JCA operation
	 * @return
	 */
	private final JCABase findJCAinProject(String name, JCAAdapterType jcaAdapterType, String operation, List<OSBService> services) {
		for (OSBService service : services) {
			if (service.getDependencyType() == ServiceDependencyType.JCA) {
				JCABase jcaBase = ((ResourceJCA) service).getJcaBase();
				if (jcaBase.getAdapterType() == jcaAdapterType && jcaBase.getName().equals(name) && jcaBase.containsOperation(operation)) {
					return jcaBase;
				}
			}
		}
		return null;
	}

	// private final Service findJCAInProject(List<Service> services,
	// ServiceDependencyType type) {
	// for (Service service : services) {
	// if (type == service.getType()) {
	// return service;
	// }
	// }
	// return null;
	// }

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

	/**
	 * contains whether project folder contains .settings or .project
	 * 
	 * @param projectFile
	 * @return
	 */
	private final boolean containsProjectFiles(File projectFile) {
		File[] files = projectFile.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory() && f.getName().equalsIgnoreCase(".settings")) {
					return true;
				} else if (f.isFile() && f.getName().equalsIgnoreCase(".project")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Analyze dependencies in projects
	 * 
	 * @param multiWorkspace
	 */
	public final void analyzeDependnecies(OraSB10gMultiWorkspace multiWorkspace) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {

			for (Project project : workspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;

				// analyze dependencies in jar project
				if (!oraSB10gProject.isFolder()) {

					for (OSBService service : oraSB10gProject.getServices()) {

						ServiceDependencies serviceDependencies = service.getServiceDependencies();
						for (ServiceDependency serviceDependency : serviceDependencies.getDependnecies()) {
							// find dependency in selected project
							OSBService targetService = findServiceInJarProject(oraSB10gProject, serviceDependency);
							if (targetService != null) {
								serviceDependency.addService(targetService);
								targetService.addReferencedByService(service);
								service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
							} else {

								// find dependency in others projects

								targetService = findServiceByPath(multiWorkspace, serviceDependency);
								if (targetService != null) {
									serviceDependency.addService(targetService);
									targetService.addReferencedByService(service);
									service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
								} else {
									addUnknowService(serviceDependencies, oraSB10gProject, serviceDependency, service);
								}
							}
						}

					}

				} else {

					// analyze dependencies in project as folder (workspace)
					for (OSBService service : oraSB10gProject.getServices()) {

						ServiceDependencies serviceDependencies = service.getServiceDependencies();
						for (ServiceDependency serviceDependency : serviceDependencies.getDependnecies()) {
							// name of dependency project
							String depProjectName = serviceDependency.getProjectNameFromRefPath();
							if (depProjectName != null) {
								OraSB10gProject depProject = findProjectByName(workspace, depProjectName);
								if (depProject != null) {

									if (serviceDependency.getType() == ServiceDependencyType.WSDL) {
										// TODO: osetrit zavislosti pri
										// SPLITJOIN pre WSDL
									}

									OSBService targetService = findService(depProject, serviceDependency.getServiceName(), serviceDependency.getRealPath(), serviceDependency.getType());
									if (targetService == null) {
										addUnknowService(serviceDependencies, depProject, serviceDependency, service);
									} else {
										serviceDependency.addService(targetService);
										targetService.addReferencedByService(service);
										service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
									}
								} else {
									OSBService targetService = findServiceByPath(multiWorkspace, serviceDependency);
									if (targetService == null) {
										addUnknowService(serviceDependencies, depProject, serviceDependency, service);
									} else {
										serviceDependency.addService(targetService);
										targetService.addReferencedByService(service);
										service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
									}

								}
							}
						}

					}

				}

			}

		}
	}

	/**
	 * find service by name in all projects
	 * 
	 * @param multiWorkspace
	 * @param serviceDependency
	 * @return
	 */
	private final OSBService findServiceByPath(OraSB10gMultiWorkspace multiWorkspace, ServiceDependency serviceDependency) {

		String[] paths = serviceDependency.toString().split("/");
		String serviceName = paths[paths.length - 1];
		String serviceName2 = serviceName.replaceAll(" ", "_");

		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;
				for (OSBService service : oraSB10gProject.getServices()) {

					if (service.getDependencyType().equals(serviceDependency.getType())) {

						if (service.getName().equals(serviceName) || service.getName().replaceAll(" ", "_").equals(serviceName2)) {

							try {
								if (service.getFolder().getPath().equals(serviceDependency.getRefPathWithoutServiceName())) {
									return service;
								}
							} catch (Exception e) {
								// TODO: error handler
								e.printStackTrace();
							}

						}

					}

				}
			}

		}
		return null;
	}

	/**
	 * find service in project (from jar)
	 * 
	 * @param project
	 * @param serviceDependency
	 * @return
	 */
	private final OSBService findServiceInJarProject(OraSB10gProject project, ServiceDependency serviceDependency) {

		// String serviceDependencyName = serviceDependency.getServiceName();
		// String sserviceDependencyName2 =
		// serviceDependencyName.replaceAll(" ", "_");

		for (OSBService service : project.getServices()) {
			if (service.getDependencyType() == serviceDependency.getType()) {

				// String serviceName = service.getName().replace(" ", "_");

				String serviceDepName = serviceDependency.getServiceName();
				if (service.getName().equals(serviceDepName) || service.getName().replaceAll(" ", "_").equals(serviceDepName.replaceAll(" ", "_"))) {
					String refPath = serviceDependency.getRefPath();
					if (refPath == null) {
						return service;
					} else {

						if (service.getFolder() != null) {
							if (service.getFolder().getPath().equals(serviceDependency.getRefPathWithoutServiceName())) {
								return service;
							}
						}
					}
				}
			}
		}
		return null;
	}

	private final void addUnknowService(ServiceDependencies serviceDependencies, OraSB10gProject depProject, ServiceDependency serviceDependency, OSBService service) {
		UnknownService unknownService = serviceDependencies.findUnknownService(depProject, serviceDependency.getServiceName());
		unknownService = (unknownService == null ? new UnknownService(depProject, serviceDependency.getServiceName()) : unknownService);
		serviceDependency.addService(unknownService);
		unknownService.addReferencedByService(service);
		service.getActivityDependency().addDependecy(serviceDependency.getActivity(), unknownService);
	}

	/**
	 * find {@link OSBService} in project by parameter
	 * 
	 * @param project
	 * @param serviceName
	 * @param realPath
	 * @param type
	 * @return
	 */
	private final OSBService findService(OraSB10gProject project, String serviceName, String realPath, ServiceDependencyType type) {

		String serviceName2 = serviceName.replaceAll(" ", "_");
		for (OSBService service : project.getServices()) {
			if (service.getDependencyType() == type) {
				try {
					if (service.getOrginalName() != null && (serviceName.equals(service.getOrginalName()) || serviceName2.equals(service.getOrginalName().replaceAll(" ", "_")))) {
						if (realPath == null) {

							// System.out.println("match: " +
							// service.toString());
							return service;
						} else {
							if (service.getFolder().getPath().equals(realPath)) {
								return service;
							}
						}
					}
				} catch (Exception e) {
					// System.out.println("serviceName: " + serviceName);
					// System.out.println("realPath: " + realPath);
					// System.out.println("serviceDependencyType: " + type);
					e.printStackTrace();
				}

			}
		}
		return null;
	}

	
	/**
	 * find {@link Project} by name in {@link Workspace}
	 * 
	 * @param workspace
	 * @param projectName
	 * @return
	 */
	private final OraSB10gProject findProjectByName(Workspace workspace, String projectName) {
		for (Project project : workspace.getProjects()) {
			if (project.getName().equals(projectName)) {
				return (OraSB10gProject) project;
			}
		}
		return null;
	}

	/**
	 * parse new project
	 * 
	 * @param asFolder
	 * @param path
	 * @return
	 */
	public final OraSB10gProject addNewProject(boolean asFolder, File path) {
		if (asFolder) {
			return projectParser.parseProject(path);
		}
		return projectParser.parseJar(path);
	}

	public OSBService findServiceWithName(MultiWorkspace multiWorkspace, String name) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;

				if (!oraSB10gProject.isFolder()) {
					for (OSBService service : oraSB10gProject.getServices()) {
						if (service.getName() != null && service.getDependencyType() == ServiceDependency.ServiceDependencyType.PROXY_SERVICE) {
							name = name.toLowerCase().replaceFirst("pl_", "");
							if ((name.equalsIgnoreCase(service.getName()) || name.equalsIgnoreCase(service.getName().toLowerCase().replace("_ps", "")) || name.equalsIgnoreCase(service.getName().toLowerCase().replace("_ws_ps", ""))))
								return service;
						}
					}
				} else {
					for (OSBService service : oraSB10gProject.getServices()) {
						if (service.getName() != null && service.getDependencyType() == ServiceDependency.ServiceDependencyType.PROXY_SERVICE) {
							name = name.toLowerCase().replaceFirst("pl_", "");
							if ((name.equalsIgnoreCase(service.getName()) || name.equalsIgnoreCase(service.getName().toLowerCase().replace("_ps", "")) || name.equalsIgnoreCase(service.getName().toLowerCase().replace("_ws_ps", ""))))
								return service;
						}
					}
				}
			}
		}
		return null;
	}

}
