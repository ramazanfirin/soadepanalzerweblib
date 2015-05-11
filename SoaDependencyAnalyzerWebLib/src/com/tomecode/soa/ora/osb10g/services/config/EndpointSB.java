package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.ora.osb10g.services.protocols.sb.SBJndi;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Endpoint protocol - SB - The SB transport allows Oracle products to
 * synchronously invoke an Oracle Service Bus proxy service using RMI.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointSB extends EndpointConfig<SBJndi> {

	public EndpointSB() {
		super(ProviderProtocol.SB);
	}

	public final void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseSBJndiUris(uris, nodes);
	}
}
