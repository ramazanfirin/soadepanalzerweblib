package com.tomecode.soa.ora.suite11g.project.sca;

import java.io.File;

import com.tomecode.soa.ora.suite11g.project.Ora11gComposite;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Component in {@link Ora11gComposite} - Components
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class ScaComponent extends CompositeService {

	/**
	 * component file
	 */
	//@PropertyViewData(title = "File: ")
	protected File file;

	/**
	 * 
	 * @param name
	 *            component name
	 * @param file
	 *            component file
	 */
	public ScaComponent(String name, File file) {
		this(name, file, CompositeServiceType.COMPONENT);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            component name
	 * @param file
	 *            component file
	 * @param type
	 *            component type
	 */
	public ScaComponent(String name, File file, CompositeServiceType type) {
		super(name, type);
		this.file = file;
	}

	public final File getFile() {
		return file;
	}

}
