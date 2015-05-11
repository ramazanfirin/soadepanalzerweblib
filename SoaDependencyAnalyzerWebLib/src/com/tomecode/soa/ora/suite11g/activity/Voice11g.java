package com.tomecode.soa.ora.suite11g.activity;

import com.tomecode.soa.ora.suite10g.activity.Voice;

/***
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * voice activity in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Voice11g extends Voice {

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param documentation
	 * @param skipCondition
	 */
	public Voice11g(String name, String documentation, String skipCondition) {
		super(name, documentation);
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		if (skipCondition != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nSkip Condition: ").append(skipCondition);
			return sb.toString();
		}
		return super.getToolTip();
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
