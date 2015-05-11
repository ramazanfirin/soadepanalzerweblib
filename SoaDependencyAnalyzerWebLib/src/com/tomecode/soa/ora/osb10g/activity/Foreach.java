package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: foreach
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Foreach extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Variable")
	private String variable;

	//@PropertyViewData(title = "Xpath")
	private String xpathText;

	//@PropertyViewData(title = "For Earch Variable")
	private String valueVariable;

	//@PropertyViewData(title = "Index Variable")
	private String indexVariable;

	//@PropertyViewData(title = "Count Variable")
	private String totalVariable;

	/**
	 * Constructor
	 * 
	 * @param comment
	 * @param variable
	 * @param xpathText
	 * @param valueVariable
	 * @param indexVariable
	 * @param totalVariable
	 */
	public Foreach(String comment, String variable, String xpathText, String valueVariable, String indexVariable, String totalVariable) {
		this.comment = comment;
		this.variable = variable;
		this.xpathText = xpathText;
		this.valueVariable = valueVariable;
		this.indexVariable = indexVariable;
		this.totalVariable = totalVariable;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (comment != null) {
			sb.append("\nComment: 				").append(comment);
		}
		if (valueVariable != null) {
			sb.append("\nFor Earch Variable: 	").append(valueVariable);
		}
		if (xpathText != null) {
			sb.append("\nXpath: 				").append(xpathText);
		}
		if (variable != null) {
			sb.append("\nIn Variable: 			").append(variable);
		}
		if (indexVariable != null) {
			sb.append("\nIndex Variable: 		").append(indexVariable);
		}
		if (totalVariable != null) {
			sb.append("\nCount Variable: 		").append(totalVariable);
		}
		return sb.toString();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_FOREACH;
	}

	@Override
	public final String getType() {
		return "For Each";
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
			if (valueVariable != null && valueVariable.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (xpathText != null && xpathText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (indexVariable != null && indexVariable.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (totalVariable != null && totalVariable.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (variable != null && variable.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}

}
