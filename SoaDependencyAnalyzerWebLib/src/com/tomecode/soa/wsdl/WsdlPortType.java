package com.tomecode.soa.wsdl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * portType in wsdl file
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "WSDL Port Type", parentMethod = "getWsdl")
public final class WsdlPortType implements Serializable, ImageFace {

	private static final long serialVersionUID = 4682224733979729749L;
	/**
	 * port type name
	 */
	//@PropertyViewData(title = "Name: ")
	private String name;

	/**
	 * wsdl operations
	 */
	private List<WsdlOperation> wsdlOperations;

	private Wsdl wsdl;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public WsdlPortType(String name) {
		this.name = name;
		this.wsdlOperations = new ArrayList<WsdlOperation>();
	}

	public final String getName() {
		return name;
	}

	public final List<WsdlOperation> getWsdlOperations() {
		return wsdlOperations;
	}

	/**
	 * add new {@link WsdlOperation}
	 * 
	 * @param wsdlOperation
	 */
	public final void addWsdlOperations(WsdlOperation wsdlOperation) {
		wsdlOperation.setPortType(this);
		this.wsdlOperations.add(wsdlOperation);
	}

	public final boolean existWsldOperation(String operationName) {
		return findWsdlOperations(operationName) != null;
	}

	public final WsdlOperation findWsdlOperations(String operationName) {
		for (WsdlOperation operation : wsdlOperations) {
			if (operation.getName().equals(operationName)) {
				return operation;
			}
		}
		return null;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_11G_WSDL_PORTTYPE;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: 		").append(getType());
		sb.append("\nName:		").append(getName());
		return sb.toString();
	}

	public final String getType() {
		return "WSDL Port Type";
	}

	/**
	 * @return the wsdl
	 */
	public final Wsdl getWsdl() {
		return wsdl;
	}

	/**
	 * @param wsdl
	 *            the wsdl to set
	 */
	public final void setWsdl(Wsdl wsdl) {
		this.wsdl = wsdl;
	}

}
