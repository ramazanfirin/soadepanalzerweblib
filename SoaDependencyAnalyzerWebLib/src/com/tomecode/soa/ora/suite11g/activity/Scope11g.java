package com.tomecode.soa.ora.suite11g.activity;

import com.tomecode.soa.ora.suite10g.activity.Scope;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * scope in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Scope11g extends Scope {

	//@PropertyViewData(title = "Variable Access Serializable")
	private String variableAccessSerializable;

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variableAccessSerializable
	 * @param documentation
	 * @param skipCondition
	 */
	public Scope11g(String name, String variableAccessSerializable, String documentation, String skipCondition) {
		super(name, documentation);
		this.variableAccessSerializable = variableAccessSerializable;
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (variableAccessSerializable != null) {
			sb.append("\nVariable Access Serializable: 	").append(variableAccessSerializable);
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 				").append(skipCondition);
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
