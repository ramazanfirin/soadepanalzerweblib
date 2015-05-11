package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.activity.Pick;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * pick in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Pick11g extends Pick {

	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param createInstance
	 * @param documentation
	 */
	public Pick11g(String name, String createInstance, String documentation, String skipCondition) {
		super(name, createInstance, documentation);
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
		return ImageFactory.ORACLE_11G_PICK;
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
