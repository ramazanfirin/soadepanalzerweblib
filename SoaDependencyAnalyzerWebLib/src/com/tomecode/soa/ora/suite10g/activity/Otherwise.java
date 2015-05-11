package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * case from switch in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public class Otherwise extends Activity {

	private static final long serialVersionUID = -4292260142977278326L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Otherwise() {
		super("Otherwise");
	}

	public final String toString() {
		return name;
	}

	public Image getImage() {
		return ImageFactory.ORACLE_10G_SWITCH;
	}

	@Override
	public final String getType() {
		return "Otherwise";
	}
}
