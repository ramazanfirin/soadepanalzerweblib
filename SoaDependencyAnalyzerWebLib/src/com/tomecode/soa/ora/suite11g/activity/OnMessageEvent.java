package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * on message - event
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class OnMessageEvent extends Activity {

	//@PropertyViewData(title = "Conversation Id")
	private String conversionId;

	//@PropertyViewData(title = "Variable")
	private String variable;
	//@PropertyViewData(title = "Event Name")
	private String eventName;

	/**
	 * Constructor
	 * 
	 * @param conversionId
	 * @param variable
	 * @param eventName
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 */
	public OnMessageEvent(String conversionId, String variable, String eventName) {
		super(null);
		this.conversionId = conversionId;
		this.variable = variable;
		this.eventName = eventName;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (conversionId != null) {
			sb.append("\nConversion Id: ").append(conversionId);
		}
		if (variable != null) {
			sb.append("\nVariable: 		").append(variable);
		}
		if (eventName != null) {
			sb.append("\nEvent Name: 	").append(eventName);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "OnMessage(Event)";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_ONMESSAGE;
	}

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(conversionId, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (variable != null && variable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}
}
