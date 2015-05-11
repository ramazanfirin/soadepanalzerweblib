package com.tomecode.soa.ora.osb10g.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: javaCallout
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class JavaCallout extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;
	//@PropertyViewData(title = "Archive")
	private String archive;
	//@PropertyViewData(title = "Class Name")
	private String className;
	//@PropertyViewData(title = "Method")
	private String method;
	private List<String> expres = new ArrayList<String>();

	/**
	 * Constructor
	 * 
	 * @param comment
	 * @param archive
	 * @param className
	 * @param method
	 */
	public JavaCallout(String comment, String archive, String className, String method) {
		super();
		this.comment = comment;
		this.archive = archive;
		this.className = className;
		this.method = method;
	}

	public final void addExpressions(String exp) {
		if (exp != null && exp.trim().length() != 0) {
			this.expres.add(exp);
		}
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (comment != null) {
			sb.append("\nComment: 		").append(comment);
		}
		if (archive != null) {
			sb.append("\nArchive: 		").append(archive);
		}
		if (className != null) {
			sb.append("\nClass Name: 	").append(className);
		}
		if (method != null) {
			sb.append("\nMethod: 		").append(method);
		}
		return sb.toString();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_JAVA_CALLOUT;
	}

	@Override
	public final String getType() {
		return "Java Callout";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			if (archive != null && archive.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (className != null && className.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			} else if (method != null && method.contains("$" + osbVariable.getVariable())) {
				osbVariable.addUsageActivty(this);
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}

}
