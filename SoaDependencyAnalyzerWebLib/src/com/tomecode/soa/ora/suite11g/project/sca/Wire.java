package com.tomecode.soa.ora.suite11g.project.sca;

import java.io.Serializable;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Wire in compoiste.xml
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Wire implements Serializable{

	/**
	 * source service
	 */
	private CompositeService source;
	/**
	 * target service
	 */
	private CompositeService target;

	/**
	 * @return the source
	 */
	public final CompositeService getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public final void setSource(CompositeService source) {
		if (source != null) {
			this.source = source;
		}
	}

	/**
	 * @return the target
	 */
	public final CompositeService getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public final void setTarget(CompositeService target) {
		if (target != null) {
			this.target = target;
		}
	}

	public final String toString() {
		return source + "->" + target;
	}
}
