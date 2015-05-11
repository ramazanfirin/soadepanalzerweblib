package com.tomecode.soa.ora.osb10g.services.protocols.email;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Email
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Email")
public final class Email implements ImageFace, Node<Email>, ToolTip {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	//@PropertyViewData(title = "Address: ")
	private String name;
	/**
	 * parent service
	 */
	private Object parentService;

	public Email(String email) {
		this.name = email;
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final Email getObj() {
		return this;
	}

	public final void setParentService(OSBService parentService) {
		this.parentService = parentService;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.EMAIL_SMALL;
		}
		return ImageFactory.EMAIL;
	}

	public final String toString() {
		return name;
	}

	public final String getName() {
		return name;
	}

	@Override
	public final String getToolTip() {
		return "Type: " + getType() + "\nAddress: " + (name == null ? "" : name);
	}

	@Override
	public final String getType() {
		return "Email";
	}
}
