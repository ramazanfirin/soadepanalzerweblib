package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.activity.Switch;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * switch in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Switch11g extends Switch {

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param documentation
	 * @param skipCondition
	 */
	public Switch11g(String name, String documentation, String skipCondition) {
		super(name, documentation);
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		if (skipCondition != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nSkip Condition: ").append(skipCondition);
			return sb.toString();
		}
		return super.getToolTip();
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_11G_SWITCH;
	}

	@Override
	public final void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(skipCondition, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
