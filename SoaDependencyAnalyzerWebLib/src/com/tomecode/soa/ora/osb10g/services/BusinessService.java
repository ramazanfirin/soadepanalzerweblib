package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList.EndpointListProject;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Business service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "Business Service", type = "Oracle Service Bus 10g", parentMethod = "getProject")
public final class BusinessService extends OSBService {

	/**
	 * Constructor
	 * 
	 * @param file
	 */
	public BusinessService(File file) {
		super(file, ServiceDependencyType.BUSINESS_SERVICE);
		String tmpName = file.getName().toLowerCase();
		int index = tmpName.indexOf(".biz");
		if (index != -1) {
			name = file.getName().substring(0, index);
		} else {
			index = tmpName.indexOf(".businessservice");
			if (index != -1) {
				name = file.getName().substring(0, index);
			} else {
				name = file.getName();
			}
		}
		orginalName = name;
		name = name.replace("_", " ");
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BUSINESS_SERVICE;
	}

	@Override
	public final void calculateEndpointList(EndpointList endpointList) {
		if (endpointConfig != null && endpointConfig.getProtocol() != ProviderProtocol.LOCAL) {
			EndpointListProject endpointListProject = endpointList.findProject(this.project);
			endpointListProject.addEndpointListService(this, this.endpointConfig.getProtocol().toString(), this.endpointConfig.getUris());
		}
	}

}
