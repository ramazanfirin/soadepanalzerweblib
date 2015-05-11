package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * pick activity
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public class Pick extends Activity {

	//@PropertyViewData(title = "Create Instance")
	private String createInstance;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param createInstance
	 * @param documentation
	 */
	public Pick(String name, String createInstance, String documentation) {
		super(name);
		this.createInstance = createInstance;
		this.documentation = documentation;
	}

	public Image getImage() {
		return ImageFactory.ORACLE_10G_PICK;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (createInstance != null) {
			sb.append("\nCreate Instance: 	").append(createInstance);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: 	").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Pick";
	}
}
