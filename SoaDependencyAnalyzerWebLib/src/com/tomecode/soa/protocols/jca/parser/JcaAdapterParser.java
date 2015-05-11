package com.tomecode.soa.protocols.jca.parser;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.dom4j.Element;

import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.protocols.jca.JCAAdapterType;
import com.tomecode.soa.services.jca.JCABase;
import com.tomecode.soa.services.jca.JCAEndpointActivation;
import com.tomecode.soa.services.jca.JCAEndpointInteraction;
import com.tomecode.soa.services.jca.adapter.JCADatabase;
import com.tomecode.soa.services.jca.adapter.JCAFile;
import com.tomecode.soa.services.jca.adapter.JCAFtp;
import com.tomecode.soa.services.jca.adapter.JCAJms;
import com.tomecode.soa.services.jca.adapter.JCASocket;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * JCA adapter parser
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
public final class JcaAdapterParser extends AbstractParser {

	/**
	 * parse {@link JCASocket} adapter
	 * 
	 * @param file
	 * @param element
	 * @return
	 */
	public final JCASocket parseSocketAdapter(File file, Element element) {
		JCASocket jcaSocket = new JCASocket(element.attributeValue("name"), file, element.attributeValue("wsdlLocation"));
		Element eConnectionFactory = element.element("connection-factory");
		if (eConnectionFactory != null) {
			jcaSocket.setLocation(eConnectionFactory.attributeValue("location"));
			jcaSocket.setAdapterRef(eConnectionFactory.attributeValue("adapterRef"));
		}
		parseEndpointActivation(element, jcaSocket);
		parseEndpointInteraction(element, jcaSocket);
		return jcaSocket;
	}

	/**
	 * parse {@link JCADatabase} adapter
	 * 
	 * @param file
	 * @param element
	 * @return
	 */
	public final JCADatabase parseDatabaseAdapter(File file, Element element) {
		JCADatabase jcaDatabase = new JCADatabase(element.attributeValue("name"), file, element.attributeValue("wsdlLocation"));
		Element eConnectionFactory = element.element("connection-factory");
		if (eConnectionFactory != null) {
			jcaDatabase.setLocation(eConnectionFactory.attributeValue("location"));
			jcaDatabase.setUIConnectionName(eConnectionFactory.attributeValue("UIConnectionName"));
			jcaDatabase.setAdapterRef(eConnectionFactory.attributeValue("adapterRef"));
		}
		parseEndpointActivation(element, jcaDatabase);
		parseEndpointInteraction(element, jcaDatabase);
		return jcaDatabase;
	}

	/**
	 * parse {@link JCAFile} adapter
	 * 
	 * @param file
	 * @param element
	 * @return
	 */
	public final JCAFile parseFileAdapter(File file, Element element) {
		JCAFile jcaFile = new JCAFile(element.attributeValue("name"), file, element.attributeValue("wsdlLocation"));
		Element eConnectionFactory = element.element("connection-factory");
		if (eConnectionFactory != null) {
			jcaFile.setLocation(eConnectionFactory.attributeValue("location"));
			jcaFile.setUIincludeWildcard(eConnectionFactory.attributeValue("UIincludeWildcard"));
			jcaFile.setUIexcludeWildcard(eConnectionFactory.attributeValue("UIexcludeWildcard"));
			jcaFile.setAdapterRef(eConnectionFactory.attributeValue("adapterRef"));
		}
		parseEndpointActivation(element, jcaFile);
		parseEndpointInteraction(element, jcaFile);
		return jcaFile;
	}

	/**
	 * parse {@link JCAFtp} adapter
	 * 
	 * @param file
	 * @param element
	 * @return
	 */
	public final JCAFtp parseFtpAdapter(File file, Element element) {
		JCAFtp jcaFtp = new JCAFtp(element.attributeValue("name"), file, element.attributeValue("wsdlLocation"));
		Element eConnectionFactory = element.element("connection-factory");
		if (eConnectionFactory != null) {
			jcaFtp.setLocation(eConnectionFactory.attributeValue("location"));
			jcaFtp.setUIincludeWildcard(eConnectionFactory.attributeValue("UIincludeWildcard"));
			jcaFtp.setUIexcludeWildcard(eConnectionFactory.attributeValue("UIexcludeWildcard"));
			jcaFtp.setAdapterRef(eConnectionFactory.attributeValue("adapterRef"));
		}
		parseEndpointActivation(element, jcaFtp);
		parseEndpointInteraction(element, jcaFtp);
		return jcaFtp;
	}

