package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: default-branch
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class DefaultBranch extends OsbActivity {

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_REQUEST;
	}

	@Override
	public final String getType() {
		return "Default Branch";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		super.findUsesForOsbVariables(osbVariableList);
	}
}
