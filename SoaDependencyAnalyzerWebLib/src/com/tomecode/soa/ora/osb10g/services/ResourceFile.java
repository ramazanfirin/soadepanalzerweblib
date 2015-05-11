package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Resource file
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Resource File...", typeMethod = "getType", parentMethod = "getWorkpsace")
public final class ResourceFile extends OSBService {

	/**
	 * Constructor
	 * 
	 * @param file
	 * @param type
	 */
	public ResourceFile(File file, ServiceDependencyType type) {
		super(file, type);
		name = file.getName();
	}

	@Override
	public final Image getImage() {
		if (type == ServiceDependencyType.ARCHIVE) {
			return ImageFactory.OSB_10G_ARCHIVE;
		} else if (type == ServiceDependencyType.WSDL) {
			return ImageFactory.OSB_10G_WSDL_TRANSFORMATION;
		} else if (type == ServiceDependencyType.XML_SCHEMA || type == ServiceDependencyType.XML) {
			return ImageFactory.OSB_10G_XML_SCHEMA;
		} else if (type == ServiceDependencyType.XQUERY) {
			return ImageFactory.OSB_10G_XQ_TRANSFORMATION;
		} else if (type == ServiceDependencyType.XQUERY) {
			return ImageFactory.OSB_10G_XQ_TRANSFORMATION;
		} else if (type == ServiceDependencyType.SERVICE_ACCOUNT) {
			return ImageFactory.OSB_10G_SERVICE_ACCOUNT;
		} else if (type == ServiceDependencyType.JAR) {
			return ImageFactory.JAR;
		} else if (type == ServiceDependencyType.JCA) {
			return ImageFactory.JCA_TRANSPORT_SMALL;
		}
		return null;
	}

}
