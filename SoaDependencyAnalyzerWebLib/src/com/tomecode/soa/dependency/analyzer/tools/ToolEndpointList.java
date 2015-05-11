package com.tomecode.soa.dependency.analyzer.tools;

/**
 * (c) Copyright Tomecode.com, 2010 -2011. All rights reserved.
 * 
 * 
 * interface for implementing method for create {@link EndpointList}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public interface ToolEndpointList {

	/**
	 * 
	 * 
	 * 
	 * @param endpointList
	 */
	void calculateEndpointList(EndpointList endpointList);
}
