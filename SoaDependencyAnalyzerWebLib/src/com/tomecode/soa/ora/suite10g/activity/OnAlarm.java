package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * onAlarm activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class OnAlarm extends Activity {

	private static final long serialVersionUID = 8364805233236556426L;

	//@PropertyViewData(title = "For")
	private String forr;

	//@PropertyViewData(title = "Until")
	private String until;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param variable
	 */
	public OnAlarm(String forr, String until, String documentation) {
		super(null);
		this.forr = forr;
		this.until = until;
		this.documentation = documentation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_ONALARM;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (forr != null) {
			sb.append("\nFor: 			").append(forr);
		}
		if (until != null) {
			sb.append("\nUntil: 		").append(until);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: ").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "OnAlarm";
	}
}
