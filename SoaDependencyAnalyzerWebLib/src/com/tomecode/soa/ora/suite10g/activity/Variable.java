package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Variable in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Variable extends Activity {

	private static final long serialVersionUID = 1387914145237466640L;

	//@PropertyViewData(title = "Message Type", showWhenIsEmpty = false)
	private final String messageType;
	//@PropertyViewData(title = "Element", showWhenIsEmpty = false)
	private final String element;
	//@PropertyViewData(title = "type", showWhenIsEmpty = false)
	private final String type;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            activity name
	 * @param messageType
	 */
	public Variable(String name, String messageType, String element, String type) {
		super(name);
		this.messageType = messageType;
		this.element = element;
		this.type = type;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_VARIABLE;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (messageType != null) {
			sb.append("\nMessage Type: 	").append(messageType);
		}
		if (element != null) {
			sb.append("\nElement: 		").append(element);
		}
		if (type != null) {
			sb.append("\nType: 			").append(type);
		}
		return sb.toString();
	}

	/**
	 * @return the messageType
	 */
	public final String getMessageType() {
		return messageType;
	}

	public final String getQname() {
		if (messageType != null) {
			return messageType;
		} else if (element != null) {
			return element;
		} else if (type != null) {
			return type;
		}
		return "";
	}

	public final String getVariableType() {
		if (messageType != null) {
			return "Message Type";
		} else if (element != null) {
			return "Element";
		} else if (type != null) {
			return "Simple Type";
		}
		return "";
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getType() {
		return "Variable";
	}

	@Override
	public final void createListOfBpelVariables(BpelVariableUsage bpelVariableUsage) {
		bpelVariableUsage.addVariable(this);
		super.createListOfBpelVariables(bpelVariableUsage);
	}
}
