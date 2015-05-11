package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: ifThenElse
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class IfThenElse extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	/**
	 * Constructor
	 * 
	 * @param comment
	 */
	public IfThenElse(String comment) {
		this.comment = comment;
	}

	public final boolean hasIf() {
		for (Activity activity : activities) {
			if (activity instanceof If) {
				return true;
			}
		}
		return false;
	}

	public final String getToolTip() {
		if (comment != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nComment:		").append(comment);
			return sb.toString();
		}
		return super.getToolTip();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_IFTHENELSE;
	}

	@Override
	public final String getType() {
		return "If Then";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		super.findUsesForOsbVariables(osbVariableList);
	}
}
