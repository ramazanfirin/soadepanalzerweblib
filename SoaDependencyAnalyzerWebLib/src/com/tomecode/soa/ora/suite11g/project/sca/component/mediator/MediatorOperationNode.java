package com.tomecode.soa.ora.suite11g.project.sca.component.mediator;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.wsdl.WsdlOperation;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * wrapper for {@link Mediator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class MediatorOperationNode implements ImageFace {

	/**
	 * {@link Mediator}
	 */
	//@PropertyViewDataInsideObject
	private final Mediator mediator;
	/**
	 * concrete mediator operation
	 */
	private final MediatorOperation mediatorOperation;
	/**
	 * wsdl operation
	 */
	private final WsdlOperation wsdlOperation;

	/**
	 * Constructor
	 * 
	 * @param wsdlOperation
	 * @param mediatorOperation
	 */
	public MediatorOperationNode(WsdlOperation wsdlOperation, MediatorOperation mediatorOperation) {
		this.wsdlOperation = wsdlOperation;
		this.mediatorOperation = mediatorOperation;
		this.mediator = mediatorOperation.getMediator();
	}

	/**
	 * @return the mediatorOperation
	 */
	public final MediatorOperation getMediatorOperation() {
		return mediatorOperation;
	}

	public final List<MediatorOperationCase> getMediatorOperationCases() {
		return mediatorOperation.getOperationCases();
	}

	/**
	 * @return the wsdlOperation
	 */
	public final WsdlOperation getWsdlOperation() {
		return wsdlOperation;
	}

	/**
	 * @return the mediator
	 */
	public final Mediator getMediator() {
		return mediator;
	}

	@Override
	public final Image getImage(boolean small) {
		return mediator.getImage(small);
	}

	@Override
	public final String getToolTip() {
		return mediator.getToolTip();
	}

	public final String toString() {
		return mediator.toString();
	}
}
