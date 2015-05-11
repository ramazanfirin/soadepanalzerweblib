package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * compensate in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Compensate extends Activity {

	//@PropertyViewData(title = "Scope")
	private String scope;
	//@PropertyViewData(title = "Documentation")
	private String documentation;
	//@PropertyViewData(title = "Skip Condition")
	private String skipCondition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param scope
	 */
	public Compensate(String name, String scope, String documentation, String skipCondition) {
		super(name);
		this.scope = scope;
		this.documentation = documentation;
		this.skipCondition = skipCondition;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (scope != null) {
			sb.append("\nScope: 			").append(scope);
		}
		if (skipCondition != null) {
			sb.append("\nSkip Condition: 	").append(skipCondition);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: 	").append(documentation);
		}
		return super.getToolTip();
	}

	@Override
	public final String getType() {
		return "Compensate";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_11G_COMPENSATE;
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
