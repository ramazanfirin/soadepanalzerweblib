package com.tomecode.soa.ora.osb10g.services.config;

import com.tomecode.soa.ora.osb10g.services.SplitJoin;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Endpoint protocol - FLOW - {@link SplitJoin}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@SuppressWarnings("rawtypes")
public final class EndpointFlow extends EndpointConfig {

	public EndpointFlow() {
		super(ProviderProtocol.FLOW);
	}

}
