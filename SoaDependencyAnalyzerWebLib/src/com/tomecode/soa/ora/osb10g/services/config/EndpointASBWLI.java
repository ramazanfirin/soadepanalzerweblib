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
public final class EndpointASBWLI extends EndpointConfig implements ImageFace{

	//@PropertyViewData(title = "Provider ID:")
	private String providerId;
	
	private AsbDbTransportParams asbDbTransportParams = new AsbDbTransportParams();
	
	
	public EndpointASBWLI() {
		super(ProviderProtocol.ASB_BPM);
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
			return ImageFactory.BPM_IMAGE_SMALL;
		}
		return ImageFactory.BPM_IMAGE_SMALL;
	}

	@Override
	public String getToolTip() {
		return "ASB";
	}

	
	public AsbDbTransportParams getAsbDbTransportParams() {
		return asbDbTransportParams;
	}

	public void setAsbDbTransportParams(AsbDbTransportParams asbDbTransportParams) {
		this.asbDbTransportParams = asbDbTransportParams;
	}
	
	@Override
	public String toString() {		
		return this.uris.get(0).toString().replace("BPMJNDIProvider", "");
	}
	
	
}
