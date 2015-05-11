package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: mflTransform
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class MflTransform extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;
	//@PropertyViewData(title = "Type")
	private String type;
	//@PropertyViewData(title = "Xquery")
	private String xqueryText;

	/**
	 * Constructor
	 * 
	 * @param comment
	 * @param type
	 * @param xqueryText
	 */
	public MflTransform(String comment, String type, String xqueryText) {
		this.comment = comment;
		this.type = type;
		this.xqueryText = xqueryText;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (type != null) {
			sb.append("\nType: 		").append(type);
		}
		if (comment != null) {
			sb.append("\nComment: 	").append(comment);
		}
		if (xqueryText != null) {
			sb.append("\nXquery: 	").append(xqueryText);
		}
		return sb.toString();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_MFL;
	}

	@Override
	public final String getType() {
		return "Mfl Transform";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			if (xqueryText != null && xqueryText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
