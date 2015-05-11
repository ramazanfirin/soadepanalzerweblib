package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * catch activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Catch extends Activity {

	private static final long serialVersionUID = 3511714056068910893L;

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
	public Catch(String name, String faultVariable, String documentation) {
		super(name);
		this.faultVariable = faultVariable;
		this.documentation = documentation;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_CATCH;
	}

	@Override
	public final String getToolTip() {
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
		return "Catch";
	}

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (faultVariable != null && faultVariable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
