package com.tomecode.soa.wsdl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * WSDL for BPEL project or ESB service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "WSDL")
public final class Wsdl implements Serializable {

	private static final long serialVersionUID = 2802713997788570698L;

	//@PropertyViewData(title = "Name: ")
	private final String name;

	//@PropertyViewData(title = "File: ")
	private final File file;

	private final List<WsdlPortType> portTypes;

	private final List<PartnerLinkType> partnerLinkTypes;

	private Object parent;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            WSDL file
	 * @param name
	 *            WSDL name
	 */
	public Wsdl(File file, String name) {
		this.file = file;
		this.name = name;
		this.partnerLinkTypes = new ArrayList<PartnerLinkType>();
		this.portTypes = new ArrayList<WsdlPortType>();
	}

	public final String getName() {
		return name;
	}

	public final File getFile() {
		return file;
	}

	/**
	 * @return the portTypes
	 */
	public final List<WsdlPortType> getPortTypes() {
		return portTypes;
	}

	public final void addPortType(WsdlPortType portType) {
		portType.setWsdl(this);
		this.portTypes.add(portType);
	}

	public final boolean existWsldOperation(String wsdlOperation) {
		for (WsdlPortType portType : portTypes) {
			return portType.existWsldOperation(wsdlOperation);
		}

		return false;
	}

	/**
	 * @return the partnerLinkTypes
	 */
	public final List<PartnerLinkType> getPartnerLinkTypes() {
		return partnerLinkTypes;
	}

	public final void addPartnerLinkType(PartnerLinkType type) {
		this.partnerLinkTypes.add(type);
	}

	/**
	 * find {@link WsdlPortType} by name
	 * 
	 * @param portTypeName
	 * @return
	 */
	public final WsdlPortType findPortType(String portTypeName) {
		if (portTypeName != null) {
			int index = portTypeName.indexOf(":");
			if (index == -1) {
				return findPortTypeByName(portTypeName);
			} else {
				return findPortType(portTypeName.substring(index + 1));
			}
		}
		return null;
	}

	/**
	 * find {@link WsdlPortType} by name in list of {@link WsdlPortType}
	 * 
	 * @param name
	 * @return null if not found
	 */
	private final WsdlPortType findPortTypeByName(String name) {
		for (WsdlPortType portType : portTypes) {
			if (name.equals(portType.getName())) {
				return portType;
			}
		}
		return null;
	}

	public final boolean containsPartnerLinkType(String partnerLinkTypeName) {
		for (PartnerLinkType partnerLinkType : partnerLinkTypes) {
			int index = partnerLinkTypeName.indexOf(":");
			if (index != -1) {
				if (partnerLinkTypeName.substring(index + 1).equals(partnerLinkType.getName())) {
					return true;
				}
			}

			if (partnerLinkTypeName.equals(partnerLinkType.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the parent
	 */
	public final Object getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public final void setParent(Object parent) {
		this.parent = parent;
	}

}
