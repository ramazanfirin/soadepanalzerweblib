package com.tomecode.soa.ora.suite11g.project.sca.component.mediator;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.Mediator.MediatorActions;
import com.tomecode.soa.wsdl.Wsdl;
import com.tomecode.soa.wsdl.WsdlOperation;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * {@link Wsdl} {@link WsdlOperation} in {@link Mediator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Mediator Event Handler")
public final class MediatorEventHandler extends MediatorActions {

	private String event;

	private String deliveryPolicy;

	private String priority;

	public MediatorEventHandler() {
	}

	/**
	 * @return the event
	 */
	public final String getEvent() {
		return event;
	}

	/**
	 * @return the deliveryPolicy
	 */
	public final String getDeliveryPolicy() {
		return deliveryPolicy;
	}

	/**
	 * @return the priority
	 */
	public final String getPriority() {
		return priority;
	}

	public final String toString() {
		return event;
	}

	@Override
	public final Image getImage(boolean small) {
		return null;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder("Type:		").append(getType());
		if (event != null && event.trim().length() != 0) {
			sb.append("\nName:		").append(event);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Mediator Event Handler";
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public final void setEvent(String event) {
		int index = event.indexOf(":");
		if (index != -1) {
			this.event = event.substring(index + 1, event.length());
		} else {
			this.event = event;
		}
	}

	/**
	 * @param deliveryPolicy
	 *            the deliveryPolicy to set
	 */
	public final void setDeliveryPolicy(String deliveryPolicy) {
		this.deliveryPolicy = deliveryPolicy;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public final void setPriority(String priority) {
		this.priority = priority;
	}

}
