package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: assign
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Assign extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Variable")
	private String variable;

	//@PropertyViewData(title = "Expression")
	private String xqueryText;

	/**
	 * Constructor
	 * 
	 * @param variable
	 * @param comment
	 * @param xqueryText
	 */
	public Assign(String variable, String comment, String xqueryText) {
		this.variable = variable;
		this.comment = comment;
		this.xqueryText = xqueryText;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ASSIGN;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (variable != null) {
			sb.append("\nVariable:		").append(variable);
		}
		if (comment != null) {
			sb.append("\nComment:		").append(comment);
		}
		if (xqueryText != null) {
			sb.append("\nXquery:		").append(xqueryText);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Assign";
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
			if (xqueryText != null && xqueryText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (variable != null && variable.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
