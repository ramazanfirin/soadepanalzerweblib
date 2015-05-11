package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class FaultHandlers extends Activity {

	/**
	 * Constructor
	 */
	public FaultHandlers() {
		super("FaultHandlers");
	}

	// TODO: preverit obrazovk
	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public final String getType() {
		return "FaultHandlers";
	}

}
