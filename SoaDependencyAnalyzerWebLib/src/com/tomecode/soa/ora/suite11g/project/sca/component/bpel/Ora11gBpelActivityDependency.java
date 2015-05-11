package com.tomecode.soa.ora.suite11g.project.sca.component.bpel;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.PartnerLinkReference;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * BPEL dependencies, wrapper object, contains current activity and his
 * reference
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Ora11gBpelActivityDependency implements ImageFace {

	/**
	 * this activity has a link to another service
	 */
	private final Activity activity;

	/**
	 * reference
	 */
	private final Ora11gBpelReference bpelReference;

	public Ora11gBpelActivityDependency(Activity activity, Ora11gBpelReference bpelReference) {
		this.activity = activity;
		this.bpelReference = bpelReference;
	}

	public final Activity getActivity() {
		return activity;
	}

	/**
	 * @return the bpelReference
	 */
	public final Ora11gBpelReference getBpelReference() {
		return bpelReference;
	}

	public final boolean compareByOperation(String wsdlPortType, String wsdlOperation) {
		if (activity instanceof PartnerLinkReference) {
			PartnerLinkReference wsdlCallIndentification = (PartnerLinkReference) activity;
			return (wsdlPortType.equals(wsdlCallIndentification.getPortType()) && wsdlOperation.equals(wsdlCallIndentification.getOperation()));
		}

		return false;
	}

	@Override
	public final Image getImage(boolean small) {
		return activity.getImage(small);
	}

	@Override
	public final String getToolTip() {
		return activity.getToolTip();
	}

	public final String toString() {
		return activity.toString();
	}
}
