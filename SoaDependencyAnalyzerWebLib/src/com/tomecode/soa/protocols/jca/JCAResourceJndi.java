package com.tomecode.soa.protocols.jca;

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
 * JCA JNDI resource location
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
@PropertyGroupView(title = "JCA JNDI Resource Location", parentMethod = "getParent")
public final class JCAResourceJndi implements ImageFace, Node<JCAResourceJndi>, ToolTip {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	private Object basicJca;

	//@PropertyViewData(title = "Name: ")
	private final String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public JCAResourceJndi(String name, Object basicJca) {
		this.name = name;
		this.basicJca = basicJca;
	}

	@Override
	public final Object getParent() {
		return this.basicJca;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final JCAResourceJndi getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JCA_JNDI_RESOURCE_SMALL;
		}
		return ImageFactory.JCA_JNDI_RESOURCE;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	@Override
	public final String getToolTip() {
		return "Type: " + getType() + "\nName: " + name;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getType() {
		return "JCA JNDI Resource Location";
	}

}
