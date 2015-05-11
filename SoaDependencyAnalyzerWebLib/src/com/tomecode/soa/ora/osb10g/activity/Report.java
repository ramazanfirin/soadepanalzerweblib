package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: report
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Report extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Key")
	private String key;

	//@PropertyViewData(title = "Variable")
	private String variable;
	//@PropertyViewData(title = "Xpath")
	private String xpathText;

	/**
	 * Constructor
	 * 
	 * @param comment
	 * @param key
	 * @param varName
	 * @param xpathText
	 */
	public Report(String comment, String key, String varName, String xpathText) {
		this.comment = comment;
		this.key = key;
		this.variable = varName;
		this.xpathText = xpathText;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_REPORT;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (key != null) {
			sb.append("\nKey: 		").append(key);
		}
		if (variable != null) {
			sb.append("\nVariable: 	").append(variable);
		}
		if (xpathText != null) {
			sb.append("\nXpath: 	").append(xpathText);
		}
		if (comment != null) {
			sb.append("\nComment: 	").append(comment);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Report";
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
			if (key != null && key.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (variable != null && variable.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (xpathText != null && xpathText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}

}
