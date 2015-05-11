package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: skip
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Skip extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	/**
	 * Constructor
	 * 
	 * @param commnet
	 */
	public Skip(String commnet) {
		this.comment = commnet;
	}

	public final String getToolTip() {
		if (comment != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nComment: ").append(comment);
			return sb.toString();
		}
		return super.getToolTip();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SKIP;
	}

	@Override
	public final String getType() {
		return "Skip";
	}
}
