package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSServer;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * 
 * Endpoint protocol - JMS
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointJms extends EndpointConfig<JMSServer> {

	private ProviderSpecificJms providerSpecificJms;

	public EndpointJms() {
		super(ProviderProtocol.JMS);
	}

	/**
	 * @return the providerSpecificJms
	 */
	public final ProviderSpecificJms getProviderSpecificJms() {
		return providerSpecificJms;
	}

	/**
	 * @param providerSpecificJms
	 *            the providerSpecificJms to set
	 */
	public final void setProviderSpecificJms(ProviderSpecificJms providerSpecificJms) {
		this.providerSpecificJms = providerSpecificJms;
	}

	public final void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		boolean isQueue = true;
		if (providerSpecificJms != null) {
			isQueue = providerSpecificJms.isQueue();
		}

		OraSB10gBasicServiceParser.parseJMSServerUris(uris, nodes, isQueue);
	}

	public final void setParentService(OSBService parentService) {
		for (Node<JMSServer> jmsServer : nodes) {
			jmsServer.getObj().setParentService(parentService);
		}
		if (providerSpecificJms != null) {
			for (Node<JMSServer> jmsServer : providerSpecificJms.getJmsServers()) {
				jmsServer.getObj().setParentService(parentService);
			}
		}
	}
}
