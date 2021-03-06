package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: Error
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Error extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;
	//@PropertyViewData(title = "Error Code")
	private String errCode;
	//@PropertyViewData(title = "Message")
	private String message;

	/**
	 * Constructor
	 * 
	 * @param errCode
	 * @param message
	 */
	public Error(String comment, String errCode, String message) {
		super();
		this.comment = comment;
		this.errCode = errCode;
		this.message = message;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (comment != null) {
			sb.append("\nComment: 		").append(comment);
		}
		if (errCode != null) {
			sb.append("\nError Code: 	").append(errCode);
		}
		if (message != null) {
			sb.append("\nMessage: 		").append(message);
		}
		return sb.toString();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_RAISE_ERROR;
	}

	@Override
	public final String getType() {
		return "Raise Error";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			if (errCode != null && errCode.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (message != null && message.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
