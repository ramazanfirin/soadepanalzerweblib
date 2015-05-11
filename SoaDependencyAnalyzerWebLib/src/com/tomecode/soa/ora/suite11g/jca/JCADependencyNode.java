package com.tomecode.soa.ora.suite11g.jca;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.services.jca.JCABase;
import com.tomecode.soa.services.jca.JCAEndpointConfig;
import com.tomecode.soa.wsdl.WsdlOperation;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * helper class for visualize dependencies in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
public final class JCADependencyNode implements ImageFace, ToolTip {

	private final JCAEndpointConfig endpointConfig;

	private final WsdlOperation wsdlOperation;

	/**
	 * 
	 * @param endpointConfig
	 * @param wsdlOperation
	 *            {@link WsdlOperation} which has dependency on JCA adapter
	 */
	public JCADependencyNode(JCAEndpointConfig endpointConfig, WsdlOperation wsdlOperation) {
		this.endpointConfig = endpointConfig;
		this.wsdlOperation = wsdlOperation;
	}

	/**
	 * @return the jcaBase
	 */
	//@PropertyViewDataInsideObject
	public final JCABase getJcaBase() {
		return endpointConfig.getJcaBase();
	}

	/**
	 * @return the endpointConfig
	 */
	public final JCAEndpointConfig getEndpointConfig() {
		return endpointConfig;
	}

	/**
	 * @return the wsdlOperation
	 */
	public final WsdlOperation getWsdlOperation() {
		return wsdlOperation;
	}

	@Override
	public final Image getImage(boolean small) {
		return endpointConfig.getJcaBase().getImage(small);
	}

	@Override
	public final String getToolTip() {
		return endpointConfig.getJcaBase().getToolTip();
	}

	public final String toString() {
		return endpointConfig.getJcaBase().toString();
	}

	@Override
	public final String getType() {
		return endpointConfig.getJcaBase().getType();
	}

}
