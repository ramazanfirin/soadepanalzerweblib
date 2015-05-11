package com.tomecode.soa.ora.suite11g.activity;

import com.tomecode.soa.ora.suite10g.activity.Reply;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * reply in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Reply11g extends Reply {

	//@PropertyViewData(title = "Event Name")
	private String eventName;

	//@PropertyViewData(title = "Fault Name")
	private String faultName;

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 * @param eventName
	 * @param faultName
	 * @param headerVariable
	 * @param documentation
	 * @param skipCondition
	 */
	public Reply11g(String name, String variable, String partnerLink, String portType, String operation, String eventName, String faultName, String headerVariable, String documentation,
			String skipCondition) {
		super(name, variable, partnerLink, portType, operation, headerVariable, documentation);
		this.eventName = eventName;
		this.faultName = faultName;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (eventName != null) {
			sb.append("\nEvent Name: 		").append(eventName);
		}
		if (faultName != null) {
			sb.append("\nFault Name: 		").append(faultName);
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 	").append(skipCondition);
		}
		return sb.toString();
	}

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(skipCondition, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
