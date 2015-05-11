package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: rename
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Rename extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Variable")
	private String variable;

	//@PropertyViewData(title = "Xpath")
	private String xpathText;
	//@PropertyViewData(title = "Namespace")
	private String namespace;
	//@PropertyViewData(title = "Localname")
	private String localname;

	/**
	 * Constructor
	 * 
	 * @param variable
	 * @param comment
	 * @param xpathText
	 * @param namespace
	 * @param localname
	 */
	public Rename(String variable, String comment, String xpathText, String namespace, String localname) {
		super();
		this.variable = variable;
		this.comment = comment;
		this.xpathText = xpathText;
		this.namespace = namespace;
		this.localname = localname;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_RENAME;
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
		if (namespace != null) {
			sb.append("\nNamespace: 	").append(namespace);
		}
		if (localname != null) {
			sb.append("\nLocalname: 	").append(localname);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Rename";
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
			} else if (localname != null && localname.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (xpathText != null && xpathText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
