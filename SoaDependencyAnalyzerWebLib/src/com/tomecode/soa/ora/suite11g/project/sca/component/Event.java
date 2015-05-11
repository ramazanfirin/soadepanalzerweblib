package com.tomecode.soa.ora.suite11g.project.sca.component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite11g.project.sca.ScaComponent;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.MediatorEventNode;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Business Event
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Event")
public final class Event extends ScaComponent {

	/**
	 * same as name but without prefix
	 */
	//@PropertyViewData(title = "Name: ")
	private String singleName;
	/**
	 * event type
	 */
	//@PropertyViewData(title = "Event Type: ")
	public final EventType eventType;

	//@PropertyViewData(title = "Consistency")
	public final String consistency;

	private final List<MediatorEventNode> mediatorEventNodes;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            event name;
	 * @param file
	 *            event file .edl
	 */
	public Event(String name, File file, String consistency, EventType eventType) {
		super(name, file, CompositeServiceType.EVENT);
		this.mediatorEventNodes = new ArrayList<MediatorEventNode>();
		this.consistency = consistency;

		int index = name.indexOf(":");
		if (index != -1) {
			singleName = name.substring(index + 1, name.length());
		} else {
			singleName = name;
		}
		this.eventType = eventType;
	}

	/**
	 * @return the consistency
	 */
	public final String getConsistency() {
		return consistency;
	}

	/**
	 * @return the eventType
	 */
	public final EventType getEventType() {
		return eventType;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_11G_EVENT;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder("Type:		");
		sb.append(getType());
		if (name != null && name.trim().length() != 0) {
			sb.append("\nName:		").append(singleName);
		}
		sb.append("\nType:		").append(eventType);
		if (file != null) {
			sb.append("\nFile:			").append(file);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Business Event";
	}

	public final String toString() {
		return singleName;
	}

	public final String getName() {
		return singleName;
	}

	public final void addDependency(MediatorEventNode mediatorEventNode) {
		mediatorEventNode.setEvent(this);
		this.mediatorEventNodes.add(mediatorEventNode);
	}

	/**
	 * @return the mediatorEventNodes
	 */
	public final List<MediatorEventNode> getMediatorEventNodes() {
		return mediatorEventNodes;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
	 * 
	 * Business Event Type
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public enum EventType {
		PUBLISHES, SUBSCRIBE, SUBSCRIBEALL;

	}

}
