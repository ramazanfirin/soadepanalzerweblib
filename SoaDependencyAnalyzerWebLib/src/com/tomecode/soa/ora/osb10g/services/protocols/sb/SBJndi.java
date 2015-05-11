package com.tomecode.soa.ora.osb10g.services.protocols.sb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.osb10g.services.config.EndpointSB;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * SB Provider for {@link EndpointSB}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "SB JNDI Provider Name")
public final class SBJndi implements ImageFace, Node<SBJndi>, ToolTip {

	private final List<ServiceName> serviceNames;

	//@PropertyViewData(title = "Name: ")
	private String name;
	/**
	 * parent service
	 */
	private Object parentService;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public SBJndi(String name) {
		this();
		this.name = name;
	}

	private SBJndi() {
		serviceNames = new ArrayList<ServiceName>();
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<ServiceName> getChilds() {
		return serviceNames;
	}

	@Override
	public final SBJndi getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.SB_JNDI_PROVIDER_SMALL;
		}
		return ImageFactory.SB_JNDI_PROVIDER;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getToolTip() {
		return "Type: " + getType() + "\nName: " + name;
	}

	public final String getName() {
		return name;
	}

	public final void addServiceName(ServiceName serviceName) {
		if (!existsServiceName(serviceName)) {
			serviceName.setSbJndi(this);
			serviceNames.add(serviceName);
		}
	}

	private final boolean existsServiceName(ServiceName uri) {
		for (ServiceName serviceName : serviceNames) {
			if (serviceName.getName().equals(uri.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final String getType() {
		return "SB JNDI Provider Name";
	}
}
