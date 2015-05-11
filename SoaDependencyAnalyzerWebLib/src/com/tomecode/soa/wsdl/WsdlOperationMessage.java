package com.tomecode.soa.wsdl;

import java.io.Serializable;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * message in {@link WsdlOperationMessage}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class WsdlOperationMessage implements Serializable {
	/**
	 * message type
	 */
	private final WsdlOperationMessageType operationMessageType;
	/**
	 * parent operation
	 */
	private WsdlOperation wsdlOperation;
	/**
	 * name
	 */
	private String name;
	/**
	 * message reference
	 */
	private final String message;
	/**
	 * documentation
	 */
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param type
	 *            {@link WsdlOperationMessageType}
	 * @param message
	 *            message reference
	 */
	public WsdlOperationMessage(WsdlOperationMessageType type, String message) {
		this.operationMessageType = type;
		if (message != null) {
			int index = message.indexOf(":");
			if (index != -1) {
				message = message.substring(index + 1, message.length());
			}
		}
		this.message = message;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name == null ? "" : name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the documentation
	 */
	public final String getDocumentation() {
		return documentation;
	}

	/**
	 * @param documentation
	 *            the documentation to set
	 */
	public final void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	/**
	 * @return the operationMessageType
	 */
	public final WsdlOperationMessageType getOperationMessageType() {
		return operationMessageType;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		return message != null ? message : "";
	}

	/**
	 * @return the wsdlOperation
	 */
	public final WsdlOperation getWsdlOperation() {
		return wsdlOperation;
	}

	/**
	 * @param wsdlOperation
	 *            the wsdlOperation to set
	 */
	public final void setWsdlOperation(WsdlOperation wsdlOperation) {
		this.wsdlOperation = wsdlOperation;
	}

	public final String toString() {
		return "Type: " + operationMessageType.toString() + "\nMessage: " + message + (name != null ? "\nName: " + name : "") + (documentation != null ? "\n:Documentation: " + documentation : "");
	}

	/**
	 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
	 * 
	 * {@link WsdlOperationMessage} type
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public enum WsdlOperationMessageType {
		INPUT, OUTPUT, FAULT;
	}
}
