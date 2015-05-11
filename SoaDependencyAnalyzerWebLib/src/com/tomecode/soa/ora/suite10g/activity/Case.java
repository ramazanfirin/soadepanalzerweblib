package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * case from switch in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Case extends Activity {

	private static final long serialVersionUID = 804854324731704322L;

	/**
	 * variable in case
	 */
	//@PropertyViewData(title = "Condition")
	private String variable;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 * @param documentation
	 */
	public Case(String name, String variable, String documentation) {
		super(name);
		this.variable = variable;
		this.documentation = documentation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_SWITCH;
	}

	/**
	 * 
	 * variable in case activity
	 * 
	 * @return the variable
	 */
	public final String getVariable() {
		return variable;
	}

	@Override
	public final String getType() {
		return "Case";
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (variable != null) {
			sb.append("\nCondition: 	").append(variable);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: ").append(documentation);
		}
		return sb.toString();
	}

	// /**
	// * find variable in activity
	// */
	// public final void findVariable(FindUsageVariableResult
	// findUsageVariableResult) {
	// if (variable != null &&
	// findUsageVariableResult.getVariable().toString().equals(variable)) {
	// findUsageVariableResult.addUsage(this);
	// }
	// }

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(variable, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
