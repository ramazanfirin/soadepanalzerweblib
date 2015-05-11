package com.tomecode.soa.dependency.analyzer.tools;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * this class contains informations about endpoints in service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class EndpointList {

	private final List<EndpointListProject> endpointListProjects;

	public EndpointList() {
		this.endpointListProjects = new ArrayList<EndpointList.EndpointListProject>();
	}

	public final List<EndpointListProject> getEndpointListProjects() {
		return endpointListProjects;
	}

	public final EndpointListProject findProject(Project project) {
		for (EndpointListProject endpointListProject : endpointListProjects) {
			if (endpointListProject.getProject().equals(project)) {
				return endpointListProject;
			}
		}

		EndpointListProject endpointListProject = new EndpointListProject(project);
		this.endpointListProjects.add(endpointListProject);
		return endpointListProject;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * this class contain project and services inside the project
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public static final class EndpointListProject {

		private Project project;

		private final List<EndpointListService> endpointListServices;

		public EndpointListProject(Project project) {
			this.endpointListServices = new ArrayList<EndpointList.EndpointListService>();
			this.project = project;
		}

		/**
		 * @return the project
		 */
		public final Project getProject() {
			return project;
		}

		public final List<EndpointListService> getEndpointListServices() {
			return this.endpointListServices;
		}

		public final void addEndpointListService(Object service, String protocol, List<String> uris) {
			EndpointListService endpointListService = findService(service);
			endpointListService.protocol = protocol;
			endpointListService.uris.addAll(uris);

		}

		public final void addEndpointListService(Object service, String protocol, String uri) {
			EndpointListService endpointListService = findService(service);
			endpointListService.protocol = protocol;
			endpointListService.uris.add(uri);
		}

		private final EndpointListService findService(Object service) {
			for (EndpointListService listService : endpointListServices) {
				if (listService.getService().equals(service)) {
					return listService;
				}
			}
			EndpointListService endpointListService = new EndpointListService(this, service);
			this.endpointListServices.add(endpointListService);
			return endpointListService;
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * this class contains informations about service, protocol, and uri
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public static class EndpointListService {

		private final EndpointListProject endpointListProject;

		// TODO: zjednotenie servicov do jedneho base typu
		private final Object service;

		private String protocol;

		private final List<String> uris;

		public EndpointListService(EndpointListProject endpointListProject, Object service) {
			this.uris = new ArrayList<String>();
			this.service = service;
			this.endpointListProject = endpointListProject;
		}

		public final EndpointListProject getEndpointListProject() {
			return this.endpointListProject;
		}

		/**
		 * @return the service
		 */
		public final Object getService() {
			return service;
		}

		/**
		 * @return the protocol
		 */
		public final String getProtocol() {
			return protocol;
		}

		/**
		 * @return the uris
		 */
		public final List<String> getUris() {
			return uris;
		}

	}
}
