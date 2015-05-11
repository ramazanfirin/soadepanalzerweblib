package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * flowN activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class FlowN extends Activity {

	private static final long serialVersionUID = 8165417878176837717L;

	//@PropertyViewData(title = "n")
	private String n;

	//@PropertyViewData(title = "Index Variable")
	private String indexVariable;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param n
	 * @param indexVariable
	 * @param documentation
	 */
	public FlowN(String name, String n, String indexVariable, String documentation) {
		super(name);
		this.n = n;
		this.indexVariable = indexVariable;
		this.documentation = documentation;
	}

	public Image getImage() {
		return ImageFactory.ORACLE_10G_FLOWN;
	}

	/**
	 * @return the n
	 */
	public final String getN() {
		return n;
	}

	/**
	 * @return the indexVariable
	 */
	public final String getIndexVariable() {
		return indexVariable;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (n != null) {
			sb.append("\nn: 				").append(n);
		}
		if (indexVariable != null) {
			sb.append("\nIndex Variable: 	").append(indexVariable);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: 	").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "FlowN";
	}

	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			if (variableUsage.containsVariable(n, bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			} else if (indexVariable != null && indexVariable.equals(bpelVariable.getVariable().getName())) {
				bpelVariable.addUsage(this);
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

}
