package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/***
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * java embedding
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public class JavaEmbedding extends Activity {

	/**
	 * language
	 */

	//@PropertyViewData(title = "Language")
	private String language;

	/**
	 * version
	 */
	//@PropertyViewData(title = "Version")
	private String version;

	//@PropertyViewData(title = "Body")
	private String body;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param version
	 * @param language
	 */
	public JavaEmbedding(String name, String version, String language, String body, String documentation) {
		super(name);
		this.version = version;
		this.language = language;
		this.body = body;
		this.documentation = documentation;
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (language != null) {
			sb.append("\nLanguage: 		").append(language);
		}
		if (version != null) {
			sb.append("\nVersion: 		").append(version);
		}
		if (body != null) {
			sb.append("\nBody: 			").append(body);
		}
		if (documentation != null) {
			sb.append("\nDocumentation: ").append(documentation);
		}
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Java Embedding";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_BPELX_EXEC;
	}

}
