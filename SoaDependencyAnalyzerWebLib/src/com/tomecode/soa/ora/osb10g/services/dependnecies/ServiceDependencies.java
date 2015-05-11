package com.tomecode.soa.ora.osb10g.services.dependnecies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.tools.ReferencedBy;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.UnknownService;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Main object. Contains all dependencies from {@link #parent} project
 * 
 * @author Tomas Frastia
 * 
 */
public final class ServiceDependencies implements Serializable{

	private final OSBService parent;

	/**
	 * list of dependencies
	 */
	private final List<ServiceDependency> dependnecies;

	/**
	 * Constructor
	 * 
	 * @param parent
	 */
	public ServiceDependencies(OSBService parent) {
		this.parent = parent;
		this.dependnecies = new ArrayList<ServiceDependency>();
	}

	/**
	 * @return the parent
	 */
	public final OSBService getParent() {
		return parent;
	}

	/**
	 * @return the dependnecies
	 */
	public final List<ServiceDependency> getDependnecies() {
		return dependnecies;
	}

	public void addDependency(ServiceDependency dependency) {
		this.dependnecies.add(dependency);
	}

	public final UnknownService findUnknownService(Project depProject, String serviceName) {
		for (ServiceDependency serviceDependency : dependnecies) {
			for (ReferencedBy refBy : serviceDependency.getServices()) {
				if(refBy instanceof OSBService){
					OSBService service = (OSBService)refBy;
					if (service.getDependencyType() == ServiceDependencyType.UNKNOWN) {
						UnknownService unknownService = (UnknownService) service;
						if (unknownService.getProject() != null) {
							if (unknownService.getProject().equals(depProject) && unknownService.getName().equals(serviceName)) {
								return unknownService;
							}
						}
					}
				}
			}
		}

		return null;
	}
}
