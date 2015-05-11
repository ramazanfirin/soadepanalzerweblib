package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: alert
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Alert extends OsbActivity {

	//@PropertyViewData(title = "Description")
	private String description;
	//@PropertyViewData(title = "Severity")
	private String severity;
	//@PropertyViewData(title = "Payload")
	private String xqueryText;

	/**
	 * Constructor
	 * 
	 * @param severity
	 */
	public Alert(String severity, String description, String xqueryText) {
		this.severity = severity;
		this.description = description;
		this.xqueryText = xqueryText;
	}

	public final String getSeverity() {
		return severity;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ALERT;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (severity != null) {
			sb.append("\nSeverity: 		").append(severity);
		}
		if (description != null) {
			sb.append("\nDescription: 	").append(description);
		}
		if (xqueryText != null) {
			sb.append("\nXquery: 		").append(xqueryText);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Alert";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			if (xqueryText != null && xqueryText.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (severity != null && severity.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
