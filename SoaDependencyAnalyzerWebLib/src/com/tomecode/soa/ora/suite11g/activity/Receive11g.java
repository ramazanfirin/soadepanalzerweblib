package com.tomecode.soa.ora.suite11g.activity;

import com.tomecode.soa.ora.suite10g.activity.Receive;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * 
 * Receive activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Receive11g extends Receive {

	//@PropertyViewData(title = "Event Name")
	private String eventName;
	//@PropertyViewData(title = "Conversation Id")
	private String conversionId;

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 * @param variable
	 * @param createInstance
	 * @param eventName
	 * @param conversionId
	 * @param headerVariable
	 * @param documentation
	 * @param skipCondition
	 */
	public Receive11g(String name, String partnerLink, String portType, String operation, String variable, String createInstance, String eventName, String conversionId, String headerVariable,
			String documentation, String skipCondition) {
		super(name, partnerLink, portType, operation, variable, createInstance, headerVariable, documentation);
		this.eventName = eventName;
		this.conversionId = conversionId;
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (eventName != null) {
			sb.append("\nEvent Name: 		").append(eventName);
		}
		if (conversionId != null) {
			sb.append("\nConversion Id: 	").append(conversionId);
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 	").append(skipCondition);
		}
		return sb.toString();
	}

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(conversionId, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (variableUsage.containsVariable(skipCondition, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}
}
