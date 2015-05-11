package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * remove entity
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class RemoveEntity extends Activity {

	//@PropertyViewData(title = "Variable")
	private String variable;

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 * @param skipCondition
	 */
	public RemoveEntity(String name, String variable, String skipCondition) {
		super(name);
		this.variable = variable;
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (variable != null) {
			sb.append("\nVariable: 			").append(variable);
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 	").append(skipCondition);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "RemoveEntity";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_11G_REMOVEENTITY;
	}

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variable != null && variable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (variableUsage.containsVariable(skipCondition, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
