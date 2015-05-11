package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/***
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: partnerLinks
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class PartnerLinks extends OsbActivity {

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_EXTERNAL_SERVICE;
	}

	@Override
	public final String getType() {
		return "Partner Links";
	}

}
