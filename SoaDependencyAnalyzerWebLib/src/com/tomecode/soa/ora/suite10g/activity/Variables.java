package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Variables extends Activity {

	/**
	 * Constructor
	 */
	public Variables() {
		super("Variables");
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_VARIABLE;
	}

	@Override
	public final String getType() {
		return "Variables";
	}

}
