package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: stage
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Stage extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param errorHandler
	 */
	public Stage(String name, String errorHandler, String comment) {
		super(name, errorHandler);
		this.comment = comment;
	}

	public final Image getImage() {
		return ImageFactory.OSB_10G_PROXY_SERVICE;
	}

	public final String getToolTip() {
		if (comment != null && comment.length() != 0) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nComment:		").append(comment);
			return sb.toString();
		}
		return super.getToolTip();
	}

	@Override
	public final String getType() {
		return "Stage";
	}

}
