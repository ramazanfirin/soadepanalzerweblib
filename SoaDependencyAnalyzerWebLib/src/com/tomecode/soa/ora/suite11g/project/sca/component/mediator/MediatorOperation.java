package com.tomecode.soa.ora.suite11g.project.sca.component.mediator;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.Mediator.MediatorActions;
import com.tomecode.soa.wsdl.Wsdl;
import com.tomecode.soa.wsdl.WsdlOperation;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * {@link Wsdl} {@link WsdlOperation} in {@link Mediator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Mediator Operation")
public final class MediatorOperation extends MediatorActions {

	/**
	 * WSDL operation name in mediator
	 */
	//@PropertyViewData(title = "Name: ")
	private final String name;

	//@PropertyViewData(title = "Delivery Policy: ")
	private String deliveryPolicy;

	//@PropertyViewData(title = "Priority: ")
	private String priority;

	//@PropertyViewData(title = "Validate Schema: ")
	private String validateSchema;

	//@PropertyViewData(title = "Callout to: ")
	private String javaFunction;

	//@PropertyViewData(title = "Callout to: ")
	private String PLSQLFunction;

	/**
	 * 
	 * @param name
	 *            WSDL operation name
	 */
	public MediatorOperation(String name) {
		this.name = name;
	}

	/**
	 * @return the deliveryPolicy
	 */
	public final String getDeliveryPolicy() {
		return deliveryPolicy;
	}

	/**
	 * @param deliveryPolicy
	 *            the deliveryPolicy to set
	 */
	public final void setDeliveryPolicy(String deliveryPolicy) {
		this.deliveryPolicy = deliveryPolicy;
	}

	/**
	 * @return the priority
	 */
	public final String getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public final void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the validateSchema
	 */
	public final String getValidateSchema() {
		return validateSchema;
	}

	/**
	 * @param validateSchema
	 *            the validateSchema to set
	 */
	public final void setValidateSchema(String validateSchema) {
		this.validateSchema = validateSchema;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	@Override
	// TODO: pridat obrazok pre mediator operation podla typu operacia. t.j. ci
	// tam response atd
	public final Image getImage(boolean small) {
		return null;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder("Type:			").append(getType());
		if (name != null && name.trim().length() != 0) {
			sb.append("\nName:			").append(name);
		}
		if (deliveryPolicy != null) {
			sb.append("\nDelivery Policy:		").append(deliveryPolicy);
		}
		sb.append("\nPriority:			").append(priority);
		return sb.toString();
	}

	public final void setJavaFunction(String javaFunction) {
		this.javaFunction = javaFunction;
	}

	/**
	 * @return the javaFunction
	 */
	public final String getJavaFunction() {
		return javaFunction;
	}

	/**
	 * @return the pLSQLFunction
	 */
	public final String getPLSQLFunction() {
		return PLSQLFunction;
	}

	/**
	 * @param pLSQLFunction
	 *            the pLSQLFunction to set
	 */
	public final void setPLSQLFunction(String pLSQLFunction) {
		PLSQLFunction = pLSQLFunction;
	}

	@Override
	public final String getType() {
		return "Mediator Operation - WSDL Operation in Mediator";
	}

}