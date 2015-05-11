package com.tomecode.soa.wsdl;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Role for {@link PartnerLinkType} in WSDL
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Role {

	private final PartnerLinkType partnerLinkType;

	private final String name;

	private final WsdlPortType portType;

	/**
	 * 
	 * @param name
	 * @param partnerLinkType
	 * @param portType
	 */
	public Role(String name, PartnerLinkType partnerLinkType, WsdlPortType portType) {
		this.partnerLinkType = partnerLinkType;
		this.name = name;
		this.portType = portType;
	}

	/**
	 * @return the partnerLinkType
	 */
	public final PartnerLinkType getPartnerLinkType() {
		return partnerLinkType;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the portType
	 */
	public final WsdlPortType getPortType() {
		return portType;
	}

}
