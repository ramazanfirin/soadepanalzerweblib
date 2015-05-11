package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: scope in SplitJoin flow
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class Scope extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Scope(String name) {
		super(name, null);
	}

	public final String toString() {
		return name == null ? "Scope" : name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_SCOPE;
	}

	public final OsbActivity getDataFlyout() {
		for (Activity activity : activities) {
			if (activity instanceof DataLayout) {
				return (OsbActivity) activity;
			}
		}
		return null;
	}

	public final OsbActivity getLogicFlyout() {
		for (Activity activity : activities) {
			if (activity instanceof LogicFlyout) {
				return (OsbActivity) activity;
			}
		}
		return null;
	}

	@Override
	public final String getType() {
		return "Scope";
	}

}
