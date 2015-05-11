package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Scope activity
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Scope extends Activity {

	//@PropertyViewData(title = "Documentation")
	protected String documentation;

	/**
	 * Scope
	 * 
	 * @param name
	 * @param documentation
	 */
	public Scope(String name, String documentation) {
		super(name);
		this.documentation = documentation;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_SCOPE;
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

	@Override
	public final String getType() {
		return "Scope";
	}
}
