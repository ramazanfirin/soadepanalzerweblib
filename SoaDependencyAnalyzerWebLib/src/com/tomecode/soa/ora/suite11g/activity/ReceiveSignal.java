package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * receive signal in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class ReceiveSignal extends Activity {

	//@PropertyViewData(title = "Label")
	private String label;

	//@PropertyViewData(title = "From")
	private String from;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param label
	 * @param from
	 */
	public ReceiveSignal(String name, String label, String from) {
		super(name);
		this.label = label;
		this.from = from;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (label != null) {
			sb.append("\nLabel: ").append(label);
		}
		if (from != null) {
			sb.append("\nFrom: 	").append(from);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "ReceiveSignal";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_11G_RECEIVESIGNAL;
	}

}
