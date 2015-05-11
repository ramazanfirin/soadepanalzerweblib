package com.tomecode.soa.ora.osb10g.services.protocols.jms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJms;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * JMS Server for {@link EndpointJms}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "JMS Server")
public final class JMSServer implements ImageFace, Node<JMSServer>, ToolTip {

	/**
	 * JMS server name (host)
	 */
	//@PropertyViewData(title = "Name:")
	private String name;
	/**
	 * JMS server port
	 */
	//@PropertyViewData(title = "Port:")
	private int port = -1;

	/**
	 * parent service
	 */
	private Object parentService;

	/**
	 * list of {@link JMSConnectionFactory}
	 */
	private final List<JMSConnectionFactory> connectionFactories;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            JMS name
	 * @param port
	 *            JMS port
	 */
	public JMSServer(String name, int port) {
		this();
		this.name = name;
		this.port = port;
	}

	/**
	 * Constructor
	 * 
	 * @param server
	 */
	public JMSServer(String server) {
		this();
		int index = server.indexOf(":");
		if (index == -1) {
			name = server;
		} else {
			name = server.substring(0, index);
			try {
				port = Integer.parseInt(server.substring(index + 1));
			} catch (Exception e) {
				port = -1;
			}

		}
	}

	private JMSServer() {
		this.connectionFactories = new ArrayList<JMSConnectionFactory>();
	}

	public final void addJMSConnectionFactory(JMSConnectionFactory connectionFactory) {
		JMSConnectionFactory exists = existJMSConnectionFactory(connectionFactory);
		if (exists == null) {
			connectionFactory.addJmsServer(this);
			connectionFactories.add(connectionFactory);
		} else {
			for (JMSQueue jmsQueue : connectionFactory.getChilds()) {
				exists.addJmsQueue(jmsQueue.getName(), jmsQueue.isQueue());
			}
		}
	}

	private final JMSConnectionFactory existJMSConnectionFactory(JMSConnectionFactory connectionFactory) {
		for (JMSConnectionFactory factory : connectionFactories) {
			if (factory.getName().equals(connectionFactory.getName())) {
				return factory;
			}
		}
		return null;
	}

	public final String getName() {
		return name;
	}

	public final int getPort() {
		return port;
	}

	public final String toString() {
		if (port != -1) {
			return name + ":" + port;
		}
		return name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JMS_SERVER_SMALL;
		}
		return ImageFactory.JMS_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "Type: " + getType() + "\nName: " + name + (port != -1 ? "\nPort: " + port : "");
	}

	public final void setParentService(OSBService parentService) {
		this.parentService = parentService;
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<JMSConnectionFactory> getChilds() {
		return connectionFactories;
	}

	@Override
	public final JMSServer getObj() {
		return this;
	}

	@Override
	public final String getType() {
		return "JMS Server";
	}

}
