package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * logic flyout in SplitJoin flow
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class LogicFlyout extends OsbActivity {

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_LOGIC_FLYOUT;
	}

	@Override
	public final String getType() {
		return "Logic Flyout";
	}

}
