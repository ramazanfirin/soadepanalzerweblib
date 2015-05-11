package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Transform activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public class Transform extends Activity {

	private static final long serialVersionUID = 752167762231326145L;

	private String fromVariable;
	private String toVariable;

	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param fromVariable
	 * @param toVariable
	 * @param documentation
	 */
	public Transform(String name, String fromVariable, String toVariable, String documentation) {
		super(name);
		this.fromVariable = fromVariable;
		this.toVariable = toVariable;
		this.documentation = documentation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_TRANSFORM;
	}

	public final String getFromVariable() {
		return fromVariable;
	}

	public final String getToVariable() {
		return toVariable;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (fromVariable != null) {
			sb.append("\nFrom Variable:	").append(fromVariable);
		}
		if (toVariable != null) {
			sb.append("\nTo Variable:	").append(toVariable);
		}
		if (documentation != null) {
			sb.append("\nDocumentation:	").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Transformation";
	}

	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(fromVariable, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (variableUsage.containsVariable(toVariable, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
