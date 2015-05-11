package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * replay in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Replay extends Activity {

	//@PropertyViewData(title = "Scope")
	private String scope;

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param scope
	 * @param skipCondition
	 */
	public Replay(String name, String scope, String skipCondition) {
		super(name);
		this.scope = scope;
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (scope != null) {
			sb.append("\nScope: 			").append(scope);
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 	").append(skipCondition);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Replay";
	}

	@Override
	public final Image getImage() {
		// TODO: chyba ikonka pre aktivitu Replay 
		return null;
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
