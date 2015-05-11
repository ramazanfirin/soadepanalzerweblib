package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: wsCallout
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class WsCallout extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Operation")
	private String operation;

	//@PropertyViewData(title = "Request Body")
	private String requestBody;

	//@PropertyViewData(title = "Request Header")
	private String requestHeader;

	//@PropertyViewData(title = "Response Body")
	private String responseBody;

	//@PropertyViewData(title = "Response Header")
	private String responseHeader;

	//@PropertyViewData(title = "Configured SOAP Boddy")
	private boolean configuredSoapBody;

	/**
	 * Constructor
	 * 
	 * @param comment
	 * @param operation
	 * @param requestBody
	 * @param requestHeader
	 * @param responseBody
	 * @param responseHeader
	 */
	public WsCallout(String comment, String operation, String requestBody, String requestHeader, String responseBody, String responseHeader) {
		this.comment = comment;
		this.operation = operation;
		this.requestBody = requestBody;
		this.requestHeader = requestHeader;
		this.responseBody = responseBody;
		this.responseHeader = responseHeader;
	}

	public final void setConfiguredSoapBody(boolean configuredSoapBody) {
		this.configuredSoapBody = configuredSoapBody;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (comment != null) {
			sb.append("\nComment:			").append(comment);
		}
		if (operation != null) {
			sb.append("\nOperation:			").append(operation);
		}
		if (requestBody != null) {
			if (configuredSoapBody) {
				sb.append("\nRequest Body: 		").append(requestBody);
			} else {
				sb.append("\nRequest Variable:	").append(requestBody);
			}
		}
		if (requestHeader != null) {
			sb.append("\nRequest Header:	").append(requestHeader);
		}
		if (responseBody != null) {
			if (configuredSoapBody) {
				sb.append("\nResponse Body:		").append(responseBody);
			} else {
				sb.append("\nResponse Variable:	").append(responseBody);
			}
		}
		if (responseHeader != null) {
			sb.append("\nResponse Header:	").append(responseHeader);
		}
		return sb.toString();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_WSCALLOUT;
	}

	@Override
	public final String getType() {
		return "Service Callout";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			if (operation != null && operation.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (requestBody != null && requestBody.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (requestHeader != null && requestHeader.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (responseBody != null && responseBody.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (responseHeader != null && responseHeader.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
