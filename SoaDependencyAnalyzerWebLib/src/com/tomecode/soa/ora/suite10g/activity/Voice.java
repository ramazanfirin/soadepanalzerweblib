package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/***
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * voice activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Voice extends Activity {

	private static final long serialVersionUID = -7771451840355025106L;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param documentation
	 */
	public Voice(String name, String documentation) {
		super(name);
		this.documentation = documentation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_VOICE;
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
		return "Voice";
	}
}
