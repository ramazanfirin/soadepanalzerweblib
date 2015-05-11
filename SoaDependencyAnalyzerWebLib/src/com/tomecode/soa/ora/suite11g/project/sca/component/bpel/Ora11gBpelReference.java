package com.tomecode.soa.ora.suite11g.project.sca.component.bpel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.ora.suite11g.project.sca.CompositeService;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Partner Link in BPEL process ( file *.componentType)
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Ora11gBpelReference implements Serializable {

	/**
	 * parent BPEL process
	 */
	private Ora11gBpelProcess bpelProcess;
	/**
	 * if "true" then rerefence is service
	 */
	private final boolean isService;
	/**
	 * partnerLink or reference name
	 */
	private final String name;
	/**
	 * WSDL location
	 */
	private String wsdlLocation;
	/**
	 * WSDL interface
	 */
	private String referenceInterface;
	/**
	 * Callback interface
	 */
	private String referenceCallbackInterface;

	private final List<CompositeService> dependencyServices;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param wsdlLocation
	 */
	public Ora11gBpelReference(String name, String wsdlLocation, boolean isService) {
		this.name = name;
		this.wsdlLocation = wsdlLocation;
		this.isService = isService;
		this.dependencyServices = new ArrayList<CompositeService>();
	}

	/**
	 * @return the isService
	 */
	public final boolean isService() {
		return isService;
	}

	/**
	 * @return the bpelProcess
	 */
	public final Ora11gBpelProcess getBpelProcess() {
		return bpelProcess;
	}

	/**
	 * @param bpelProcess
	 *            the bpelProcess to set
	 */
	final void setBpelProcess(Ora11gBpelProcess bpelProcess) {
		this.bpelProcess = bpelProcess;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the wsdlLocation
	 */
	public final String getWsdlLocation() {
		return wsdlLocation;
	}

	/**
	 * @return the referenceInterface
	 */
	public final String getReferenceInterface() {
		return referenceInterface;
	}

	/**
	 * @param referenceInterface
	 *            the referenceInterface to set
	 */
	public final void setReferenceInterface(String referenceInterface) {
		this.referenceInterface = referenceInterface;
	}

	/**
	 * @return the referenceCallbackInterface
	 */
	public final String getReferenceCallbackInterface() {
		return referenceCallbackInterface;
	}

	/**
	 * @param referenceCallbackInterface
	 *            the referenceCallbackInterface to set
	 */
	public final void setReferenceCallbackInterface(String referenceCallbackInterface) {
		this.referenceCallbackInterface = referenceCallbackInterface;
	}

	/**
	 * @return the dependencyService
	 */
	public final List<CompositeService> getDependencyServices() {
		return this.dependencyServices;
	}

	/**
	 * @param dependencyService
	 *            the dependencyService to set
	 */
	public final void setDependencyService(CompositeService dependencyService) {
		if (!this.dependencyServices.contains(dependencyService)) {
			this.dependencyServices.add(dependencyService);
		}
	}

}
