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
public class Wait extends Activity {

	private static final long serialVersionUID = 8837403543290074974L;

	//@PropertyViewData(title = "For")
	private String forr;

	//@PropertyViewData(title = "Until")
	private String until;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param forr
	 * @param until
	 * @param documentation
	 */
	public Wait(String name, String forr, String until, String documentation) {
		super(name);
		this.forr = forr;
		this.until = until;
		this.documentation = documentation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_WAIT;
	}

	@Override
	public final String getType() {
		return "Wait";
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (forr != null) {
			sb.append("\nForr: 			").append(forr);
		}
		if (forr != null) {
			sb.append("\nUntil: 		").append(until);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: ").append(documentation);
		}
		return sb.toString();
	}

	// /**
	// * find variable in activity
	// */
	// public final void findVariable(FindUsageVariableResult
	// findUsageVariableResult) {
	// if (variable != null &&
	// findUsageVariableResult.getVariable().toString().equals(variable)) {
	// findUsageVariableResult.addUsage(this);
	// }
	// }
}
