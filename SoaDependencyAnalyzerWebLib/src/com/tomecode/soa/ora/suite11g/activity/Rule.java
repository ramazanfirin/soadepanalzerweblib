package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * rule
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Rule extends Activity {

	//@PropertyViewData(title = "Variable Access Serializable")
	private String variableAccessSerializable;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

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
	public Rule(String name, String variableAccessSerializable, String documentation, String skipCondition) {
		super(name);
		this.variableAccessSerializable = variableAccessSerializable;
		this.documentation = documentation;
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (variableAccessSerializable != null) {
			sb.append("\nVariable Access Serializable: 	").append(variableAccessSerializable);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: 				").append(documentation);
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 				").append(skipCondition);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Rule";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_11G_BUSINESS_RULE;
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
