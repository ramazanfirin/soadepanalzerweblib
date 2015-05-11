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
 * JCA FTP adapter
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
@PropertyGroupView(title = "JCA FTP Adapter")
public final class JCAFtp extends JCABase {

	//@PropertyViewData(title = "UI include Wildcard: ")
	private String UIincludeWildcard;

	//@PropertyViewData(title = "UI exclude Wildcard: ")
	private String UIexcludeWildcard;

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
	public JCAFtp(String name, File file, String wsdlFile) {
		super(JCAAdapterType.FTP, name, file, wsdlFile);
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JCA_FTP_ADAPTER_SMALL;
		}
		return ImageFactory.JCA_FTP_ADAPTER;
	}

	public final String getUIincludeWildcard() {
		return UIincludeWildcard;
	}

	public final void setUIincludeWildcard(String uIincludeWildcard) {
		UIincludeWildcard = uIincludeWildcard;
	}

	public final String getUIexcludeWildcard() {
		return UIexcludeWildcard;
	}

	public final void setUIexcludeWildcard(String uIexcludeWildcard) {
		UIexcludeWildcard = uIexcludeWildcard;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getType() {
		return "JCA FTP Adapter";
	}
}
