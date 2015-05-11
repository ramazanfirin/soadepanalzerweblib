package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: javaCallout in SplitJoin
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class JavaCallout extends OsbActivity {

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_JAVA_CALLOUT;
	}

	@Override
	public final String getType() {
		return "Java Callout";
	}

}
