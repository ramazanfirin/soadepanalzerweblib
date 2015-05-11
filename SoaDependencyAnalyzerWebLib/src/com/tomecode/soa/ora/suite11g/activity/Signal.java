package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * signal in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Signal extends Activity {

	//@PropertyViewData(title = "Label")
	private String label;

	//@PropertyViewData(title = "To")
	private String to;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param label
	 * @param from
	 */
	public Signal(String name, String label, String to) {
		super(name);
		this.label = label;
		this.to = to;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (label != null) {
			sb.append("\nLabel: ").append(label);
		}
		if (to != null) {
			sb.append("\nTo: 	").append(to);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Signal";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_11G_SIGNAL;
	}
}
