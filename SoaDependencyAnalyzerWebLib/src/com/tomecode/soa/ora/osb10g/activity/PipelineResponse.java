package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: pipeline type="response"
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class PipelineResponse extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param errorHandler
	 */
	public PipelineResponse(String name, String errorHandler) {
		super(name, errorHandler);
	}

	public final void merge(PipelineResponse response) {
		this.errorHandler = response.getErrorHandlerName();
		for (Activity copyActivity : response.activities) {
			((OsbActivity) copyActivity).setParent(this);
			this.activities.add(copyActivity);
		}
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_RESPONSE;
	}

	@Override
	public final String getType() {
		return "Pipeline Response";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		super.findUsesForOsbVariables(osbVariableList);
	}
}
