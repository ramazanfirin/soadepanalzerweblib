package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.ora.osb10g.services.protocols.jdp.JdpProvider;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Endpoint protocol - JPD
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointJPD extends EndpointConfig<JdpProvider> {

	public EndpointJPD() {
		super(ProviderProtocol.JPD);
	}

	public final void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseJDDProviderUris(uris, nodes);
	}
}
