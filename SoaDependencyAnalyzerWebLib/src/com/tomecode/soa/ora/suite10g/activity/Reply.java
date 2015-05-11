package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.PartnerLinkReference;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * reply activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Reply extends Activity implements PartnerLinkReference {

	private static final long serialVersionUID = 5210081469434718236L;

	//@PropertyViewData(title = "Variable")
	protected String variable;

	//@PropertyViewData(title = "Partner Link")
	protected String partnerLink;

	//@PropertyViewData(title = "PortType")
	protected String portType;

	//@PropertyViewData(title = "Operation")
	protected String operation;

	//@PropertyViewData(title = "Input Header Variable")
	protected String inputHeaderVariable;

	//@PropertyViewData(title = "Documentation")
	protected String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 * @param inputHeaderVariable
	 * @param documentation
	 */
	public Reply(String name, String variable, String partnerLink, String portType, String operation, String inputHeaderVariable, String documentation) {
		super(name);
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.portType = portType;
		this.operation = operation;
		this.inputHeaderVariable = inputHeaderVariable;
		this.documentation = documentation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_REPLY;
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

	public final String getVariable() {
		return variable;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (partnerLink != null) {
			sb.append("\nPartner Link:			").append(partnerLink);
		}
		if (portType != null) {
			sb.append("\nPortType:				").append(portType);
		}
		if (operation != null) {
			sb.append("\nOperation:			").append(operation);
		}
		if (inputHeaderVariable != null) {
			sb.append("\nInput Header Variable:	").append(inputHeaderVariable);
		}
		if (documentation != null) {
			sb.append("\nDocumentation:		").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Reply";
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

	@Override
	public final void findUsesForPartnerLink(PartnerLinksUsage partnerLinksUsage) {
		partnerLinksUsage.addActivity(partnerLink, this);
		super.findUsesForPartnerLink(partnerLinksUsage);
	}
}
