package com.tomecode.soa.wsdl.parser;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.wsdl.Wsdl;
import com.tomecode.soa.wsdl.WsdlOperation;
import com.tomecode.soa.wsdl.WsdlOperationMessage;
import com.tomecode.soa.wsdl.WsdlOperationMessage.WsdlOperationMessageType;
import com.tomecode.soa.wsdl.WsdlPortType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * parser for {@link Wsdl}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class WsdlParser extends AbstractParser {

	/**
	 * Constructor
	 */
	public WsdlParser() {

	}

	/**
	 * parse WSDL file
	 * 
	 * @param file
	 * @return
	 */
	public final Wsdl parseWsdl(File file) {
		if (file.isFile() && file.exists()) {
			try {
				Element element = parseXml(file);
				Wsdl wsdl = new Wsdl(file, element.attributeValue("name"));
				parsePortTypes(element.elements("portType"), wsdl);
				return wsdl;
			} catch (ServiceParserException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * parse list of {@link WsdlPortType}
	 * 
	 * @param elements
	 * @param wsdl
	 */
	protected final void parsePortTypes(List<?> elements, Wsdl wsdl) {
		if (elements != null) {
			for (Object o : elements) {
				Element element = (Element) o;
				wsdl.addPortType(parsePortType(element));
			}

		}
	}

	/**
	 * parse element: portType in WSDL file
	 * 
	 * @param ePortType
	 * @return
	 */
	protected final WsdlPortType parsePortType(Element ePortType) {
		WsdlPortType portType = new WsdlPortType(ePortType.attributeValue("name"));

		List<?> eOperations = ePortType.elements("operation");
		for (Object o : eOperations) {
			portType.addWsdlOperations(parseOperation((Element) o));
		}
		return portType;
	}

	/**
	 * parse {@link WsdlOperation}
	 * 
	 * @param eOperation
	 * @return
	 */
	private final WsdlOperation parseOperation(Element eOperation) {
		WsdlOperation wsdlOperation = new WsdlOperation(eOperation.attributeValue("name"));

		boolean input = false, output = false;
		Element e = eOperation.element("input");
		if (e != null) {
			wsdlOperation.addMessage(parseOperationMessage(e, WsdlOperationMessageType.INPUT));
			input = true;
		}
		e = eOperation.element("output");
		if (e != null) {
			wsdlOperation.addMessage(parseOperationMessage(e, WsdlOperationMessageType.OUTPUT));
			output = true;
		}
		e = eOperation.element("fault");
		if (e != null) {
			wsdlOperation.addMessage(parseOperationMessage(e, WsdlOperationMessageType.FAULT));
		}
		wsdlOperation.setRequestResponse((input && output));
		return wsdlOperation;
	}

	/**
	 * parse message type in {@link WsdlOperation}
	 * 
	 * @param eInput
	 * @param type
	 * @return
	 */
	private final WsdlOperationMessage parseOperationMessage(Element eInput, WsdlOperationMessageType type) {
		WsdlOperationMessage message = new WsdlOperationMessage(type, eInput.attributeValue("message"));
		message.setName(eInput.attributeValue("name"));
		message.setDocumentation(eInput.elementTextTrim("documentation"));
		return message;
	}
}
