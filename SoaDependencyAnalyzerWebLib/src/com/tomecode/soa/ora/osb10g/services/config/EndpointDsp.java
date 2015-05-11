package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.ora.osb10g.services.protocols.dsp.DSPServer;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Endpoint protocol - DSP (Oracle Data Service Integrator)
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointDsp extends EndpointConfig<DSPServer> {

	//@PropertyViewData(title = "Endpoint Request-response: ")
	private boolean requestResponse;

	public EndpointDsp() {
		super(ProviderProtocol.DSP);
	}

	public final void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseDSPServerUris(uris, nodes);
	}

	/**
	 * @return the requestResponse
	 */
	public final boolean isRequestResponse() {
		return requestResponse;
	}

	/**
	 * @param requestResponse
	 *            the requestResponse to set
	 */
	public final void setRequestResponse(boolean requestResponse) {
		this.requestResponse = requestResponse;
	}
}
