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
 * JCA JMS adapter
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
@PropertyGroupView(title = "JCA JMS Adapter")
public final class JCAJms extends JCABase {

	//@PropertyViewData(title = "UI Connection Name: ")
	private String uiConnectionName;

	//@PropertyViewData(title = "UI Jms Provider: ")
	private String uiJmsProvider;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            JCA adapter name
	 * @param file
	 *            JCA adapter file
	 * @param wsdlFile
	 *            WSDL
	 */
	public JCAJms(String name, File file, String wsdlFile) {
		super(JCAAdapterType.JMS, name, file, wsdlFile);
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JCA_JMS_ADAPTER_SMALL;
		}
		return ImageFactory.JCA_JMS_ADAPTER;
	}

	public final String getUiConnectionName() {
		return uiConnectionName;
	}

	public final void setUiConnectionName(String uiConnectionName) {
		this.uiConnectionName = uiConnectionName;
	}

	public final String getUiJmsProvider() {
		return uiJmsProvider;
	}

	public final void setUiJmsProvider(String uiJmsProvider) {
		this.uiJmsProvider = uiJmsProvider;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getType() {
		return "JCA JMS Adapter";
	}
}
