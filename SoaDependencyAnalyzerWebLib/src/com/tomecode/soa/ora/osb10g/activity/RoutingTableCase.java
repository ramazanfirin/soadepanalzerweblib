package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: case parent: routingTable
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class RoutingTableCase extends OsbActivity {

	//@PropertyViewData(title = "Operator")
	private String operator;

	//@PropertyViewData(title = "Value")
	private String value;

	/**
	 * Constructor
	 * 
	 * @param operator
	 * @param value
	 */
	public RoutingTableCase(String operator, String value) {
		this.operator = operator;
		this.value = value;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (operator != null) {
			sb.append("\nOperator: ").append(operator);
		}
		if (value != null) {
			sb.append("\nValue: ").append(value);
		}
		return sb.toString();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_CASE;
	}

	@Override
	public final String getType() {
		return "Case";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			if (operator != null && operator.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (value != null && value.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}
}
