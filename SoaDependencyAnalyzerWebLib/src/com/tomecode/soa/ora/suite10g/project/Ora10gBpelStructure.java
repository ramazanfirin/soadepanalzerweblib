package com.tomecode.soa.ora.suite10g.project;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * View the BPEL process tree
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(titleMethod = "getType")
public final class Ora10gBpelStructure extends Activity {

	private static final long serialVersionUID = -8082029461398462336L;

	private final Ora10gBpelProject bpelProject;

	/**
	 * Constructor
	 * 
	 * @param bpelProcess
	 */
	public Ora10gBpelStructure(Ora10gBpelProject bpelProcess) {
		super(bpelProcess.getName());
		this.bpelProject = bpelProcess;
	}

	public final String toString() {
		return bpelProject.toString();
	}

	public final Ora10gBpelProject getProject() {
		return bpelProject;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_BPEL_PROCESS;
	}

	@Override
	public final String getType() {
		return "BPEL Process";
	}

	/**
	 * find all variables in process
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
	 * find all partner links in process
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

	public final void readName() {
		this.name = bpelProject.getName();
	}

}
