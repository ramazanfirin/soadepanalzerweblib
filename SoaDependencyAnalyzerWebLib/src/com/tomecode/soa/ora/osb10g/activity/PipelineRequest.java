package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: pipeline type="request"
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class PipelineRequest extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public PipelineRequest(String name, String errorHandler) {
		super(name, errorHandler);
	}

	public final void merge(PipelineRequest request) {
		this.errorHandler = request.getErrorHandlerName();
		for (Activity copyActivity : request.activities) {
			((OsbActivity) copyActivity).setParent(this);
			this.activities.add(copyActivity);
		}
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_REQUEST;
	}

	@Override
	public final String getType() {
		return "Pipeline Request";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		super.findUsesForOsbVariables(osbVariableList);
	}
}
