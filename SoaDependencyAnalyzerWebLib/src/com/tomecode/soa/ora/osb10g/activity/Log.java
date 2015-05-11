package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: log
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Log extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;
	//@PropertyViewData(title = "Log Level")
	private String logLevel;
	//@PropertyViewData(title = "Xquery")
	private String xqueryText;
	//@PropertyViewData(title = "Message")
	private String message;

	/**
	 * Constructor
	 * 
	 * @param logLevel
	 * @param comment
	 * @param xqueryText
	 * @param message
	 */
	public Log(String logLevel, String comment, String xqueryText, String message) {
		this.logLevel = logLevel;
		this.xqueryText = xqueryText;
		this.comment = comment;
		this.message = message;
	}

	public final String getLogLevel() {
		return logLevel;
	}

	public final String toString() {
		return "Log";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_LOG;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (logLevel != null) {
			sb.append("\nLevel: 		").append(logLevel);
		}
		if (comment != null) {
			sb.append("\nComment: 		").append(comment);
		}
		if (message != null) {
			sb.append("\nMessage: 		").append(message);
		}
		if (xqueryText != null) {
			sb.append("\nXquery: 		").append(xqueryText);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Log";
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
