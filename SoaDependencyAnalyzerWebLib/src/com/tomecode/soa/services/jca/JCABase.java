package com.tomecode.soa.services.jca;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.protocols.jca.JCAAdapterType;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * JCA basic class
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
public abstract class JCABase implements ImageFace, ToolTip {

	//@PropertyViewData(title = "Name: ")
	protected final String name;

	//@PropertyViewData(title = "JCA File: ")
	protected final File file;

	//@PropertyViewData(title = "WSDL Location: ")
	protected final String wsdlFile;

	//@PropertyViewData(title = "Location: ")
	private String location;

	//@PropertyViewData(title = "Adapter Ref: ")
	private String adapterRef;

	private final JCAAdapterType jcaAdapterType;

	private final List<JCAEndpointActivation> endpointActivations;

	private final List<JCAEndpointInteraction> endpointInteractions;

	/**
	 * Constructor
	 * 
	 * @param jcaAdapterType
	 *            adapter type
	 * @param name
	 *            adapter name
	 * @param file
	 *            adapter configuration file
	 * @param wsdlFile
	 *            adapter WSDL file
	 */
	public JCABase(JCAAdapterType jcaAdapterType, String name, File file, String wsdlFile) {
		this.endpointActivations = new ArrayList<JCAEndpointActivation>();
		this.endpointInteractions = new ArrayList<JCAEndpointInteraction>();
		this.jcaAdapterType = jcaAdapterType;
		this.name = name;
		this.file = file;
		this.wsdlFile = wsdlFile;
	}

	/**
	 * @return the jcaAdapterType
	 */
	public final JCAAdapterType getAdapterType() {
		return jcaAdapterType;
	}

	/**
	 * @return the wsdlFile
	 */
	public final String getWsdlFile() {
		return wsdlFile;
	}

	/**
	 * @return the file
	 */
	public final File getFile() {
		return file;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * add {@link EndpointActivation}
	 * 
	 * @param endpointActivation
	 */
	public final void addEndpointActivation(JCAEndpointActivation endpointActivation) {
		endpointActivation.setJcaBase(this);
		this.endpointActivations.add(endpointActivation);
	}

	/**
	 * add {@link EndpointInteraction}
	 * 
	 * @param endpointInteraction
	 */
	public final void addEndpointInteraction(JCAEndpointInteraction endpointInteraction) {
		endpointInteraction.setJcaBase(this);
		this.endpointInteractions.add(endpointInteraction);
	}

	/**
	 * @return the endpointActivations
	 */
	public final List<JCAEndpointActivation> getEndpointActivations() {
		return endpointActivations;
	}

	/**
	 * @return the endpointInteractions
	 */
	public final List<JCAEndpointInteraction> getEndpointInteractions() {
		return endpointInteractions;
	}

	/**
	 * @return the location
	 */
	public final String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public final void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the adapterRef
	 */
	public final String getAdapterRef() {
		return adapterRef;
	}

	/**
	 * @param adapterRef
	 *            the adapterRef to set
	 */
	public final void setAdapterRef(String adapterRef) {
		this.adapterRef = adapterRef;
	}

	public final boolean containsOperation(String operation) {
		for (JCAEndpointActivation endpointActivation : endpointActivations) {
			if (endpointActivation.getOperation().equals(operation)) {
				return true;
			}
		}

		for (JCAEndpointInteraction endpointInteraction : endpointInteractions) {
			if (endpointInteraction.getOperation().equals(operation)) {
				return true;
			}
		}
		return false;
	}

	public final JCAEndpointActivation findEndpointActivation(String portTypeName, String wsdlOperationName) {
		for (JCAEndpointActivation endpointActivation : endpointActivations) {
			if (endpointActivation.getPortType().equals(portTypeName) && endpointActivation.getOperation().endsWith(wsdlOperationName)) {
				return endpointActivation;
			}
		}
		return null;
	}

	public final JCAEndpointInteraction findEndpointInteraction(String portTypeName, String wsdlOperationName) {
		for (JCAEndpointInteraction endpointInteraction : endpointInteractions) {
			if (endpointInteraction.getPortType().equals(portTypeName) && endpointInteraction.getOperation().endsWith(wsdlOperationName)) {
				return endpointInteraction;
			}
		}
		return null;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: 				").append(getType());
		sb.append("\nName:				").append(getName());
		sb.append("\nJNDI Location:		").append(location != null ? location : "");
		sb.append("\nJCA File:			").append(file != null ? file : "");
		sb.append("\nWSDL File:			").append(wsdlFile != null ? wsdlFile : "");
		return sb.toString();
	}

}
