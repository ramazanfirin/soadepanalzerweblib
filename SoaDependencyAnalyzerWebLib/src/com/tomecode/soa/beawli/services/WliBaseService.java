package com.tomecode.soa.beawli.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.AddDataToExistsGroupView;
//import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.dependency.analyzer.tools.ReferencedBy;
import com.tomecode.soa.dependency.analyzer.tools.ToolEndpointList;
import com.tomecode.soa.ora.osb10g.services.Binding;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolder;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointUNKNOWN;
import com.tomecode.soa.project.Project;

public class WliBaseService extends ReferencedBy implements ImageFace, ToolEndpointList {

	//@PropertyViewData(title = "Name")
	protected String name;

	//@PropertyViewData(title = "Description", showWhenIsEmpty = false)
	protected String description;
	
	
	private OSBService refOsbService;
	
	public WliBaseService() {
	}
	public WliBaseService(File file) {
	}

	public final String getName() {
		return this.name;
	}


	public Image getImage(boolean small) {
		return ImageFactory.OPEN_ESB_BPEL_INVOKE;
	}

	


	public String toString() {
		return this.name;
	}

//	public final ServiceDependencies getServiceDependencies() {
//		return this.serviceDependencies;
//	}
//
//	public final OsbActivityDependency getActivityDependency() {
//		return this.activityDependency;
//	}



	public void calculateEndpointList(EndpointList endpointLists) {
	}

	public String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type:\t\t\t").append(getType());
		sb.append("\nName:\t\t\t").append(this.name);
		if ((this.description != null) && (this.description.length() != 0)) {
			sb.append("\nDescription:\t\t").append(this.description);
		}
		return sb.toString();
	}
	
	

	public void setName(String name) {
		this.name = name;
	}
	public final String getType() {
		return "WLI base service";
	}
	
	public OSBService getOsbService() {
		return refOsbService;
	}

	public void setOsbService(OSBService osbService) {
		this.refOsbService = osbService;
	}


}