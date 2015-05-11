package com.tomecode.soa.ora.osb10g.services;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.DataLayout;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.LogicFlyout;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Split Join process structure
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class SplitJoinStructure extends OsbActivity {

	/**
	 * parent project
	 */
	private final SplitJoin splitJoin;

	/**
	 * Constructor
	 * 
	 * @param project
	 */
	public SplitJoinStructure(SplitJoin splitJoin) {
		super(splitJoin.getName(), null);
		this.splitJoin = splitJoin;
	}

	/**
	 * @return the splitJoin
	 */
	public final SplitJoin getSplitJoin() {
		return splitJoin;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN;
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
		return null;
	}

}
