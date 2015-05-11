package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * partner link in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 * 
 */
public final class PartnerLink extends Activity {

	private static final long serialVersionUID = -6888516272264499697L;

	//@PropertyViewData(title = "Partner Link Type")
	private String partnerLinkType;

	//@PropertyViewData(title = "My Role")
	private String myRole;

	//@PropertyViewData(title = "Partner Role")
	private String partnerRole;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param partnerLinkType
	 * @param myRole
	 * @param partnerRole
	 */
	public PartnerLink(String name, String partnerLinkType, String myRole, String partnerRole) {
		super(name);
		this.partnerLinkType = partnerLinkType;
		this.myRole = myRole;
		this.partnerRole = partnerRole;

	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_PARTNERLINK;
	}

	public final String getPartnerLinkType() {
		return partnerLinkType;
	}

	public final String getMyRole() {
		return myRole;
	}

	public final String getPartnerRole() {
		return partnerRole;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (partnerLinkType != null) {
			sb.append("\nPartner Link Type:	").append(partnerLinkType);
		}
		if (partnerRole != null) {
			sb.append("\nPartner Role: 		").append(partnerRole);
		}
		if (myRole != null) {
			sb.append("\nMy Role: 			").append(myRole);
		}
		return sb.toString();
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getType() {
		return "PartnerLink";
	}

	@Override
	public final void createListOfPartnerLinks(PartnerLinksUsage partnerLinksUsage) {
		partnerLinksUsage.addPartnerLink(this);
		super.createListOfPartnerLinks(partnerLinksUsage);
	}
}
