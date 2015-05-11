package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: insert
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Insert extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;
	//@PropertyViewData(title = "Variable")
	private String variable;

	//@PropertyViewData(title = "Where")
	private String where;
	//@PropertyViewData(title = "Xpath")
	private String xpathText;

	//@PropertyViewData(title = "Xquery")
	private String xqueryText;

	/**
	 * Constructor
	 * 
	 * @param variable
	 * @param where
	 * @param xpathText
	 * @param expression
	 * @param comment
	 */
	public Insert(String variable, String where, String xpathText, String xqueryText, String comment) {
		this.variable = variable;
		this.where = where;
		this.xpathText = xpathText;
		this.xqueryText = xqueryText;
		this.comment = comment;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_INSERT;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (variable != null) {
			sb.append("\nVariable: 		").append(variable);
		}
		if (where != null) {
			sb.append("\nWhere: 		").append(where);
		}
		if (comment != null) {
			sb.append("\nComment: 		").append(comment);
		}
		if (xpathText != null) {
			sb.append("\nXpath: 		").append(xpathText);
		}
		if (xqueryText != null) {
			sb.append("\nXquery: 		").append(xqueryText);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Insert";
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
			} else if (where != null && where.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (xpathText != null && xpathText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
