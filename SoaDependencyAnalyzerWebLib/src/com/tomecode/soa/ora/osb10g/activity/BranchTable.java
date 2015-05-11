package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: branch-table
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class BranchTable extends OsbActivity {

	//@PropertyViewData(title = "Variable")
	private String variable;

	/**
	 * Constructor
	 * 
	 * @param variable
	 */
	public BranchTable(String variable) {
		super();
		this.variable = variable;
	}

	public final String getVariable() {
		return variable;
	}

	@Override
	public final Image getImage() {
		return null;
	}

	@Override
	public final String getType() {
		return "Branch-table";
	}

	public final String getToolTip() {
		if (variable != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nVariable: 		").append(variable);
			return sb.toString();
		}
		return super.getToolTip();
	}

	/**
	 * parse OSB variables from activity
	 * 
	 */
	@Override
	public final void createListOfOsbVariables(OsbVariableList osbVariableList) {
		if (variable != null) {
			osbVariableList.addVariable(variable, this);
		}
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			if (variable != null && variable.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
