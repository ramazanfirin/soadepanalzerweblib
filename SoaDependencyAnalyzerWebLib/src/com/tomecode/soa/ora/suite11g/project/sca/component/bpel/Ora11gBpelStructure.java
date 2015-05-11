package com.tomecode.soa.ora.suite11g.project.sca.component.bpel;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Tree structure of {@link Ora11gBpelProcess}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Ora11gBpelStructure extends Activity {

	private final Ora11gBpelProcess bpelProcess;

	/**
	 * Constructor
	 * 
	 * @param bpelProcess
	 */
	public Ora11gBpelStructure(Ora11gBpelProcess bpelProcess) {
		super(bpelProcess.getName());
		this.bpelProcess = bpelProcess;
	}

	/**
	 * @return the bpelProcess
	 */
	public final Ora11gBpelProcess getBpelProcess() {
		return bpelProcess;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_BPEL_PROCESS;
	}

	public final String toString() {
		return bpelProcess.toString();
	}

	@Override
	public final String getType() {
		return "BPEL Process";
	}

	/**
	 * find all variables
	 * 
	 * @param variableUsage
	 */
	public final void getAllVariables(BpelVariableUsage variableUsage) {
		for (Activity activity : activities) {
			activity.createListOfBpelVariables(variableUsage);
		}

		for (Activity activity : activities) {
			activity.findUsesForBpelVariables(variableUsage);
		}

	}

	/**
	 * find all partnerlinks
	 * 
	 * @param partnerLinksUsage
	 */
	public final void getAllPartnerLins(PartnerLinksUsage partnerLinksUsage) {
		for (Activity activity : activities) {
			activity.createListOfPartnerLinks(partnerLinksUsage);
		}
		for (Activity activity : activities) {
			activity.findUsesForPartnerLink(partnerLinksUsage);
		}
	}
}
