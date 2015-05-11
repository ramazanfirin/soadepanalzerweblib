package com.tomecode.soa.ora.osb10g.services.protocols.jms;

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
 * JMS Queue or topic
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
@PropertyGroupView(title = "JMS Queue/Topic", parentMethod = "getParent")
public final class JMSQueue implements ImageFace, Node<JMSQueue>, ToolTip {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	/**
	 * parent {@link JMSConnectionFactory}
	 */
	private JMSConnectionFactory parentJmsConnectionFactory;

	/**
	 * queue name
	 */
	//@PropertyViewData(title = "Name:")
	private String name;

	private boolean isQueue;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            JMS queue name
	 * @param jmsConnectionFactory
	 *            parent {@link JMSConnectionFactory}
	 */
	public JMSQueue(String name, JMSConnectionFactory jmsConnectionFactory, boolean isQueue) {
		this.name = name;
		this.parentJmsConnectionFactory = jmsConnectionFactory;
		this.isQueue = isQueue;
	}

	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (isQueue) {
			if (small) {
				return ImageFactory.JMS_QUEUE_SMALL;
			}
			return ImageFactory.JMS_QUEUE;
		}
		if (small) {
			return ImageFactory.JMS_TOPIC_SMALL;
		}
		return ImageFactory.JMS_TOPIC;
	}

	@Override
	public final String getToolTip() {
		return "Type: " + getType() + "\nName: " + name;
	}

	@Override
	public final Object getParent() {
		return this.parentJmsConnectionFactory;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final JMSQueue getObj() {
		return this;
	}

	/**
	 * @return the isQueue
	 */
	public final boolean isQueue() {
		return isQueue;
	}

	@Override
	public String getType() {
		if (isQueue) {
			return "JMS Queue";
		}
		return "JMS Topic";
	}
}
