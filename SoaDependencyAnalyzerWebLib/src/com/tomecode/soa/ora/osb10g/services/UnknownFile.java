package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "Unknown File...", typeMethod = "getType", parentMethod = "getWorkpsace")
public final class UnknownFile extends OSBService {

	public UnknownFile(File file) {
		super(file, ServiceDependencyType.UNKNOWN);
		this.name = file.getName();
		this.orginalName = name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.UNKNOWN;
	}

	public final String getToolTip() {
		return "name - " + file.getName();
	}

}
