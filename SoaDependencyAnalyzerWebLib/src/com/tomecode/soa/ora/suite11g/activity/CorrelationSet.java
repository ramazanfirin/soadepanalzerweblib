package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Correlation set
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class CorrelationSet extends Activity {

	//@PropertyViewData(title = "Properties")
	private String properties;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param properties
	 */
	public CorrelationSet(String name, String properties) {
		super(name);
		this.properties = properties;
	}

	@Override
	public final String getToolTip() {
		if (properties != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nProperties: ").append(properties);
			return sb.toString();
		}
		return super.getToolTip();
	}

	@Override
	public final String getType() {
		return "correlationSet";
	}

	@Override
	public final Image getImage() {
		return null;
	}

}
