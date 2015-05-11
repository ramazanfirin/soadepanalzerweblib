package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * throw activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Throw extends Activity {

	private static final long serialVersionUID = -6296106634300381880L;

	//@PropertyViewData(title = "Fault Variable")
	private String faultVariable;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param faultVariable
	 * @param documentation
	 */
	public Throw(String name, String faultVariable, String documentation) {
		super(name);
		this.faultVariable = faultVariable;
		this.documentation = documentation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_THROW;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (faultVariable != null) {
			sb.append("\nFault Variable: 	").append(faultVariable);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: 	").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Throw";
	}

	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (faultVariable != null && faultVariable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}
}
