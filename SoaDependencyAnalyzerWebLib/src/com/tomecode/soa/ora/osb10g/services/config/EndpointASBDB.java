package com.tomecode.soa.ora.osb10g.services.config;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * 
 * Endpoint protocol - JPD
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@SuppressWarnings("rawtypes")
public final class EndpointASBDB extends EndpointConfig implements ImageFace{

	//@PropertyViewData(title = "Provider ID:")
	private String providerId;
	
	private AsbDbTransportParams asbDbTransportParams = new AsbDbTransportParams();
	
	
	public EndpointASBDB() {
		super(ProviderProtocol.ASB_DB);
	}
	
	/**
	 * @return the providerId
	 */
	public final String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId
	 *            the providerId to set
	 */
	public final void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	
	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.DB_SERVER;
		}
		return ImageFactory.DB_SERVER;
	}

	@Override
	public String getToolTip() {
		return "ASB_BPM";
	}

	
	public AsbDbTransportParams getAsbDbTransportParams() {
		return asbDbTransportParams;
	}

	public void setAsbDbTransportParams(AsbDbTransportParams asbDbTransportParams) {
		this.asbDbTransportParams = asbDbTransportParams;
	}
	
	@Override
	public String toString() {		
		return this.uris.get(0).toString() + "\n" + asbDbTransportParams.getProcedureName();
	}
	
	
}
