package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.services.jca.JCABase;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * JCA resource file in OSB
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
public final class ResourceJCA extends OSBService {

	private final JCABase jcaBase;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            resource file
	 * @param type
	 * @param jcaBase
	 */
	public ResourceJCA(File file, ServiceDependencyType type, JCABase jcaBase) {
		super(file, type);
		this.name = file.getName();
		this.jcaBase = jcaBase;
	}

	@Override
	public final Image getImage() {
		return jcaBase.getImage(true);
	}

	/**
	 * @return the jcaBase
	 */
	public final JCABase getJcaBase() {
		return jcaBase;
	}

}