package com.tomecode.soa.ora.osb10g.services.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSServer;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * element: provider-specific for JMS
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProviderSpecificJms implements Serializable{

	private boolean queue;

	private boolean responseRequired;

	private final List<JMSServer> jmsServers;

	/**
	 * response URI
	 */
	private String responseURI;

	public ProviderSpecificJms() {
		jmsServers = new ArrayList<JMSServer>();
	}

	/**
	 * @return the queue
	 */
	public final boolean isQueue() {
		return queue;
	}

	/**
	 * @param queue
	 *            the queue to set
	 */
	public final void setQueue(boolean queue) {
		this.queue = queue;
	}

	/**
	 * @return the responseRequired
	 */
	public final boolean isResponseRequired() {
		return responseRequired;
	}

	/**
	 * @param responseRequired
	 *            the responseRequired to set
	 */
	public final void setResponseRequired(boolean responseRequired) {
		this.responseRequired = responseRequired;
	}

	/**
	 * @return the responseURI
	 */
	public final String getResponseURI() {
		return responseURI;
	}

	/**
	 * @param responseURI
	 *            the responseURI to set
	 */
	public final void setResponseURI(String responseURI) {
		this.responseURI = responseURI;
		OraSB10gBasicServiceParser.parseJMSServerUris(responseURI, jmsServers, queue);
	}

	public final List<JMSServer> getJmsServers() {
		return jmsServers;
	}

}
