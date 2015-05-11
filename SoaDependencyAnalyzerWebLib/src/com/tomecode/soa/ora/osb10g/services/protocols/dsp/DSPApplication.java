package com.tomecode.soa.ora.osb10g.services.protocols.dsp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010,2011. All rights reserved.
 * 
 * DSP Application for {@link DSPServer}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "DSP Application", parentMethod = "getParent")
public final class DSPApplication implements ImageFace, Node<DSPApplication>, ToolTip {

	//@PropertyViewData(title = "Name:")
	private String name;

	private DSPServer dspServer;

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	public DSPApplication(String name) {
		this.name = name;
	}

	@Override
	public final Object getParent() {
		return dspServer;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final DSPApplication getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.DSP_APPLICATION_SMALL;
		}
		return ImageFactory.DSP_APPLICATION;
	}

	@Override
	public String getToolTip() {
		return "Type: " + getType() + "\nName: " + name;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	/**
	 * @param dspServer
	 *            the dspServer to set
	 */
	public final void setDspServer(DSPServer dspServer) {
		this.dspServer = dspServer;
	}

	@Override
	public final String getType() {
		return "DSP Application";
	}

}
