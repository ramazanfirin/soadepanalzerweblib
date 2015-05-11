package com.tomecode.soa.ora.osb10g.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.dependency.analyzer.tools.ToolOsbList.HeaderInActivity;
import com.tomecode.soa.dependency.analyzer.tools.ToolOsbList.OsbHeaderList;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolders;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.SplitJoin;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle Service Bus 10g project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Project...", type = "Oracle Service Bus 10g/11g", parentMethod = "getWorkpsace")
public final class OraSB10gProject implements Project {

	/**
	 * if is true then this project is JAR file
	 */
	private boolean folder;
	/**
	 * project file or folder
	 */
	private File file;
	/**
	 * parent {@link Workspace}
	 */
	private OraSB10gWorkspace workspace;

	private OraSB10gFolders folders;

	private final List<OSBService> services;

	private final List<OSBService> servicesWithFlow;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            project file or folder
	 * @param isFolder
	 */
	public OraSB10gProject(File file, boolean isFolder) {
		this.file = file;
		this.folder = isFolder;
		this.services = new ArrayList<OSBService>();
		this.servicesWithFlow = new ArrayList<OSBService>();
		this.folders = new OraSB10gFolders(this, file, null, null);
	}

	public final void addService(OSBService service) {
		service.setProject(this);
		if (service instanceof Proxy || service instanceof SplitJoin) {
			this.servicesWithFlow.add(service);
		}
		this.services.add(service);
	}

	/**
	 * 
	 * @return all services in project
	 */
	public final List<OSBService> getServices() {
		return services;
	}

	/**
	 * 
	 * 
	 * @return list of {@link Proxy} or {@link SplitJoin}
	 */
	public final List<OSBService> getServicesWithFlow() {
		return servicesWithFlow;
	}

	/**
	 * @return the oraSB10gFolders
	 */
	public final OraSB10gFolders getFolders() {
		return folders;
	}

	public final void setWorkspace(OraSB10gWorkspace workspace) {
		this.workspace = workspace;
	}

	public final String toString() {
		if(file!=null){
			int index = file.getName().lastIndexOf("/");
			if(index>0) return file.getName().substring(index+1);			
		}
		return file.getName();
	}

	//@PropertyViewData(title = "Name")
	public final String getName() {
		if(file!=null){
			int index = file.getName().lastIndexOf("/");
			if(index>0) return file.getName().substring(index+1);			
		}
		return file.getName();
	}

	@Override
	//@PropertyViewData(title = "Path")
	public final File getFile() {
		return file;
	}

	@Override
	public final ProjectType getProjectType() {
		return ProjectType.ORACLE_SERVICE_BUS_1OG;
	}

	@Override
	public final Workspace getWorkpsace() {
		return workspace;
	}

	public final Image getImage(boolean small) {
		return ImageFactory.OSB_10G_PROJECT;
	}

	public final boolean isFolder() {
		return folder;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: 		").append(getType());
		sb.append("\nName:		").append(getName());
		sb.append("\nPath: 		").append(file != null ? file : "");
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Oracle Service Bus 10g/11g Project";
	}

	public final void calculateEndpointList(EndpointList endpointLists) {
		for (OSBService osbService : services) {
			if (osbService.getDependencyType() == ServiceDependencyType.PROXY_SERVICE || osbService.getDependencyType() == ServiceDependencyType.BUSINESS_SERVICE) {
				osbService.calculateEndpointList(endpointLists);
			}
		}
	}

	/***
	 * find all headers in all proxy services
	 * 
	 * @param osbHeaderList
	 */
	public final void findAllHeadersFromAllServices(OsbHeaderList osbHeaderList) {
		for (OSBService osbService : getServices()) {
			if (osbService.getDependencyType() == ServiceDependencyType.PROXY_SERVICE) {
				Proxy proxy = (Proxy) osbService;
				HeaderInActivity osbHeader = osbHeaderList.addProxy(proxy);
				proxy.getStructure().findHeaders(osbHeader);
			}
		}
	}

}
