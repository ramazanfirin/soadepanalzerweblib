package com.tomecode.soa.activity;

/**
 * 
 * Special interface only for activities in the BPEL processes, this interface
 * implementing only the activities with attributes:
 * 
 * partnerLink, portType, operation
 * 
 * 
 * for example: invoke, receive, onMessage, reply
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public interface PartnerLinkReference {
	/**
	 * get partnerLink name in activity
	 * 
	 * @return
	 */
	public String getPartnerLink();

	/**
	 * get WSDL PortType in activity
	 * 
	 * @return
	 */
	public String getPortType();

	/**
	 * get WSDL operation in activity
	 * 
	 * @return
	 */
	public String getOperation();

}
