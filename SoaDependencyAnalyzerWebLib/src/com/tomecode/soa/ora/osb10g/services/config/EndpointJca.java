package com.tomecode.soa.ora.osb10g.services.config;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.jca.GeneralJCA;
import com.tomecode.soa.protocols.jca.JCAAdapterType;
import com.tomecode.soa.services.jca.JCABase;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * End point protocol - JCA
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointJca extends EndpointConfig<GeneralJCA> {

	//@PropertyViewData(title = "Adapter Name: ")
	private String name;

	private String refJcaFile;

	private String operation;

	private JCAAdapterType jcaAdapterType;

	public EndpointJca() {
		super(ProviderProtocol.JCA);
	}

	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JCA_TRANSPORT_SMALL;
		}
		return ImageFactory.JCA_TRANSPORT;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void addJcaAdapter(GeneralJCA adapter) {
		for (String uri : uris) {
			adapter.addJCAResourceJndi(uri);
		}
		nodes.add(adapter);
	}

	public final String getRefJcaFile() {
		return refJcaFile;
	}

	public final void setRefJcaFile(String refJcaFile) {
		this.refJcaFile = refJcaFile;
	}

	public final String getOperation() {
		return operation;
	}

	public final void setOperation(String operation) {
		this.operation = operation;
	}

	public final JCAAdapterType getJcaAdapterType() {
		return jcaAdapterType;
	}

	public final void setJcaAdapterType(JCAAdapterType jcaAdapterType) {
		this.jcaAdapterType = jcaAdapterType;
	}

	public final void setJCAbase(JCABase jcaBase) {
		if (!nodes.isEmpty()) {
			nodes.get(0).setJcaBase(jcaBase);
		}

	}

}
