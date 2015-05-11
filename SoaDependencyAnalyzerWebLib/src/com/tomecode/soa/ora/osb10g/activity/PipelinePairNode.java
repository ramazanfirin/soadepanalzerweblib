package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * pipeline-node in proxy
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class PipelinePairNode extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param errorHandler
	 */
	public PipelinePairNode(String name, String comment, String errorHandler) {
		super(name, errorHandler);
		this.comment = comment;
	}

	public final String getToolTip() {
		if (comment != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nComment: ").append(comment);
			return sb.toString();
		}
		return super.getToolTip();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_PARI_NODE;
	}

	@Override
	public final String getType() {
		return "PipelinePairNode";
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		super.findUsesForOsbVariables(osbVariableList);
	}
}
