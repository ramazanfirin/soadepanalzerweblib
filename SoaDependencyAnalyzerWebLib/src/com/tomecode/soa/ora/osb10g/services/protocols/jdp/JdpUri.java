package com.tomecode.soa.ora.osb10g.services.protocols.jdp;

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
 * JDP Uri for {@link JdpProvider}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "JDP Uri", parentMethod = "getParent")
public final class JdpUri implements ImageFace, Node<JdpUri>, ToolTip {

	//@PropertyViewData(title = "Name:")
	private String name;

	private JdpProvider provider;

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	public JdpUri(String name) {
		this.name = name;
	}

	@Override
	public final Object getParent() {
		return provider;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final JdpUri getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JDP_URI_SMALL;
		}
		return ImageFactory.JDP_URI;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getToolTip() {
		return "Type: " + getType() + "\nUri: " + toString();
	}

	public final String getName() {
		return name;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public final void setProvider(JdpProvider provider) {
		this.provider = provider;
	}

	@Override
	public final String getType() {
		return "JPD Uri";
	}

}
