package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.PartnerLinkReference;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Receive activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Receive extends Activity implements PartnerLinkReference {

	private static final long serialVersionUID = 1068491215667638987L;

	//@PropertyViewData(title = "Variable")
	protected String variable;

	//@PropertyViewData(title = "Partner Link")
	protected String partnerLink;

	//@PropertyViewData(title = "PortType")
	protected String portType;

	//@PropertyViewData(title = "Operation")
	protected String operation;

	//@PropertyViewData(title = "Create Instance")
	protected String createInstance;

	//@PropertyViewData(title = "Documentation")
	protected String documentation;

	//@PropertyViewData(title = "Header Variable")
	protected String headerVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param partnerLink
	 * @param portType
	 * @param operation
	 * @param variable
	 * @param createInstance
	 * @param headerVariable
	 * @param documentation
	 */
	public Receive(String name, String partnerLink, String portType, String operation, String variable, String createInstance, String headerVariable, String documentation) {
		super(name);
		this.partnerLink = partnerLink;
		this.portType = portType;
		this.operation = operation;
		this.variable = variable;
		this.createInstance = createInstance;
		this.documentation = documentation;
		this.headerVariable = headerVariable;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_RECEIVE;
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

	/**
	 * @return the createInstance
	 */
	public final String getCreateInstance() {
		return createInstance;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (partnerLink != null) {
			sb.append("\nPartner Link: 		").append(partnerLink);
		}
		if (portType != null) {
			sb.append("\nPortType: 			").append(portType);
		}
		if (operation != null) {
			sb.append("\nOperation: 		").append(operation);
		}
		if (variable != null) {
			sb.append("\nVariable: 			").append(variable);
		}
		if (createInstance != null) {
			sb.append("\nCreate Instance: 	").append(createInstance);
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
		return "Receive";
	}

	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variable != null && variable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (headerVariable != null && headerVariable.equals(bpelVariable.getVariable().getName())) {
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
