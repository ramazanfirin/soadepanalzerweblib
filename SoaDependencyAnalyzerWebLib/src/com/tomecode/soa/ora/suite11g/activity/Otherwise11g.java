package com.tomecode.soa.ora.suite11g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.activity.Otherwise;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * case from switch in SOA Suite 11g;
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Otherwise11g extends Otherwise {

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param documentation
	 */
	public Otherwise11g(String documentation) {
		super();
		this.documentation = documentation;
	}

	@Override
	public final String getToolTip() {
		if (documentation != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nDocumentation: ").append(documentation);
			return sb.toString();
		}
		return super.getToolTip();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_11G_SWITCH;
	}

}
