package com.tomecode.soa.wsdl;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Partnerlink type in WSDL -namespace:
 * http://docs.oasis-open.org/wsbpel/2.0/plnktype
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class PartnerLinkType {

	private final String name;

	private final List<Role> roles;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            partner link name
	 */
	public PartnerLinkType(String name) {
		this.name = name;
		this.roles = new ArrayList<Role>();
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the roles
	 */
	public final List<Role> getRoles() {
		return roles;
	}

	public final void addRole(Role role) {
		this.roles.add(role);
	}
}
