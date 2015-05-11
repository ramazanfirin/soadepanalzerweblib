package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: routingTable
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class RoutingTable extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Xquery")
	private String xqueryText;

	/**
	 * Constructor
	 * 
	 * @param comment
	 * @param xqueryText
	 */
	public RoutingTable(String comment, String xqueryText) {
		this.comment = comment;
		this.xqueryText = xqueryText;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (comment != null) {
			sb.append("\nComment: 		").append(comment);
		}
		if (xqueryText != null) {
			sb.append("\nXquery Text: 	").append(xqueryText);
		}
		return sb.toString();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ROUTING_TABLE;
	}

	@Override
	public final String getType() {
		return "Publish Table";
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
