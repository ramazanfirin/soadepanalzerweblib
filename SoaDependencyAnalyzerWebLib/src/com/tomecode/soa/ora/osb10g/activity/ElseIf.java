package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * parent element: ifThenElse - target element: case
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class ElseIf extends OsbActivity {

	//@PropertyViewData(title = "Condition")
	private String condition;

	/**
	 * Constructor
	 * 
	 * @param condition
	 */
	public ElseIf(String condition) {
		this.condition = condition;
	}

	public final String getToolTip() {
		if (condition != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nCondition: ").append(condition);
			return sb.toString();
		}
		return super.getToolTip();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ELSEIF;
	}

	@Override
	public final String getType() {
		return "Else If";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			if (condition != null && condition.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
