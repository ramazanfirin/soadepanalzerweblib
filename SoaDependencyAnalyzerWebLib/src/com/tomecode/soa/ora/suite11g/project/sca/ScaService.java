package com.tomecode.soa.ora.suite11g.project.sca;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.suite11g.project.Ora11gComposite;
import com.tomecode.soa.ora.suite11g.project.Ora11gComposite.Binding;
import com.tomecode.soa.services.jca.JCABase;
import com.tomecode.soa.wsdl.Wsdl;
import com.tomecode.soa.wsdl.WsdlPortType;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Service in {@link Ora11gComposite} - Exposed Services
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class ScaService extends CompositeService implements Binding, ImageFace, ToolTip {

	/**
	 * service WSDL location
	 */
	//@PropertyViewData(title = "WSDL Location: ")
	private String wsdlLocation;
	/**
	 * service WSDL
	 */
	private Wsdl wsdl;

	/**
	 * concrete port types
	 */
	private final List<WsdlPortType> portTypes;
	/**
	 * JCA adapter reference
	 */
	protected JCABase jcaBase;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param wsdlLocation
	 * @param type
	 */
	public ScaService(String name, String wsdlLocation, CompositeServiceType type) {
		super(name, type);
		this.portTypes = new ArrayList<WsdlPortType>();
		if(wsdlLocation.indexOf("oramds")==0){
			System.out.println(" DB_WS "+wsdlLocation);
			this.wsdlLocation = wsdlLocation.substring(wsdlLocation.lastIndexOf("/")+1);
		}else
			this.wsdlLocation = wsdlLocation;
	}

	/**
	 * @return the wsdlLocation
	 */
	public final String getWsdlLocation() {
		return wsdlLocation;
	}

	public final Wsdl getWsdl() {
		return wsdl;
	}

	public final void setWsdl(Wsdl wsdl) {
		if(wsdl!=null) wsdl.setParent(this);
		this.wsdl = wsdl;
	}

	public final List<WsdlPortType> getPortTypes() {
		return portTypes;
	}

	public final void addPortType(String portTypeName) {
		if(wsdl!=null) this.portTypes.add(wsdl.findPortType(portTypeName));
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder("Type:		");
		sb.append(getType());
		if (name != null && name.trim().length() != 0) {
			sb.append("\nName:		").append(name);
		}
		if (wsdlLocation != null) {
			sb.append("\nWSDL Location:	").append(wsdlLocation);
		}
		if (jcaBase != null) {
			sb.append("\nJCA JNDI:		").append(jcaBase.getLocation());
			sb.append("\nWSDL File:		" + jcaBase.getWsdlFile());
			sb.append("\nJCA File: 		" + jcaBase.getFile());
		}
		return sb.toString();
	}
}
