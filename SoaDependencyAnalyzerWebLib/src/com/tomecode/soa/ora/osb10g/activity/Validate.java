package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element : validate
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Validate extends OsbActivity {

	//@PropertyViewData(title = "Variable")
	private String variable;

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Xpath")
	private String xpathText;

	//@PropertyViewData(title = "Save Variable")
	private String resultVarName;

	/**
	 * Constructor
	 * 
	 * @param variable
	 * @param comment
	 * @param xpathText
	 * @param resultVarName
	 */
	public Validate(String variable, String comment, String xpathText, String resultVarName) {
		this.variable = variable;
		this.comment = comment;
		this.xpathText = xpathText;
		this.resultVarName = resultVarName;
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
		if (resultVarName != null) {
			sb.append("\nSave Variable: ").append(resultVarName);
		}
		return sb.toString();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_VALIDATE;
	}

	@Override
	public final String getType() {
		return "Validate";
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
			} else if (xpathText != null && xpathText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}

}
