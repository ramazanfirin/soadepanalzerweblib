package com.tomecode.soa.ora.osb10g.services.protocols.dsp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.config.EndpointDsp;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010,2011. All rights reserved.
 * 
 * 
 * DSP Server for {@link EndpointDsp}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "DSP Server")
public final class DSPServer implements ImageFace, Node<DSPServer>, ToolTip {

	/**
	 * DSP server name (host)
	 */
	//@PropertyViewData(title = "Name:")
	private String name;
	/**
	 * DSP server port
	 */
	//@PropertyViewData(title = "Port:")
	private int port = -1;

	private final List<DSPApplication> dspApplications;

	/**
	 * DSP Server
	 * 
	 * @param name
	 *            server name
	 * @param port
	 *            server port
	 */
	public DSPServer(String name, int port) {
		this();
		this.name = name;
		this.port = port;
	}

	/**
	 * dsp server
	 * 
	 * @param server
	 *            host:port
	 */
	public DSPServer(String server) {
		this();
		int index = server.indexOf(":");
		if (index == -1) {
			name = server;
		} else {
			name = server.substring(0, index);
			try {
				port = Integer.parseInt(server.substring(index + 1));
			} catch (Exception e) {

			}
		}
	}

	private DSPServer() {
		dspApplications = new ArrayList<DSPApplication>();
	}

	/**
	 * parent service
	 */
	private Object parentService;

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<?> getChilds() {
		return dspApplications;
	}

	@Override
	public final DSPServer getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.DSP_SERVER_SMALL;
		}
		return ImageFactory.DSP_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "Type: " + getType() + "\nName: " + name + ((port != -1 || port == 0) ? "\nPort: " + port : "");
	}

	public final void setParentService(OSBService parentService) {
		this.parentService = parentService;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the port
	 */
	public final int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public final void setPort(int port) {
		this.port = port;
	}

	public final String toString() {
		if (port != -1 || port != 0) {
			return name + ":" + port;
		}
		return name;
	}

	public final void addDSPApplication(DSPApplication dspApplication) {
		if (!existsDSPApplication(dspApplication)) {
			dspApplication.setDspServer(this);
			dspApplications.add(dspApplication);
		}
	}

	private final boolean existsDSPApplication(DSPApplication application) {
		for (DSPApplication dspApplication : dspApplications) {
			if (dspApplication.getName().equals(application.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final String getType() {
		return "DSP Server";
	}
}
