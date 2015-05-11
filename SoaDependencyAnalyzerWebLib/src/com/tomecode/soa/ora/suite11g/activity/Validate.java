package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * validate
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Validate extends Activity {

	// TODO: VARRIABLE zobrazovat v properties
	private final String[] variables;

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param vars
	 */
	public Validate(String name, String vars, String skipCondition) {
		super(name);
		if (vars != null) {
			variables = vars.split(" ");
		} else {
			variables = new String[] {};
		}
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder("Type: ");
		if (variables.length != 0) {
			for (String var : variables) {
				sb.append("\nVariable: 		").append(var);
			}
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 	").append(skipCondition);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Validate";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_11G_VALIDATE;
	}

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {

			for (String var : variables) {
				if (var.equals(bpelVariable.getVariable())) {
					bpelVariable.addUsage(this);
				}
			}
			if (variableUsage.containsVariable(skipCondition, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
