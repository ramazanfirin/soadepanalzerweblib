package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.PartnerLinkReference;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * OnMessage activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class OnMessage extends Activity implements PartnerLinkReference {

	private static final long serialVersionUID = 1604503742786050538L;

	//@PropertyViewData(title = "Variable")
	private String variable;

	//@PropertyViewData(title = "Partner Link")
	private String partnerLink;

	//@PropertyViewData(title = "PortType")
	private String portType;

	//@PropertyViewData(title = "Operation")
	private String operation;

	//@PropertyViewData(title = "Header Variable")
	private String headerVariable;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param variable
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 * @param headerVariable
	 * @param documentation
	 */
	public OnMessage(String variable, String partnerLink, String portType, String operation, String headerVariable, String documentation) {
		super();
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.portType = portType;
		this.operation = operation;
		this.headerVariable = headerVariable;
		this.documentation = documentation;
	}

	public final String getHeaderVariable() {
		return headerVariable;
	}

	public final String getVariable() {
		return variable;
	}

	@Override
	public final String getPartnerLink() {
		if (partnerLink != null) {
			int index = partnerLink.indexOf(":");
			if (index != -1) {
				return partnerLink.substring(index + 1, partnerLink.length());
			}
		}
		return partnerLink;
	}

	@Override
	public final String getPortType() {
		if (portType != null) {
			int index = portType.indexOf(":");
			if (index != -1) {
				return portType.substring(index + 1, portType.length());
			}
		}
		return portType;

	}

	@Override
	public final String getOperation() {
		if (operation != null) {
			int index = operation.indexOf(":");
			if (index != -1) {
				return operation.substring(index + 1, operation.length());
			}
		}
		return operation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_ONMESSAGE;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (partnerLink != null) {
			sb.append("\nPartner Link: 		").append(partnerLink);
		}
		if (portType != null) {
			sb.append("\nPort Type:			").append(portType);
		}
		if (operation != null) {
			sb.append("\nOperation:	 		").append(operation);
		}
		if (variable != null) {
			sb.append("\nVariable: 			").append(variable);
		}
		if (headerVariable != null) {
			sb.append("\nHeader Variable: 	").append(headerVariable);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: 	").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "OnMessage";
	}

	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (headerVariable != null && headerVariable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (variable != null && variable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

	@Override
	public final void findUsesForPartnerLink(PartnerLinksUsage partnerLinksUsage) {
		partnerLinksUsage.addActivity(partnerLink, this);
		super.findUsesForPartnerLink(partnerLinksUsage);
	}
}
