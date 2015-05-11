package com.tomecode.soa.ora.suite10g.project;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Partner link operation
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Operation implements ImageFace {

	private static final long serialVersionUID = -5643471889740129373L;

	private Activity activity;

	/**
	 * WSDL operation
	 */
	private String operation;

	private PartnerLinkBinding partnerLinkBinding;

	/**
	 * owner project
	 */
	private Ora10gBpelProject ownerBpelProject;

	/**
	 * Constructor
	 * 
	 * @param activity
	 * @param name
	 * @param operation
	 * @param partnerLinkBinding
	 */
	public Operation(Activity activity, String operation, Ora10gBpelProject ownerBpelProject, PartnerLinkBinding partnerLinkBinding) {
		this.activity = activity;
		this.operation = operation;
		this.partnerLinkBinding = partnerLinkBinding;
		this.ownerBpelProject = ownerBpelProject;
	}

	/**
	 * WSDL operation in activity
	 * 
	 * @return
	 */
	public final String getOperation() {
		return operation;
	}

	public final Activity getActivity() {
		return activity;
	}

	public final PartnerLinkBinding getPartnerLinkBinding() {
		return partnerLinkBinding;
	}

	public final boolean hasChildren() {
		if (partnerLinkBinding == null) {
			return false;
		}
		return partnerLinkBinding.hasChildren();
	}

	public final Object[] getChildren() {
		return partnerLinkBinding.getChildren();
	}

	public final Ora10gBpelProject getOwnerBpelProject() {
		return ownerBpelProject;
	}

	public final Ora10gBpelProject getPartnerLinkBpelProcess() {
		return partnerLinkBinding.getDependencyBpelProject();
	}

	public final String toString() {
		if (activity == null) {
			return (operation == null ? "" : operation);
		}
		return activity.toString() + (operation == null ? "" : " - " + operation);
	}

	public final Operation clone() {
		return new Operation(activity, operation, ownerBpelProject, partnerLinkBinding);// ,
																						// activities);
	}

	@Override
	public final Image getImage(boolean small) {
		if (activity != null) {
			return activity.getImage(small);
		}
		return null;
	}

	@Override
	public final String getToolTip() {
		return activity.getToolTip();
	}
}
