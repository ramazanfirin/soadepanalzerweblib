package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * sms activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Sms extends Activity {

	private static final long serialVersionUID = 509780617476931941L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Sms(String name) {
		super(name);
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_SMS;
	}

	@Override
	public final String getType() {
		return "Sms";
	}
}
