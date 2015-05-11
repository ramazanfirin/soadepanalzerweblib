package com.tomecode.soa.services.jca.adapter;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.jca.JCAAdapterType;
import com.tomecode.soa.services.jca.JCABase;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * JCA Database adapter
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
@PropertyGroupView(title = "JCA Database Adapter")
public final class JCADatabase extends JCABase {

	//@PropertyViewData(title = "UI Connection Name: ")
	private String UIConnectionName;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            JCA database adapter name
	 * @param file
	 *            JCA database adapter file
	 * @param wsdlFile
	 *            WSDL
	 */
	public JCADatabase(String name, File file, String wsdlFile) {
		super(JCAAdapterType.DATABASE, name, file, wsdlFile);
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JCA_DB_ADAPTER_SMALL;
		}
		return ImageFactory.JCA_DB_ADAPTER;
	}

	/**
	 * @return the uIConnectionName
	 */
	public final String getUIConnectionName() {
		return UIConnectionName;
	}

	/**
	 * @param uIConnectionName
	 *            the uIConnectionName to set
	 */
	public final void setUIConnectionName(String uIConnectionName) {
		UIConnectionName = uIConnectionName;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getType() {
		return "JCA Database Adapter";
	}
}
