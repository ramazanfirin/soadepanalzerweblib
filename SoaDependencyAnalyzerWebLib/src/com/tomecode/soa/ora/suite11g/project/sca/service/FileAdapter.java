package com.tomecode.soa.ora.suite11g.project.sca.service;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite11g.project.Ora11gComposite.BindingJCA;
import com.tomecode.soa.ora.suite11g.project.sca.ScaService;
import com.tomecode.soa.services.jca.JCABase;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * FILE adapter in Oracle SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "File Adapter")
public final class FileAdapter extends ScaService implements BindingJCA {

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
	public FileAdapter(String name, String wsdlLocation, CompositeServiceType type) {
		super(name, wsdlLocation, type);
	}

	@Override
	public final JCABase getJcaBase() {
		return jcaBase;
	}

	@Override
	public final void setJcaBase(JCABase jcaBase) {
		if (jcaBase != null) {
			this.jcaBase = jcaBase;
		}
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_10G_ESB_FILE_ADAPTER;
	}

	@Override
	public final String getType() {
		return "File Adapter";
	}
}
