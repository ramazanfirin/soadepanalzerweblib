package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.PartnerLinkReference;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Invoke BPEL activity
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Invoke extends Activity implements PartnerLinkReference {

	private static final long serialVersionUID = 4576862167470771828L;

	//@PropertyViewData(title = "Input Variable")
	private String inputVariable;

	//@PropertyViewData(title = "Output Variable")
	private String outputVariable;

	//@PropertyViewData(title = "Partner Link")
	private String partnerLink;

	//@PropertyViewData(title = "PortType")
	private String portType;

	//@PropertyViewData(title = "Operation")
	private String operation;

	//@PropertyViewData(title = "Input Header Variable")
	private String inputHeaderVariable;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param inputVariable
	 * @param outputVariable
	 * @param partnerLink
	 * @param operation
	 */
	public Invoke(String name, String partnerLink, String portType, String operation, String inputVariable, String outputVariable, String inputHeaderVariable, String documentation) {
		super(name);
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
		this.partnerLink = partnerLink;
		this.operation = operation;
		this.portType = portType;
		this.inputHeaderVariable = inputHeaderVariable;
		this.documentation = documentation;
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

	public final String getInputVariable() {
		return inputVariable;
	}

	public final String getOutputVariable() {
		return outputVariable;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_INVOKE;
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
		if (inputVariable != null) {
			sb.append("\nInput Variable: 		").append(inputVariable);
		}
		if (outputVariable != null) {
			sb.append("\nOutput Variable: 	").append(outputVariable);
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
		return "Invoke";
	}

	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (inputVariable != null && inputVariable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (outputVariable != null && outputVariable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (inputHeaderVariable != null && inputHeaderVariable.equals(bpelVariable.getVariable().getName())) {
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
