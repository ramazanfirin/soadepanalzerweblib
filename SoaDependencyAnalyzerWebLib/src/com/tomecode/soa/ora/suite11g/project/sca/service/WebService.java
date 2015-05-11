package com.tomecode.soa.ora.suite11g.project.sca.service;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite11g.project.sca.ScaService;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Web Service(SOAP) in Oracle SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "Web Service")
public final class WebService extends ScaService {

	private String interfacee;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            adapter name
	 * @param wsdlLocation
	 *            WSDL location
	 * @param type
	 *            service type
	 */
	public WebService(String name, String wsdlLocation, CompositeServiceType type) {
		super(name, wsdlLocation, type);
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_10G_ESB_SOAP_SERVICE;
	}

	@Override
	public final String getType() {
		return "Web Service";
	}

	public final void setInterface(String interfacee) {
		this.interfacee = interfacee;
	}

	public final String getInterface() {
		return interfacee;
	}
}
