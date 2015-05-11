package com.tomecode.soa.ora.suite11g.project.sca.component.mediator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite11g.project.sca.CompositeService;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.Mediator.MediatorActions;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * switch case for {@link MediatorOperation}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Mediator Operation Case")
public final class MediatorOperationCase implements ImageFace {
	/**
	 * case name
	 */
	//@PropertyViewData(title = "Name: ")
	private final String name;
	/**
	 * execution type
	 */
	//@PropertyViewData(title = "Execution Type: ")
	private String executionType;

	private MediatorActions mediatorActions;

	//@PropertyViewData(title = "Default Rule: ")
	private String defaultRule;

	//@PropertyViewData(title = "Reference: ")
	private String reference;

	//@PropertyViewData(title = "Language: ")
	private String language;

	//@PropertyViewData(title = "Condition Expression: ")
	private String conditionExpression;

	/**
	 * target WSDL operation name
	 */
	private String operation;
	/**
	 * target SERVICES
	 */
	private final List<CompositeService> referenceObjects;

	private String event;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            case name
	 */
	public MediatorOperationCase(String name) {
		this.referenceObjects = new ArrayList<CompositeService>();
		this.name = name;
	}

	/**
	 * @return the event
	 */
	public final String getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public final void setEvent(String event) {
		this.event = event;
		if (event != null) {
			int index = event.indexOf(":");
			if (index != -1) {
				this.event = event.substring(index + 1, event.length());
			}
		}
	}

	/**
	 * @return the reference
	 */
	public final String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set
	 */
	public final void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the operation
	 */
	public final String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public final void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the refernceObject
	 */
	public final List<CompositeService> getReferenceObjects() {
		return referenceObjects;
	}

	/**
	 * @param referenceObject
	 *            the refernceObject to set
	 */
	public final void addReferenceObject(CompositeService referenceObject) {
		if (referenceObject != null) {
			this.referenceObjects.add(referenceObject);
		}
	}

	/**
	 * @return the mediatorActions
	 */
	public final MediatorActions getMediatorActions() {
		return mediatorActions;
	}

	/**
	 * @param mediatorActions
	 *            the mediatorActions to set
	 */
	public final void setMediatorActions(MediatorActions mediatorActions) {
		this.mediatorActions = mediatorActions;
	}

	/**
	 * @return the executionType
	 */
	public final String getExecutionType() {
		return executionType;
	}

	/**
	 * @param executionType
	 *            the executionType to set
	 */
	public final void setExecutionType(String executionType) {
		this.executionType = executionType;
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

	/**
	 * @return the defaultRule
	 */
	public final String getDefaultRule() {
		return defaultRule;
	}

	/**
	 * @param defaultRule
	 *            the defaultRule to set
	 */
	public final void setDefaultRule(String defaultRule) {
		this.defaultRule = defaultRule;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_11G_OPERATION_CASE;
	}

	/**
	 * @return the language
	 */
	public final String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public final void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the conditionExpression
	 */
	public final String getConditionExpression() {
		return conditionExpression;
	}

	/**
	 * @param conditionExpression
	 *            the conditionExpression to set
	 */
	public final void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder("Type:			").append(getType());
		if (name != null && name.trim().length() != 0) {
			sb.append("\nName:			").append(name);
		}

		if (defaultRule != null && defaultRule.trim().length() != 0) {
			sb.append("\nDefault Rule:		").append("true");
		} else {
			sb.append("\nDefault Rule:		").append("false");
		}
		if (executionType != null && executionType.trim().length() != 0) {
			sb.append("\nExecution Type:	").append(executionType);
		}

		return sb.toString();
	}

	public final String getType() {
		return "Operation Case";
	}
}