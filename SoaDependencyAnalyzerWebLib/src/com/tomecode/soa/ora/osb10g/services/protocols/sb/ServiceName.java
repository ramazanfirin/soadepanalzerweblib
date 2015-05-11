package com.tomecode.soa.ora.osb10g.services.protocols.sb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Service name for {@link SBJndi}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Service name", parentMethod = "getParent")
public final class ServiceName implements ImageFace, Node<ServiceName>, ToolTip {

	//@PropertyViewData(title = "Name:")
	private String name;

	private SBJndi sbJndi;

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	public ServiceName(String name) {
		this.name = name;
	}

	@Override
	public final Object getParent() {
		return sbJndi;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final ServiceName getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.SB_NAME_SMALL;
		}
		return ImageFactory.SB_NAME;
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

	/**
	 * @param sbJndi
	 *            the sbJndi to set
	 */
	public final void setSbJndi(SBJndi sbJndi) {
		this.sbJndi = sbJndi;
	}

	@Override
	public final String getType() {
		return "Service name";
	}

}
