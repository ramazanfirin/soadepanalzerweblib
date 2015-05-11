package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: branch-node type: condition
 * 
 * @author Tomas Frastia
 * 
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class BranchNodeCondition extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Variable")
	private String variable;

	//@PropertyViewData(title = "Xpath")
	private String xpathText;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param comment
	 * @param errorHandler
	 */
	public BranchNodeCondition(String name, String comment, String errorHandler) {
		super(name, errorHandler);
		this.comment = comment;
	}

	public final void setVariable(String variable) {
		this.variable = variable;
	}

	public final void setXpathText(String xpathText) {
		this.xpathText = xpathText;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BRANCH_NODE_CONDITION;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (variable != null) {
			sb.append("\nVariable: 		").append(variable);
		}
		if (comment != null) {
			sb.append("\nComment: 		").append(comment);
		}
		if (xpathText != null) {
			sb.append("\nXpath: 		").append(xpathText);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Branch Condition";
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
			if (xpathText != null && xpathText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (variable != null && variable.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
