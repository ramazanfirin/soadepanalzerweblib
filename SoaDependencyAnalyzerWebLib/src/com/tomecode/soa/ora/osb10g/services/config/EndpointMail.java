package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.protocols.email.Email;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * End point protocol - EMAIL
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointMail extends EndpointConfig<Email> {

	public EndpointMail() {
		super(ProviderProtocol.EMAIL);
	}

	public void putAllURI(List<String> uris) {
		super.putAllURI(uris);
		for (String email : uris) {
			nodes.add(new Email(email));
		}
	}

	public final void setParentService(OSBService parentService) {
		for (Email email : nodes) {
			email.setParentService(parentService);
		}
	}
}
