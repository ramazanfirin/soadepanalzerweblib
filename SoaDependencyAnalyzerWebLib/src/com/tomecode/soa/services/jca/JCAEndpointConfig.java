package com.tomecode.soa.services.jca;

import java.io.Serializable;
import java.util.Properties;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * basic class for {@link JCAEndpointActivation}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
public abstract class JCAEndpointConfig implements Serializable{

	private String className;

	////@PropertyViewData(title = "Port Type: ")
	private String portType;

	////@PropertyViewData(title = "Operation: ")
	private String operation;

	private Properties properties;

	private JCABase jcaBase;

	public final String getPortType() {
		return portType;
	}

	public final void setPortType(String portType) {
		this.portType = portType;
	}

	public final String getOperation() {
		return operation;
	}

	public final void setOperation(String operation) {
		this.operation = operation;
	}

	public final Properties getProperties() {
		return properties;
	}

	public final void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the jcaBase
	 */
	public final JCABase getJcaBase() {
		return jcaBase;
	}

	/**
	 * @param jcaBase
	 *            the jcaBase to set
	 */
	public final void setJcaBase(JCABase jcaBase) {
		this.jcaBase = jcaBase;
	}

	/**
	 * @return the className
	 */
	public final String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public final void setClassName(String className) {
		this.className = className;
	}

}
