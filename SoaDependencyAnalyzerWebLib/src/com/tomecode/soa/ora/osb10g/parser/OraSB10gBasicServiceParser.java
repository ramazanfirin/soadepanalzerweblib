package com.tomecode.soa.ora.osb10g.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.osb10g.services.Binding;
import com.tomecode.soa.ora.osb10g.services.Binding.BindingType;
import com.tomecode.soa.ora.osb10g.services.Binding.WsdlServiceBinding;
import com.tomecode.soa.ora.osb10g.services.Binding.WsldServiceBindingType;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.services.config.AsbDbTransportParams;
import com.tomecode.soa.ora.osb10g.services.config.DbTransportParameter;
import com.tomecode.soa.ora.osb10g.services.config.DbTransportParameter.Directions;
import com.tomecode.soa.ora.osb10g.services.config.DbTransportParameter.SqlType;
import com.tomecode.soa.ora.osb10g.services.config.EndpointASBDB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointASBWLI;
import com.tomecode.soa.ora.osb10g.services.config.EndpointBPEL10g;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointDsp;
import com.tomecode.soa.ora.osb10g.services.config.EndpointEJB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFTP;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFile;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFlow;
import com.tomecode.soa.ora.osb10g.services.config.EndpointHttp;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJPD;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJca;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJms;
import com.tomecode.soa.ora.osb10g.services.config.EndpointLocal;
import com.tomecode.soa.ora.osb10g.services.config.EndpointMQ;
import com.tomecode.soa.ora.osb10g.services.config.EndpointMail;
import com.tomecode.soa.ora.osb10g.services.config.EndpointSB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointSFTP;
import com.tomecode.soa.ora.osb10g.services.config.EndpointUNKNOWN;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificJms;
import com.tomecode.soa.ora.osb10g.services.protocols.dsp.DSPApplication;
import com.tomecode.soa.ora.osb10g.services.protocols.dsp.DSPServer;
import com.tomecode.soa.ora.osb10g.services.protocols.ftp.FtpServer;
import com.tomecode.soa.ora.osb10g.services.protocols.ftp.SFtpServer;
import com.tomecode.soa.ora.osb10g.services.protocols.jdp.JdpProvider;
import com.tomecode.soa.ora.osb10g.services.protocols.jdp.JdpUri;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSConnectionFactory;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSServer;
import com.tomecode.soa.ora.osb10g.services.protocols.sb.SBJndi;
import com.tomecode.soa.ora.osb10g.services.protocols.sb.ServiceName;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.protocols.Node;
import com.tomecode.soa.protocols.ejb.EjbHome;
import com.tomecode.soa.protocols.ejb.EjbMethod;
import com.tomecode.soa.protocols.ejb.EjbObject;
import com.tomecode.soa.protocols.ejb.EjbProvider;
import com.tomecode.soa.protocols.file.File;
import com.tomecode.soa.protocols.http.HttpServer;
import com.tomecode.soa.protocols.jca.GeneralJCA;
import com.tomecode.soa.protocols.jca.JCAAdapterType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Basic parser for Proxy and Business service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class OraSB10gBasicServiceParser extends AbstractParser {

	/**
	 * parsing end point configuration
	 * 
	 * @param eXml
	 * @return
	 */
	protected final EndpointConfig<?> parseEndpointConfig(Element eXml, OSBService service) {
		EndpointConfig<?> endpoint = null;

		Element eEndpointConfig = eXml.element("endpointConfig");

		if (eEndpointConfig != null) {
			String providerId = eEndpointConfig.elementText("provider-id");
			if (ProviderProtocol.LOCAL.name().equalsIgnoreCase(providerId)) {
				endpoint = new EndpointLocal();
			} else if (ProviderProtocol.EJB.name().equalsIgnoreCase(providerId)) {
				endpoint = parseEJBtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.HTTP.name().equalsIgnoreCase(providerId)) {
				endpoint = parseHttpTransport(eEndpointConfig, service);
			} else if (ProviderProtocol.JCA.name().equalsIgnoreCase(providerId)) {
				endpoint = parseJCAtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.SB.name().equalsIgnoreCase(providerId)) {
				endpoint = parseSBtransport(eEndpointConfig);
			} else if (ProviderProtocol.FILE.name().equalsIgnoreCase(providerId)) {
				endpoint = parseFILEtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.DSP.name().equalsIgnoreCase(providerId)) {
				endpoint = parseDSPtransport(eEndpointConfig);
			} else if (ProviderProtocol.FTP.name().equalsIgnoreCase(providerId)) {
				endpoint = parseFTPtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.JPD.name().equalsIgnoreCase(providerId)) {
				endpoint = parseJPDtransport(eEndpointConfig);
			} else if (ProviderProtocol.JMS.name().equalsIgnoreCase(providerId)) {
				endpoint = parseJMStransport(eEndpointConfig, service);
			} else if (ProviderProtocol.EMAIL.name().equalsIgnoreCase(providerId)) {
				endpoint = parseMAILtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.SFTP.name().equalsIgnoreCase(providerId)) {
				endpoint = parseSFTPtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.FLOW.name().equalsIgnoreCase(providerId)) {
				endpoint = parseFlowtransport(eEndpointConfig, service);
				// TODO: MQ
			} else if (ProviderProtocol.MQ.name().equalsIgnoreCase(providerId)) {
				endpoint = parseMQtransport(eEndpointConfig);
				// TODO: BPEL
			} else if (ProviderProtocol.BPEL_10G.name().equalsIgnoreCase(providerId)) {
				endpoint = parseBPEL10Gtransport(eEndpointConfig);
				// TODO: dokncit parsovanie enpointu pre WS
				// } else if
				// (ProviderProtocol.WS.name().equalsIgnoreCase(providerId)) {
				// endpoint = parseWStransport(eEndpointConfig);
			} else if (EndpointConfig.ProviderProtocol.ASB_DB.name().equalsIgnoreCase(providerId)) {
				endpoint = parseAsbDbEndpoint(eEndpointConfig, service);
		}  else if (EndpointConfig.ProviderProtocol.ASB_BPM.name().equalsIgnoreCase(providerId)) {
				endpoint = parseAsbWLIEndpoint(eEndpointConfig, service);
			} else {
				endpoint = parseUnknownEndpoint(eEndpointConfig, service);
			}

			endpoint.setInbound(Boolean.parseBoolean(eEndpointConfig.elementText("inbound")));
		} else {
			endpoint = parseUnknownEndpoint(eEndpointConfig, service);
		}
		return endpoint;
	}
	
	private final EndpointASBDB parseAsbDbEndpoint(Element eEndpointConfig, OSBService parentService) {
		EndpointASBDB asbDb = new EndpointASBDB();
		asbDb.setProviderId(eEndpointConfig.elementText("provider-id"));
		feedAsbDbTransportParams(asbDb, eEndpointConfig.elements("provider-specific"));
		asbDb.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return asbDb;
	}

	
	private final EndpointASBWLI parseAsbWLIEndpoint(Element eEndpointConfig, OSBService parentService) {
		EndpointASBWLI asbDb = new EndpointASBWLI();
		asbDb.setProviderId(eEndpointConfig.elementText("provider-id"));
		//feedAsbDbTransportParams(asbDb, eEndpointConfig.elements("provider-specific"));
		
		asbDb.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return asbDb;
	}
	
	/**
	 * parse unknown end point
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointUNKNOWN parseUnknownEndpoint(Element eEndpointConfig, OSBService parentService) {
		EndpointUNKNOWN unknown = new EndpointUNKNOWN(parentService);
		unknown.setProviderId(eEndpointConfig.elementText("provider-id"));
		unknown.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return unknown;
	}

	/**
	 * parsing end point with BPEL-10g transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private final EndpointBPEL10g parseBPEL10Gtransport(Element eEndpointConfig) {
		EndpointBPEL10g bpel10g = new EndpointBPEL10g();
		bpel10g.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return bpel10g;
	}

	/**
	 * parsing end point with SFTP transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointSFTP parseSFTPtransport(Element eEndpointConfig, OSBService service) {
		EndpointSFTP sftp = new EndpointSFTP();
		sftp.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		for (SFtpServer sFtpServer : sftp.getSFtpServers()) {
			sFtpServer.setParentService(service);
		}
		return sftp;
	}

	/**
	 * parsing end point with Flow transport
	 * 
	 * @param eEndpointConfig
	 * @param service
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private final EndpointFlow parseFlowtransport(Element eEndpointConfig, OSBService service) {
		EndpointFlow flow = new EndpointFlow();
		flow.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return flow;
	}

	/**
	 * parsing end point with MQ transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private final EndpointMQ parseMQtransport(Element eEndpointConfig) {
		EndpointMQ mq = new EndpointMQ();
		mq.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return mq;
	}

	/**
	 * parsing end point with EMAIL transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointMail parseMAILtransport(Element eEndpointConfig, OSBService parentService) {
		EndpointMail mail = new EndpointMail();
		mail.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		mail.setParentService(parentService);
		return mail;
	}

	/**
	 * parsing end point with JMS transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointJms parseJMStransport(Element eEndpointConfig, OSBService parentService) {
		EndpointJms jms = new EndpointJms();
		jms.setProviderSpecificJms(parseProviderSpecificJms(eEndpointConfig.element("provider-specific")));
		jms.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		jms.setParentService(parentService);
		return jms;
	}

	/**
	 * parsing end point with JPD transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointJPD parseJPDtransport(Element eEndpointConfig) {
		EndpointJPD jpd = new EndpointJPD();
		jpd.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return jpd;
	}

	/**
	 * parsing end point with FTP transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointFTP parseFTPtransport(Element eEndpointConfig, OSBService service) {
		EndpointFTP ftp = new EndpointFTP();
		ftp.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		for (Node<FtpServer> ftpServer : ftp.getNodes()) {
			ftpServer.getObj().setParentService(service);
		}
		return ftp;
	}

	/**
	 * parsing end point with DSP transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointDsp parseDSPtransport(Element eEndpointConfig) {
		EndpointDsp dsp = new EndpointDsp();
		dsp.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));

		Element eProviderSpecific = eEndpointConfig.element("provider-specific");
		if (eProviderSpecific != null) {
			dsp.setRequestResponse(Boolean.parseBoolean(eProviderSpecific.elementTextTrim("request-response")));
		}
		return dsp;
	}

	/**
	 * parsing end point with WS transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointFile parseFILEtransport(Element eEndpointConfig, OSBService service) {
		EndpointFile file = new EndpointFile();
		file.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		for (File f : file.getNodes()) {
			f.setParentService(service);
		}
		return file;
	}

	/**
	 * parsing end point with WS transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	// @SuppressWarnings("unchecked")
	// private final EndpointWS parseWStransport(Element eEndpointConfig) {
	// EndpointWS ws = new EndpointWS();
	// ws.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
	// return ws;
	// }

	/**
	 * parsing end point with SB transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointSB parseSBtransport(Element eEndpointConfig) {
		EndpointSB sb = new EndpointSB();
		sb.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return sb;
	}

	/**
	 * parsing end point with JCA transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointJca parseJCAtransport(Element eEndpointConfig, OSBService service) {
		EndpointJca endpointJca = new EndpointJca();
		endpointJca.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));

		Element eProviderSpecific = eEndpointConfig.element("provider-specific");
		if (eProviderSpecific != null) {
			Element jcaFile = eProviderSpecific.element("jca-file");
			if (jcaFile != null) {
				endpointJca.setRefJcaFile(jcaFile.attributeValue("ref"));
			}
			JCAAdapterType jcaAdapterType = JCAAdapterType.parse(eProviderSpecific.elementTextTrim("adapter-type"));
			if (jcaAdapterType != null) {
				String name = eProviderSpecific.elementTextTrim("adapter-name");
				endpointJca.setOperation(parseJcaOperation(eProviderSpecific));
				endpointJca.setName(name);
				endpointJca.setJcaAdapterType(jcaAdapterType);
				endpointJca.addJcaAdapter(new GeneralJCA(name));
			}
		}

		return endpointJca;
	}

	private final String parseJcaOperation(Element e) {
		for (Object o : e.elements()) {
			if (o instanceof Element) {
				Element element = (Element) o;
				if (element.getName().endsWith("-properties")) {
					Element eOperationProperties = element.element("operation-properties");
					if (eOperationProperties != null) {
						return eOperationProperties.elementTextTrim("operation-name");
					}
				}
			}
		}
		return null;
	}

	/**
	 * parsing end point with HTTP transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointHttp parseHttpTransport(Element eEndpointConfig, OSBService service) {
		EndpointHttp http = new EndpointHttp();
		http.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		Element eProviderSpecific = eEndpointConfig.element("provider-specific");
		if (eProviderSpecific != null) {
			Element outboundProperties = eProviderSpecific.element("outbound-properties");
			if (outboundProperties != null) {
				http.setRequestMethod(outboundProperties.elementTextTrim("request-method"));
			}
		}

		for (Node<HttpServer> httpServer : http.getNodes()) {
			httpServer.getObj().setParentService(service);
		}
		return http;
	}

	/**
	 * parsing end point with EJB transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointEJB parseEJBtransport(Element eEndpointConfig, OSBService service) {
		EndpointEJB ejb = new EndpointEJB();
		ejb.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));

		if (!ejb.getUris().isEmpty()) {
			EjbHome ejbHome = parseEjbHome(eEndpointConfig.element("provider-specific"));
			if (ejbHome != null) {
				String[] ejbUris = ejb.getUris().get(0).toString().split(":");
				EjbProvider ejbProvider = new EjbProvider(ejbUris[1]);
				ejbProvider.addEjbHome(ejbHome);
				ejb.addEjbProvider(ejbProvider);
				ejbProvider.setParentService(service);
			}
		}

		return ejb;
	}

	/**
	 * parse {@link ProviderSpecificEJB}
	 * 
	 * @param element
	 * @return
	 */
	private final EjbHome parseEjbHome(Element element) {
		if (element != null) {
			Element eService = element.element("service");
			if (eService != null) {
				Element eEjbHome = eService.element("ejbHome");
				if (eEjbHome != null) {
					EjbHome ejbHome = new EjbHome(eEjbHome.attributeValue("classname"));
					Element eEjbObject = eService.element("ejbObject");
					if (eEjbObject != null) {
						EjbObject ejbObject = new EjbObject(eEjbObject.attributeValue("classname"));
						ejbHome.addEjbObject(ejbObject);

						Element eMethod = eEjbObject.element("method");
						if (eMethod != null) {
							ejbObject.addEjbMethod(new EjbMethod(eMethod.attributeValue("name"), eMethod.attributeValue("signature")));
						}
					}

					return ejbHome;
				}
			}
		}

		return null;
	}

	/**
	 * parse all URI element
	 * 
	 * @param eURI
	 * @return
	 */
	private final List<String> parseTrasportURI(List<?> eURI) {
		List<String> list = new ArrayList<String>();
		if (eURI != null) {
			for (Object o : eURI) {
				Element e = (Element) o;
				String value = e.elementTextTrim("value");
				if (value != null && value.trim().length() != 0) {
					list.add(value);
				}
			}
		}
		return list;
	}

	/**
	 * parse provider specific JMS
	 * 
	 * @param list
	 * @return
	 */
	private final ProviderSpecificJms parseProviderSpecificJms(Element element) {
		ProviderSpecificJms providerSpecificJms = new ProviderSpecificJms();
		if (element != null) {
			providerSpecificJms.setQueue(Boolean.parseBoolean(element.elementTextTrim("is-queue")));
			Element outboundProperties = element.element("outbound-properties");
			if (outboundProperties != null) {
				boolean isResponseRequired = Boolean.parseBoolean(outboundProperties.elementTextTrim("response-required"));
				providerSpecificJms.setResponseRequired(isResponseRequired);
				if (!isResponseRequired) {
					return providerSpecificJms;
				}
				providerSpecificJms.setResponseURI(outboundProperties.elementText("response-URI"));
			}
		}
		return providerSpecificJms;
	}

	/**
	 * parse list of URIs to {@link DSPServer}
	 * 
	 * @param uris
	 * @param nodes
	 */
	public final static void parseDSPServerUris(List<String> uris, List<DSPServer> dspServers) {
		for (String uri : uris) {
			parseDSPServerUris(uri, dspServers);
		}
	}

	/**
	 * parse uri for {@link DSPServer}
	 * 
	 * @param uri
	 * @param dspServers
	 */
	public static final void parseDSPServerUris(String uri, List<DSPServer> dspServers) {
		int index = uri.indexOf("t3://");
		if (index != -1) {
			uri = uri.substring(index + "t3://".length());
			index = uri.indexOf("/");

			String dspResource = uri.substring(index + 1);
			DSPApplication dspApplication = null;
			int moduleIndex = dspResource.indexOf("/");
			if (moduleIndex == -1) {
				dspApplication = new DSPApplication(dspResource);
			} else {
				dspApplication = new DSPApplication(dspResource.substring(0, moduleIndex));
			}

			String serversURI = uri.substring(0, index);
			String servers[] = serversURI.split(",");
			for (String server : servers) {
				DSPServer dspServer = new DSPServer(server);

				DSPServer exists = existsDSPServer(dspServers, dspServer);
				if (exists == null) {
					dspServers.add(dspServer);
				} else {
					dspServer = exists;
				}
				dspServer.addDSPApplication(dspApplication);
			}
		}
	}

	private static final DSPServer existsDSPServer(List<DSPServer> dspServers, DSPServer dspServer) {
		for (DSPServer server : dspServers) {
			if (server.getName().equals(dspServer.getName())) {
				if (server.getPort() == dspServer.getPort()) {
					return server;
				}
			}
		}
		return null;
	}

	private static final JdpProvider existsJDPProvider(List<JdpProvider> jdpProviders, JdpProvider provider) {
		for (JdpProvider jdpProvider : jdpProviders) {
			if (jdpProvider.getName().equals(provider.getName())) {
				return jdpProvider;
			}
		}
		return null;
	}

	/**
	 * parse list of URIs to {@link JMSServer}
	 * 
	 * @param jmsUris
	 */
	public static final void parseJMSServerUris(List<String> jmsUris, List<JMSServer> jmsServers, boolean isQueue) {
		for (String jmsUri : jmsUris) {
			parseJMSServerUris(jmsUri, jmsServers, isQueue);
		}
	}

	/**
	 * parse list of URIs to {@link JMSServer}
	 * 
	 * @param jmsUri
	 * @param jmsServers
	 */
	public static final void parseJMSServerUris(String jmsUri, List<JMSServer> jmsServers, boolean isQueue) {
		int index = jmsUri.indexOf("jms://");
		if (index != -1) {
			jmsUri = jmsUri.substring(index + "jms://".length());
			index = jmsUri.indexOf("/");
			if (index != -1) {

				String jmsResource = jmsUri.substring(index + 1);
				JMSConnectionFactory connectionFactory = null;
				if (jmsResource.contains("BinaryFileTopic111")) {
					String.class.toString();
				}
				int moduleIndex = jmsResource.indexOf("/");
				if (moduleIndex == -1) {
					connectionFactory = new JMSConnectionFactory(jmsResource);
				} else {
					connectionFactory = new JMSConnectionFactory(jmsResource.substring(0, moduleIndex));
					connectionFactory.addJmsQueue(jmsResource.substring(moduleIndex + 1), isQueue);
				}

				String serversURI = jmsUri.substring(0, index);
				String servers[] = serversURI.split(",");
				for (String server : servers) {
					JMSServer jmsServer = new JMSServer(server);

					JMSServer exists = existsJMSServer(jmsServers, jmsServer);
					if (exists == null) {
						jmsServers.add(jmsServer);
					} else {
						jmsServer = exists;
					}
					jmsServer.addJMSConnectionFactory(connectionFactory);
				}

			}

		}
	}

	/**
	 * check, whether exists {@link JMSServer}
	 * 
	 * @param jmsServers
	 * @param jmsServer
	 * @return
	 */
	private final static JMSServer existsJMSServer(List<JMSServer> jmsServers, JMSServer jmsServer) {
		for (JMSServer server : jmsServers) {
			if (server.getName().equals(jmsServer.getName()) && server.getPort() == jmsServer.getPort()) {
				return server;
			}
		}
		return null;
	}

	/**
	 * parse element binding
	 * 
	 * @param eBinding
	 * @param proxy
	 */
	protected final Binding parseBinding(Element eBinding) {
		if (eBinding != null) {
			BindingType bindingType = BindingType.parse(eBinding.attributeValue("type"));
			Binding binding = new Binding(bindingType, Boolean.parseBoolean(eBinding.attributeValue("isSoap12")));

			Element eRequest = eBinding.element("request");
			if (eRequest != null) {
				binding.setRequest(eRequest.attributeValue("type"));
			}
			Element eResponse = eBinding.element("response");
			if (eResponse != null) {
				binding.setResponse(eResponse.attributeValue("type"));
			}

			if (bindingType == BindingType.SOAP_SERVICES) {
				Element eWsdl = eBinding.element("wsdl");
				if (eWsdl != null) {
					binding.setWsdlRef(eWsdl.attributeValue("ref"));
				}

				Element eWsdlBinding = eBinding.element("binding");
				if (eWsdlBinding != null) {
					String name = eWsdlBinding.elementTextTrim("name");
					String namespace = eWsdlBinding.elementTextTrim("namespace");
					WsdlServiceBinding wsdlServiceBinding = new WsdlServiceBinding(WsldServiceBindingType.BINDING, name, namespace);
					binding.setWsdlServiceBinding(wsdlServiceBinding);
				}

				Element eWsdlPort = eBinding.element("port");
				if (eWsdlPort != null) {
					String name = eWsdlPort.elementTextTrim("name");
					String namespace = eWsdlPort.elementTextTrim("namespace");
					WsdlServiceBinding wsdlServiceBinding = new WsdlServiceBinding(WsldServiceBindingType.PORT, name, namespace);
					binding.setWsdlServiceBinding(wsdlServiceBinding);
				}

				Element eSelector = eBinding.element("selector");
				if (eSelector != null) {
					Element eMapping = eSelector.element("mapping");
					if (eMapping != null) {
						binding.setWsdlOperation(eMapping.attributeValue("operation"));
					}
				}
			}

			return binding;
		}

		return null;
	}

	/**
	 * parse {@link HttpServer} w
	 * 
	 * @param uris
	 * @param httpServers
	 */
	public final static void parseHttpServersUris(List<String> uris, List<HttpServer> httpServers) {
		for (String uri : uris) {
			boolean isHttps = false;
			try {
				URL url = new URL(uri);
				if ("https".equals(url.getProtocol())) {
					isHttps = true;
				}
				HttpServer exists = findExistsHttpServer(isHttps, url.getHost(), url.getPort(), httpServers);
				if (exists == null) {
					exists = new HttpServer(isHttps, url.getHost(), url.getPort());
					httpServers.add(exists);
				}
				if (url.getPath().trim().length() != 0) {
					exists.addUrl(url.getPath());
				}
			} catch (MalformedURLException e) {
				HttpServer exists = findExistsHttpServer(false, "", -1, httpServers);
				if (exists == null) {
					exists = new HttpServer(false, "", -1);
					httpServers.add(exists);
				}
				if (uri.trim().length() != 0) {
					exists.addUrl(uri);
				}
			}
		}
	}

	/**
	 * check, whether exists {@link HttpServer}
	 * 
	 * @param isHttps
	 * @param url
	 * @param httpServers
	 * @return
	 */
	private final static HttpServer findExistsHttpServer(boolean isHttps, String host, int port, List<HttpServer> httpServers) {
		for (HttpServer httpServer : httpServers) {
			if (host.equals(httpServer.getServer()) && isHttps == httpServer.isHttps() && port == httpServer.getPort()) {
				return httpServer.getObj();
			}
		}
		return null;
	}

	/**
	 * parse FTP urls
	 * 
	 * @param uris
	 * @param ftpServers
	 */
	public final static void parseFtpServerUris(List<String> uris, List<FtpServer> ftpServers) {
		for (String uri : uris) {
			try {
				URL url = new URL(uri);
				FtpServer ftpServer = findExistsFtpServer(url.getHost(), url.getPort(), ftpServers);
				if (ftpServer == null) {
					ftpServer = new FtpServer(url.getHost(), url.getPort());
					ftpServers.add(ftpServer);
				}
				if (url.getPath().trim().length() != 0) {
					ftpServer.addUrl(url.getPath());
				}
			} catch (MalformedURLException e) {
			}
		}
	}

	/**
	 * check, whether exists {@link FtpServer}
	 * 
	 * @param host
	 * @param port
	 * @param ftpServers
	 * @return
	 */
	private final static FtpServer findExistsFtpServer(String host, int port, List<FtpServer> ftpServers) {
		for (FtpServer ftpServer : ftpServers) {
			if (host.equals(ftpServer.getServer()) && port == ftpServer.getPort()) {
				return ftpServer;
			}
		}
		return null;
	}

	private final static SFtpServer findExistsSFtpServer(String host, int port, List<SFtpServer> ftpServers) {
		for (SFtpServer ftpServer : ftpServers) {
			if (host.equals(ftpServer.getServer()) && port == ftpServer.getPort()) {
				return ftpServer;
			}
		}
		return null;
	}

	/**
	 * parse {@link SFtpServer}
	 * 
	 * @param uris
	 * @param ftpServers
	 */
	public static void parseSFtpServerUris(List<String> uris, List<SFtpServer> ftpServers) {
		for (String uri : uris) {
			try {
				URL url = new URL(uri);
				SFtpServer ftpServer = findExistsSFtpServer(url.getHost(), url.getPort(), ftpServers);
				if (ftpServer == null) {
					ftpServer = new SFtpServer(url.getHost(), url.getPort());
					ftpServers.add(ftpServer);
				}
				if (url.getPath().trim().length() != 0) {
					ftpServer.addUrl(url.getPath());
				}
			} catch (MalformedURLException e) {
			}
		}
	}

	/**
	 * parse {@link JdpProvider} and {@link JdpUri}
	 * 
	 * @param uris
	 * @param nodes
	 */
	public final static void parseJDDProviderUris(List<String> uris, List<JdpProvider> nodes) {
		for (String uri : uris) {
			parseJDPProviderUris(uri, nodes);
		}
	}

	private final static void parseJDPProviderUris(String uri, List<JdpProvider> jdpProviders) {
		int index = uri.indexOf("jpd:");
		if (index != -1) {
			uri = uri.substring(index + "jpd:".length());
			index = uri.indexOf(":/");

			String jdpUriResource = uri.substring(index + 2);
			JdpUri jdpUri = null;
			int moduleIndex = jdpUriResource.indexOf(":/");
			if (moduleIndex == -1) {
				jdpUri = new JdpUri(jdpUriResource);
			} else {
				jdpUri = new JdpUri(jdpUriResource.substring(0, moduleIndex));
			}

			String serversURI = uri.substring(0, index);
			String servers[] = serversURI.split(",");
			for (String server : servers) {
				JdpProvider jdpProvider = new JdpProvider(server);

				JdpProvider exists = existsJDPProvider(jdpProviders, jdpProvider);
				if (exists == null) {
					jdpProviders.add(jdpProvider);
				} else {
					jdpProvider = exists;
				}
				jdpProvider.addJDPUri(jdpUri);
			}
		}
	}

	public final static void parseSBJndiUris(List<String> uris, List<SBJndi> sbJndis) {
		for (String uri : uris) {
			parseSBJndiUri(uri, sbJndis);
		}
	}

	private final static void parseSBJndiUri(String uri, List<SBJndi> sbJndis) {
		int index = uri.indexOf("sb://");
		if (index != -1) {
			uri = uri.substring(index + "sb://".length());
			index = uri.indexOf("/");

			String sbUriResource = uri.substring(index + 1);
			ServiceName serviceName = null;
			int moduleIndex = sbUriResource.indexOf("/");
			if (moduleIndex == -1) {
				serviceName = new ServiceName(sbUriResource);
			} else {
				serviceName = new ServiceName(sbUriResource.substring(0, moduleIndex));
			}

			String serversURI = uri.substring(0, index);
			String servers[] = serversURI.split(",");
			for (String server : servers) {
				SBJndi sbJndi = new SBJndi(server);

				SBJndi exists = existsSBJndi(sbJndis, sbJndi);
				if (exists == null) {
					sbJndis.add(sbJndi);
				} else {
					sbJndi = exists;
				}
				sbJndi.addServiceName(serviceName);
			}
		}
	}

	private static SBJndi existsSBJndi(List<SBJndi> sbJndis, SBJndi sbJndi) {
		for (SBJndi jndi : sbJndis) {
			if (jndi.getName().equals(jndi.getName())) {
				return jndi;
			}
		}
		return null;
	}
	
	
	private void feedAsbDbTransportParams(EndpointASBDB asbDb, List<?> eURI) {
		List<String> list = new ArrayList<String>();
		if (eURI != null) {
			for (Object o : eURI) {
				Element e = (Element) o;
				Iterator iter = e.elementIterator();
				while (iter.hasNext()) {
					Element element = (Element) iter.next();
					AsbDbTransportParams trParams = asbDb.getAsbDbTransportParams();
					trParams.setOperationType(element.elementText("operationType"));
					trParams.setWorkManager(element.elementText("workManager"));
					trParams.setSqlStatement(element.elementText("statement"));
					try {
						trParams.setTimeoutInSeconds(Integer.parseInt(element.elementText("timeoutInSeconds")));
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}

					Iterator iterator = element.elementIterator("Parameters");

					if (iterator.hasNext()) {
						Element listParams = (Element) iterator.next();
						Iterator paramsIter = listParams.elementIterator();
						while (paramsIter.hasNext()) {
							DbTransportParameter dbParam = new DbTransportParameter();
							Element titleElement = (Element) paramsIter.next();
							System.out.println(titleElement.getName() + " " + titleElement.attributeValue("parameter")+" "+ titleElement.attributeValue("responseElement"));
							if(titleElement.attributeValue("parameter")==null)
								dbParam.setParameterName(titleElement.attributeValue("responseElement"));
							else
								dbParam.setParameterName(titleElement.attributeValue("parameter"));
							
							try{
								if(titleElement.attributeValue("jdbcIndex")!=null)							
									dbParam.setJdbcIndex(Integer.parseInt(titleElement.attributeValue("jdbcIndex")));
							}catch (Exception e2) {}
							dbParam.setMandatory(Boolean.parseBoolean(titleElement.attributeValue("isMandatory")));
							if ("IN".equalsIgnoreCase(titleElement.attributeValue("isMandatory")))
								dbParam.setDirection(Directions.IN);
							else
								dbParam.setDirection(Directions.OUT);

							if ("NUMERIC".equalsIgnoreCase(titleElement.attributeValue("sqlType")))
								dbParam.setSqlType(SqlType.NUMERIC);
							else
								dbParam.setSqlType(SqlType.VARCHAR);

							trParams.getParameterList().add(dbParam);
						}
					}

				}
			}
		}
	}


}
