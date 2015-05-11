package com.tomecode.soa.wsdl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.wsdl.WsdlOperationMessage.WsdlOperationMessageType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * operation in {@link Wsdl}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "WSDL Operation", parentMethod = "getPortType")
public final class WsdlOperation implements Serializable, ImageFace {

	private static final long serialVersionUID = 8680563798539886063L;
	/**
	 * WSDL operation name
	 */
	//@PropertyViewData(title = "Name: ")
	private final String name;

	/**
	 * list of dependency objects
	 */
	private final List<Object> dependencies;
	/**
	 * messages
	 */
	private final List<WsdlOperationMessage> messages;

	private WsdlPortType portType;

	//@PropertyViewData(title = "Is Request-Response: ")
	private boolean requestResponse;

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 */
	public WsdlOperation(String name) {
		this.messages = new ArrayList<WsdlOperationMessage>();
		this.dependencies = new ArrayList<Object>();
		this.name = name;
	}

	/**
	 * @return the dependencies
	 */
	public final List<Object> getDependencies() {
		return dependencies;
	}

	/**
	 * @return the messages
	 */
	public final List<WsdlOperationMessage> getMessages() {
		return messages;
	}

	public final void addMessage(WsdlOperationMessage message) {
		message.setWsdlOperation(this);
		this.messages.add(message);
	}

	public final WsdlOperationMessage findMessage(WsdlOperationMessageType type) {
		for (WsdlOperationMessage message : messages) {
			if (message.getOperationMessageType() == type) {
				return message;
			}
		}
		return null;
	}

	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	/**
	 * dependency objects
	 * 
	 * @param dependency
	 */
	public final void addDependnecy(Object dependency) {
		if (dependency != null) {
			if (!dependencies.contains(dependency)) {
				dependencies.add(dependency);
			}
		}
	}

	@Override
	public final Image getImage(boolean small) {
		if (requestResponse) {
			return ImageFactory.ORACLE_10G_OPERATION_REQUEST_RESPONSE;
		}
		return ImageFactory.ORACLE_10G_OPERATION_REQUEST;
	}

	public final void setRequestResponse(boolean state) {
		requestResponse = state;
	}

	@Override
	public final String getToolTip() {
		StringBuilder tooltip = new StringBuilder("Type:			").append(getType()).append("\nName:			").append(name).append("\n");

		WsdlOperationMessage message = findMessage(WsdlOperationMessageType.INPUT);
		tooltip.append((message != null ? "\nInput Message: 	" + message.getMessage() : ""));
		message = findMessage(WsdlOperationMessageType.OUTPUT);
		tooltip.append((message != null ? "\nOutput Message: 	" + message.getMessage() : ""));
		message = findMessage(WsdlOperationMessageType.FAULT);
		tooltip.append((message != null ? "\nFault Message:		" + message.getMessage() : ""));
		return tooltip.toString();
	}

	//@PropertyViewData(title = "Input Message: ")
	public final String getInputMessage() {
		WsdlOperationMessage message = findMessage(WsdlOperationMessageType.INPUT);
		if (message != null) {
			return message.getMessage();
		}
		return "";
	}

	//@PropertyViewData(title = "Output Message: ")
	public final String getOutputMessage() {
		WsdlOperationMessage message = findMessage(WsdlOperationMessageType.OUTPUT);
		if (message != null) {
			return message.getMessage();
		}
		return "";
	}

	//@PropertyViewData(title = "Fault Message: ")
	public final String getFaultMessage() {
		WsdlOperationMessage message = findMessage(WsdlOperationMessageType.FAULT);
		if (message != null) {
			return message.getMessage();
		}
		return "";
	}

	public final String getType() {
		return "WSDL Operation";
	}

	/**
	 * @return the portType
	 */
	public final WsdlPortType getPortType() {
		return portType;
	}

	/**
	 * @param portType
	 *            the portType to set
	 */
	public final void setPortType(WsdlPortType portType) {
		this.portType = portType;
	}

}
