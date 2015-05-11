package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * empty activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Empty extends Activity {

	private static final long serialVersionUID = -2041759641287369784L;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 * @param documentation
	 */
	public Empty(String name, String documentation) {
		super(name);
		this.documentation = documentation;
	}

	@Override
	public String getToolTip() {
		if (documentation != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nDocumentation: ").append(documentation);
			return sb.toString();
		}
		return super.getToolTip();
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_EMPTY;
	}

	@Override
	public final String getType() {
		return "Empty";
	}
}
