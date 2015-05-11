package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Assert activity in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Assert extends Activity {

	//@PropertyViewData(title = "Message")
	private String message;

	//@PropertyViewData(title = "Expression")
	private String expression;

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param message
	 * @param expression
	 */
	public Assert(String name, String message, String expression, String skipCondition) {
		super(name);
		this.message = message;
		this.expression = expression;
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (message != null) {
			sb.append("\nMessage: 			").append(message);
		}
		if (expression != null) {
			sb.append("\nExpression: 		").append(expression);
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 	").append(skipCondition);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Assert";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_11G_ASSERT;
	}

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(expression, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (variableUsage.containsVariable(skipCondition, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
