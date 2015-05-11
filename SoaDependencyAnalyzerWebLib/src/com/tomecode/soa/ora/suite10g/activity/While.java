package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * wait activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class While extends Activity {

	private static final long serialVersionUID = 912793397218119096L;

	//@PropertyViewData(title = "Condition")
	private String condition;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param condition
	 * @param documentation
	 */
	public While(String name, String condition, String documentation) {
		super(name);
		this.condition = condition;
		this.documentation = documentation;
	}

	public Image getImage() {
		return ImageFactory.ORACLE_10G_WHILE;
	}

	/**
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
	}

	@Override
	public final String getType() {
		return "While";
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (condition != null) {
			sb.append("\nCondition: 	").append(condition);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: ").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(condition, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
