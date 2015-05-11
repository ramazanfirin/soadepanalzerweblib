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
 * JCA Socket adapter
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
@PropertyGroupView(title = "JCA Socket Adapter")
public final class JCASocket extends JCABase {

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
	public JCASocket(String name, File file, String wsdlFile) {
		super(JCAAdapterType.SOCKET, name, file, wsdlFile);
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JCA_SOCKET_SMALL;
		}
		return ImageFactory.JCA_SOCKET;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final String getType() {
		return "JCA Socket Adapter";
	}
}
