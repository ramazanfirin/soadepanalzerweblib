package com.tomecode.soa.ora.osb10g.services.protocols.jdp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJPD;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * JDP Provider for {@link EndpointJPD}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "JDP Provider")
public final class JdpProvider implements ImageFace, Node<JdpProvider>, ToolTip {

	private final List<JdpUri> jdpUris;

	//@PropertyViewData(title = "Name:")
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
	public JdpProvider(String name) {
		this();
		this.name = name;
	}

	private JdpProvider() {
		jdpUris = new ArrayList<JdpUri>();
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<?> getChilds() {
		return jdpUris;
	}

	@Override
	public final JdpProvider getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JDP_PROVIDER_SMALL;
		}
		return ImageFactory.JDP_PROVIDER;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getToolTip() {
		return "Type: " + getType() + "\nName: " + toString();
	}

	public final String getName() {
		return name;
	}

	public final void addJDPUri(JdpUri jdpUri) {
		if (!existsJDPUri(jdpUri)) {
			jdpUri.setProvider(this);
			jdpUris.add(jdpUri);
		}
	}

	private final boolean existsJDPUri(JdpUri uri) {
		for (JdpUri jdpUri : jdpUris) {
			if (jdpUri.getName().equals(uri.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final String getType() {
		return "JDP Provider";
	}
}