	/**
	 * parse {@link JCAJms} adapter
	 * 
	 * @param file
	 * @param element
	 * @return
	 */
	public final JCAJms parseJmsAdapter(File file, Element element) {
		JCAJms jcaJms = new JCAJms(element.attributeValue("name"), file, element.attributeValue("wsdlLocation"));

		Element eConnectionFactory = element.element("connection-factory");
		if (eConnectionFactory != null) {
			jcaJms.setLocation(eConnectionFactory.attributeValue("location"));
			jcaJms.setUiConnectionName(eConnectionFactory.attributeValue("UIConnectionName"));
			jcaJms.setUiJmsProvider(eConnectionFactory.attributeValue("UIJmsProvider"));
			jcaJms.setAdapterRef(eConnectionFactory.attributeValue("adapterRef"));
		}
		parseEndpointActivation(element, jcaJms);
		parseEndpointInteraction(element, jcaJms);

		return jcaJms;
	}

	private final void parseEndpointActivation(Element element, JCABase jcaBase) {

		List<?> list = element.elements("endpoint-activation");
		if (list != null) {
			for (Object o : list) {
				if (o instanceof Element) {
					Element eEdnpointActivation = ((Element) o);
					JCAEndpointActivation endpointActivation = new JCAEndpointActivation();
					endpointActivation.setPortType(eEdnpointActivation.attributeValue("portType"));
					endpointActivation.setOperation(eEdnpointActivation.attributeValue("operation"));

					if (eEdnpointActivation != null) {
						Element eActivationSpec = eEdnpointActivation.element("activation-spec");
						if (eActivationSpec != null) {
							endpointActivation.setClassName(eActivationSpec.attributeValue("className"));
							endpointActivation.setProperties(parseProperties(eActivationSpec.elements("property")));
						}
					}

					jcaBase.addEndpointActivation(endpointActivation);

				}
			}
		}
	}

	private final void parseEndpointInteraction(Element element, JCABase jcaBase) {

		List<?> list = element.elements("endpoint-interaction");
		if (list != null) {
			for (Object o : list) {
				if (o instanceof Element) {

					Element eEdnpointActivation = (Element) o;
					JCAEndpointInteraction endpointInteraction = new JCAEndpointInteraction();
					endpointInteraction.setPortType(eEdnpointActivation.attributeValue("portType"));
					endpointInteraction.setOperation(eEdnpointActivation.attributeValue("operation"));
					if (eEdnpointActivation != null) {
						Element eActivationSpec = eEdnpointActivation.element("interaction-spec");
						if (eActivationSpec != null) {
							endpointInteraction.setClassName(eActivationSpec.attributeValue("className"));
							endpointInteraction.setProperties(parseProperties(eActivationSpec.elements("property")));
						}
					}

					jcaBase.addEndpointInteraction(endpointInteraction);
				}
			}
		}
	}

	/**
	 * parse properties for adapter
	 * 
	 * @param list
	 * @return
	 */
	private final Properties parseProperties(List<?> list) {
		Properties properties = new Properties();
		for (Object o : list) {
			Element element = (Element) o;
			properties.put(element.attributeValue("name"), element.attributeValue("value"));
		}
		return properties;
	}

	private final Element parseAdapter(File file) {
		try {
			Element element = parseXml(file);
			if (element != null && "adapter-config".equals(element.getName())) {
				return element;
			}

		} catch (ServiceParserException e) {
			// TODO: doplnit
			e.printStackTrace();
		}
		return null;
	}

	// public final void parseJCAAdapter(File file, EndpointJca endpointJca) {
	// if (endpointJca.getJcaAdapterType() == JCAAdapterType.DATABASE) {
	//
	// // parseDatabaseAdapter(endpointJca.getOperation());
	//
	// }
	// }

	public final JCABase parseJca(File file) {
		Element eRoot = parseAdapter(file);
		if (eRoot != null) {
			JCAAdapterType type = JCAAdapterType.parseAdapter(eRoot.attributeValue("adapter"));
			if (JCAAdapterType.DATABASE == type) {
				return parseDatabaseAdapter(file, eRoot);
			} else if (JCAAdapterType.FILE == type) {
				return parseFileAdapter(file, eRoot);
			} else if (JCAAdapterType.FTP == type) {
				return parseFtpAdapter(file, eRoot);
			} else if (JCAAdapterType.JMS == type) {
				return parseJmsAdapter(file, eRoot);
			} else if (JCAAdapterType.SOCKET == type) {
				return parseSocketAdapter(file, eRoot);
			}
		}
		return null;
	}
}
