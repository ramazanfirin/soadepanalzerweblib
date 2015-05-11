package com.tomecode.soa.ora.suite11g.activity;

import com.tomecode.soa.ora.suite10g.activity.Invoke;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Invoke BPEL activity
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Invoke11g extends Invoke {

	//@PropertyViewData(title = "Event name")
	private String eventName;
	//@PropertyViewData(title = "Conversation Id")
	private String conversionId;
	//@PropertyViewData(title = "Invoke As Detail")
	private String invokeAsDetail;
	//@PropertyViewData(title = "Detail Label")
	private String detailLabel;
	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param inputVariable
	 * @param outputVariable
	 * @param eventName
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 * @param conversionId
	 * @param invokeAsDetail
	 * @param detailLabel
	 */
	public Invoke11g(String name, String inputVariable, String outputVariable, String eventName, String partnerLink, String portType, String operation, String conversionId, String invokeAsDetail,
			String detailLabel, String headerVariable, String documentation, String skipCondition) {
		super(name, partnerLink, portType, operation, inputVariable, outputVariable, headerVariable, documentation);
		this.eventName = eventName;
		this.conversionId = conversionId;
		this.invokeAsDetail = invokeAsDetail;
		this.detailLabel = detailLabel;
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
		if (invokeAsDetail != null) {
			sb.append("\nInvoke As Detail: 	").append(invokeAsDetail);
		}
		if (detailLabel != null) {
			sb.append("\nDetail Label: 		").append(detailLabel);
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
