package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: branch-node type: operation
 * 
 * @author Tomas Frastia
 * 
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class BranchNodeOperation extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param errorHandler
	 */
	public BranchNodeOperation(String name, String errorHandler) {
		super(name, errorHandler);
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BRANCH_OPERATION;
	}

	@Override
	public final String getType() {
		return "Branch Operation";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		super.findUsesForOsbVariables(osbVariableList);
	}
}
