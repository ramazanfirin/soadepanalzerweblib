package com.tomecode.soa.ora.suite11g.activity;

import com.tomecode.soa.ora.suite10g.activity.OnMessage;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * on message - partnerlink
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class OnMessage11g extends OnMessage {

	//@PropertyViewData(title = "Conversation Id")
	private String conversionId;

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param conversionId
	 * @param variable
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 * @param headerVariable
	 * @param documentation
	 */
	public OnMessage11g(String conversionId, String variable, String partnerLink, String portType, String operation, String headerVariable, String documentation, String skipCondition) {
		super(null, partnerLink, portType, operation, headerVariable, documentation);
		this.conversionId = conversionId;
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
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
