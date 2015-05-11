package com.tomecode.soa.beawli.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.beawli.services.WliBaseFolders;
import com.tomecode.soa.beawli.services.WliBaseProcess;
import com.tomecode.soa.beawli.services.WliBaseService;
import com.tomecode.soa.beawli.workspace.WliBaseWorkspace;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
//import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.dependency.analyzer.tools.ToolOsbList;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolders;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.SplitJoin;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.ora.suite11g.project.Ora11gComposite;
import com.tomecode.soa.ora.suite11g.project.sca.CompositeService;
import com.tomecode.soa.ora.suite11g.workspace.Ora11gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;

@PropertyGroupView(title = "Project...", type = "Wli Base Project", parentMethod = "getWorkpsace")
public final class WliBaseProject  implements Project {
	private boolean folder;
	private File file;
	private WliBaseWorkspace workspace;
	private final List<WliBaseProcess> processList;

	public WliBaseProject(File file, boolean isFolder) {		
		this.file = file;
		this.folder = isFolder;
		this.processList = new ArrayList<WliBaseProcess>();
	}

	public final void addProcess(WliBaseProcess process) {
		this.processList.add(process);
	}



	public WliBaseWorkspace getWorkspace() {
		return workspace;
	}

	public List<WliBaseProcess> getProcessList() {
		return processList;
	}

	public final void setWorkspace(WliBaseWorkspace workspace) {
		this.workspace = workspace;
	}

	public final String toString() {
		if(file!=null){
			int index = file.getName().lastIndexOf("/");
			if(index>0) return file.getName().substring(index+1);			
		}
		return this.file.getName();
	}

	//@PropertyViewData(title = "Name")
	public final String getName() {
		if(file!=null){
			int index = file.getName().lastIndexOf("/");
			if(index>0) return file.getName().substring(index+1);			
		}
		return this.file.getName();
	}

	//@PropertyViewData(title = "Path")
	public final File getFile() {
		return this.file;
	}

	public final ProjectType getProjectType() {
		return ProjectType.WLI_BASE_PROJECT;
	}

	public final Workspace getWorkpsace() {
		return this.workspace;
	}

	public final Image getImage(boolean small) {
		return ImageFactory.EJB_HOME;
	}

	public final boolean isFolder() {
		return this.folder;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: \t\t").append(getType());
		sb.append("\nName:\t\t").append(getName());
		sb.append("\nPath: \t\t").append(this.file != null ? this.file : "");
		return sb.toString();
	}

	public final String getType() {
		return "WLI Base Project";
	}

	public final void calculateEndpointList(EndpointList endpointLists) {
//		for (WliBaseService osbService : this.services)
//			if ((osbService.getDependencyType() == ServiceDependency.ServiceDependencyType.PROXY_SERVICE) || (osbService.getDependencyType() == ServiceDependency.ServiceDependencyType.BUSINESS_SERVICE))
//				osbService.calculateEndpointList(endpointLists);
	}

	public final void findAllHeadersFromAllServices(ToolOsbList.OsbHeaderList osbHeaderList) {
//		for (OSBService osbService : getServices())
//			if (osbService.getDependencyType() == ServiceDependency.ServiceDependencyType.PROXY_SERVICE) {
//				Proxy proxy = (Proxy) osbService;
//				ToolOsbList.HeaderInActivity osbHeader = osbHeaderList.addProxy(proxy);
//				proxy.getStructure().findHeaders(osbHeader);
//			}
	}
	
}
