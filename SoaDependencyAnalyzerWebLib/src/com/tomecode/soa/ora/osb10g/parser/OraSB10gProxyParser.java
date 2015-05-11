package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.ora.osb10g.activity.Alert;
import com.tomecode.soa.ora.osb10g.activity.Assign;
import com.tomecode.soa.ora.osb10g.activity.Branch;
import com.tomecode.soa.ora.osb10g.activity.BranchNodeCondition;
import com.tomecode.soa.ora.osb10g.activity.BranchNodeOperation;
import com.tomecode.soa.ora.osb10g.activity.DefaultBranch;
import com.tomecode.soa.ora.osb10g.activity.Delete;
import com.tomecode.soa.ora.osb10g.activity.DynamicRoute;
import com.tomecode.soa.ora.osb10g.activity.ElseIf;
import com.tomecode.soa.ora.osb10g.activity.Error;
import com.tomecode.soa.ora.osb10g.activity.Foreach;
import com.tomecode.soa.ora.osb10g.activity.If;
import com.tomecode.soa.ora.osb10g.activity.IfDefault;
import com.tomecode.soa.ora.osb10g.activity.IfThenElse;
import com.tomecode.soa.ora.osb10g.activity.Insert;
import com.tomecode.soa.ora.osb10g.activity.JavaCallout;
import com.tomecode.soa.ora.osb10g.activity.Log;
import com.tomecode.soa.ora.osb10g.activity.MflTransform;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.activity.PipelineError;
import com.tomecode.soa.ora.osb10g.activity.PipelinePairNode;
import com.tomecode.soa.ora.osb10g.activity.PipelineRequest;
import com.tomecode.soa.ora.osb10g.activity.PipelineResponse;
import com.tomecode.soa.ora.osb10g.activity.Rename;
import com.tomecode.soa.ora.osb10g.activity.Replace;
import com.tomecode.soa.ora.osb10g.activity.Reply;
import com.tomecode.soa.ora.osb10g.activity.Report;
import com.tomecode.soa.ora.osb10g.activity.Route;
import com.tomecode.soa.ora.osb10g.activity.RouteNode;
import com.tomecode.soa.ora.osb10g.activity.RouteOutboundTransform;
import com.tomecode.soa.ora.osb10g.activity.RouteResponseTransform;
import com.tomecode.soa.ora.osb10g.activity.Router;
import com.tomecode.soa.ora.osb10g.activity.RoutingOptions;
import com.tomecode.soa.ora.osb10g.activity.RoutingTable;
import com.tomecode.soa.ora.osb10g.activity.RoutingTableCase;
import com.tomecode.soa.ora.osb10g.activity.RoutingTableDefaultCase;
import com.tomecode.soa.ora.osb10g.activity.Skip;
import com.tomecode.soa.ora.osb10g.activity.Stage;
import com.tomecode.soa.ora.osb10g.activity.TransportHeaders;
import com.tomecode.soa.ora.osb10g.activity.TransportHeaders.Header;
import com.tomecode.soa.ora.osb10g.activity.Validate;
import com.tomecode.soa.ora.osb10g.activity.WsCallout;
import com.tomecode.soa.ora.osb10g.activity.WsCalloutRequestTransform;
import com.tomecode.soa.ora.osb10g.activity.WsCalloutResponseTransform;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle SB 10g PROXY service parser
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class OraSB10gProxyParser extends OraSB10gBasicServiceParser {

	/**
	 * parse Proxy Service in OSB 10g
	 * 
	 * @param file
	 * @return
	 * @throws ServiceParserException
	 */
	public final Proxy parseProxy(File file) throws ServiceParserException {
		return parseProxy(file, parseXml(file));
	}

	/**
	 * parse Proxy Service in OSB 10g
	 * 
	 * @param file
	 * @param inputStream
	 * @return
	 * @throws ServiceParserException
	 */
	public final Proxy parseProxy(File file, InputStream inputStream) throws ServiceParserException {
		return parseProxy(file, parseXml(inputStream));
	}

	public final Proxy parseProxy(File file, Element eXml) throws ServiceParserException {
		if (eXml != null) {
			Element eCoreEntry = eXml.element("coreEntry");
			if (eCoreEntry != null) {
				Proxy proxy = new Proxy(file);
				proxy.setEndpointConfig(parseEndpointConfig(eXml, proxy));
				parseCoreEntrty(eCoreEntry, proxy);
				parseFlow(eXml.element("router"), proxy);
				return proxy;
			}
		}
		return null;
	}

	/**
	 * parse element coreEntry
	 * 
	 * @param eCoreEntry
	 * @param proxy
	 */
	private final void parseCoreEntrty(Element eCoreEntry, Proxy proxy) {
		proxy.setIsEnabled(Boolean.parseBoolean(eCoreEntry.attributeValue("isEnabled")));
		proxy.setIsAutoPublish(Boolean.parseBoolean(eCoreEntry.attributeValue("isAutoPublish")));
		proxy.setDescription(eCoreEntry.elementText("description"));
		proxy.setBinding(parseBinding(eCoreEntry.element("binding")));
	}

	/**
	 * parse flow
	 * 
	 * @param eRouter
	 * @param proxy
	 */
	private final void parseFlow(Element eRouter, Proxy proxy) {
		Router router = new Router(eRouter.attributeValue("errorHandler"));
		proxy.getStructure().addActivity(router);
		// parseActivities(eRouter.elements(), router);

		try {
			Element eFlow = eRouter.element("flow");
			if (eFlow != null) {
				parseFlowStructure(proxy.getServiceDependencies(), eFlow.elements(), router);
				List<OsbActivity> pipelines = parsePipelines(proxy.getServiceDependencies(), eRouter.elements("pipeline"));
				linkingPipeLines(pipelines, router);
				linkingErrorHandlers(pipelines, router);
			}
		} catch (Exception e) {
			// TODO error handler
			e.printStackTrace();
		}

	}

	private final void linkingErrorHandlers(List<OsbActivity> pipelines, Router router) {
		List<OsbActivity> activities = new ArrayList<OsbActivity>();
		findAllActivityWithError(activities, router);

		for (OsbActivity activity : activities) {
			PipelineError error = findPipelineError(activity.getErrorHandlerName(), pipelines);
			if (error != null) {
				if (activity.getParent() == null) {
					activity.setErrorHandler(error);
				} else {
					((OsbActivity) activity.getParent()).setErrorHandler(error);
				}
			}
		}
	}

	private final PipelineError findPipelineError(String name, List<OsbActivity> pipelines) {
		for (OsbActivity osbActivity : pipelines) {
			if (osbActivity instanceof PipelineError) {
				if (((PipelineError) osbActivity).getName().equals(name)) {
					return (PipelineError) osbActivity;
				}
			}
		}
		return null;
	}

	private final void findAllActivityWithError(List<OsbActivity> activitiesWithError, OsbActivity parentActivity) {
		if (parentActivity.getErrorHandlerName() != null) {
			if (!activitiesWithError.contains(activitiesWithError)) {
				activitiesWithError.add(parentActivity);
			}
		}
		for (Activity osbActivity : parentActivity.getActivities()) {
			findAllActivityWithError(activitiesWithError, (OsbActivity) osbActivity);
		}
	}

	/**
	 * linking pipeLine-s with flow
	 * 
	 * @param pipelines
	 * @param router
	 */
	private final void linkingPipeLines(List<OsbActivity> pipelines, Router router) {
		for (OsbActivity osbActivity : pipelines) {
			if (osbActivity instanceof PipelineRequest) {
				PipelineRequest pipelineRequest = (PipelineRequest) osbActivity;
				replacePipelineRequest(pipelineRequest, router.getActivities());
			} else if (osbActivity instanceof PipelineResponse) {
				PipelineResponse pipelineResponse = (PipelineResponse) osbActivity;
				replacePipelineResponse(pipelineResponse, router.getActivities());
			}
		}
	}

	/**
	 * 
	 * @param pipelineResponse
	 * @param root
	 */
	private final void replacePipelineResponse(PipelineResponse pipelineResponse, List<Activity> activities) {
		PipelineResponse response = findPipelineResponse(pipelineResponse.getName(), activities);
		if (response != null) {
			response.merge(pipelineResponse);
		}
	}

	/**
	 * 
	 * @param pipelineRequest
	 * @param root
	 */
	private final void replacePipelineRequest(PipelineRequest pipelineRequest, List<Activity> activities) {
		PipelineRequest request = findPipelineRequest(pipelineRequest.getName(), activities);
		if (request != null) {
			request.merge(pipelineRequest);
		}
	}

	/**
	 * find {@link PipelineResponse} in list of {@link OsbActivity}
	 * 
	 * @param name
	 * @param activities
	 * @return
	 */
	private final PipelineResponse findPipelineResponse(String name, List<Activity> activities) {
		try {
			for (Activity osbActivity : activities) {
				if (osbActivity instanceof PipelineResponse && osbActivity.getActivities().isEmpty()) {
					PipelineResponse response = (PipelineResponse) osbActivity;
					if (response.getName().equals(name)) {
						return response;
					}
				} else {
					PipelineResponse response = findPipelineResponse(name, osbActivity.getActivities());
					if (response != null) {
						return response;
					}
				}
			}
		} catch (Exception e) {
			// TODO error handler
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * find {@link PipelineRequest} in list of {@link OsbActivity}
	 * 
	 * @param name
	 * @param activities
	 * @return
	 */
	private final PipelineRequest findPipelineRequest(String name, List<Activity> activities) {
		for (Activity osbActivity : activities) {
			if (osbActivity instanceof PipelineRequest && osbActivity.getActivities().isEmpty()) {
				PipelineRequest request = (PipelineRequest) osbActivity;
				if (request.getName().equals(name)) {
					return request;
				}
			} else {
				PipelineRequest request = findPipelineRequest(name, osbActivity.getActivities());
				if (request != null) {
					return request;
				}
			}
		}
		return null;
	}

	/**
	 * parse element: flow
	 * 
	 * @param serviceDepependnecies
	 *            contains all the dependencies for parsing PROXY service
	 * @param eFlow
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseFlowContent(ServiceDependencies serviceDependencies, Element eFlow, OsbActivity root) {
		if (eFlow != null) {
			parseFlowStructure(serviceDependencies, eFlow.elements(), root);
		}
	}

	/**
	 * 
	 * parse request or response pipelines
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param ePipelines
	 *            current element in XML
	 * @return list of parsed {@link OsbActivity}
	 */
	private final List<OsbActivity> parsePipelines(ServiceDependencies serviceDependencies, List<?> ePipelines) {
		List<OsbActivity> osbActivities = new ArrayList<OsbActivity>();
		if (ePipelines != null) {
			for (Object o : ePipelines) {
				Element element = (Element) o;
				if ("request".equals(element.attributeValue("type"))) {
					PipelineRequest pipelineRequest = new PipelineRequest(element.attributeValue("name"), element.attributeValue("errorHandler"));
					parseActivities(serviceDependencies, element.elements(), pipelineRequest);
					osbActivities.add(pipelineRequest);
				} else if ("response".equals(element.attributeValue("type"))) {
					PipelineResponse pipelineResponse = new PipelineResponse(element.attributeValue("name"), element.attributeValue("errorHandler"));
					parseActivities(serviceDependencies, element.elements(), pipelineResponse);
					osbActivities.add(pipelineResponse);
				} else if ("error".equals(element.attributeValue("type"))) {
					PipelineError pipelineError = new PipelineError(element.attributeValue("name"));
					parseActivities(serviceDependencies, element.elements(), pipelineError);
					osbActivities.add(pipelineError);
				}
			}
		}
		return osbActivities;
	}

	/**
	 * parse all types
	 * 
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param elements
	 *            list of {@link Element}
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseActivities(ServiceDependencies serviceDependencies, List<?> elements, OsbActivity root) {
		for (Object o : elements) {
			Element e = (Element) o;
			if ("stage".equals(e.getName())) {
				Stage stage = new Stage(e.attributeValue("name"), e.attributeValue("errorHandler"), e.elementTextTrim("comment"));
				root.addActivity(stage);
				Element eActions = e.element("actions");
				if (eActions != null) {
					parseActivities(serviceDependencies, eActions.elements(), stage);
				}
			} else if ("actions".equals(e.getName())) {
				parseActivities(serviceDependencies, e.elements(), root);
			} else if ("ifThenElse".equals(e.getName())) {
				IfThenElse ifThenElse = new IfThenElse(e.elementTextTrim("comment"));
				root.addActivity(ifThenElse);
				parseActivities(serviceDependencies, e.elements(), ifThenElse);
			} else if ("case".equals(e.getName())) {
				if (root instanceof IfThenElse) {
					IfThenElse ifThenElse = (IfThenElse) root;
					if (ifThenElse.hasIf()) {
						ElseIf elseIf = new ElseIf(getXqueryText(e, "condition"));
						root.getParent().addActivity(elseIf);
						parseActivities(serviceDependencies, e.elements(), elseIf);
					} else {
						If iff = new If(getXqueryText(e, "condition"));
						ifThenElse.addActivity(iff);
						parseActivities(serviceDependencies, e.elements(), iff);
					}
				} else {
					parseActivities(serviceDependencies, e.elements(), root);
				}
			} else if ("delete".equals(e.getName())) {
				root.addActivity(new Delete(e.attributeValue("varName"), e.elementText("comment"), getXqueryText(e, "location")));
			} else if ("default".equals(e.getName())) {
				if (root instanceof IfThenElse) {
					IfDefault ifDefault = new IfDefault();
					root.addActivity(ifDefault);
					parseActivities(serviceDependencies, e.elements(), ifDefault);
				}
			} else if ("Error".equals(e.getName())) {
				root.addActivity(new Error(e.elementText("comment"), e.elementTextTrim("errCode"), e.elementTextTrim("message")));
			} else if ("reply".equals(e.getName())) {
				root.addActivity(new Reply());
			} else if ("skip".equals(e.getName())) {
				root.addActivity(new Skip(e.elementText("comment")));
			} else if ("assign".equals(e.getName())) {
				root.addActivity(new Assign(e.attributeValue("varName"), e.elementText("comment"), getXqueryText(e, "expr")));
			} else if ("insert".equals(e.getName())) {
				root.addActivity(new Insert(e.attributeValue("varName"), e.elementText("where"), getXPath(e, "location"), getXqueryText(e, "expr"), e.elementText("comment")));
			} else if ("javaCallout".equals(e.getName())) {
				String archive = null;
				Element eArchive = e.element("archive");
				if (eArchive != null) {
					archive = eArchive.attributeValue("ref");
				}
				JavaCallout javaCallout = new JavaCallout(e.elementText("comment"), archive, e.elementText("className"), e.elementText("method"));
				root.addActivity(javaCallout);
				List<?> l = e.elements("expr");
				if (l != null) {
					for (Object ol : l) {
						javaCallout.addExpressions(((Element) ol).elementText("xqueryText"));
					}
				}
			} else if ("mflTransform".equals(e.getName())) {
				root.addActivity(new MflTransform(e.elementText("comment"), e.elementText("type"), getXqueryText(e, "expr")));
			} else if ("rename".equals(e.getName())) {
				root.addActivity(new Rename(e.elementText("varName"), e.elementText("comment"), getXPath(e, "location"), e.elementText("namespace"), e.elementText("localname")));
			} else if ("replace".equals(e.getName())) {
				root.addActivity(new Replace(e.elementText("varName"), e.elementText("comment"), getXPath(e, "location"), getXqueryText(e, "expr"), Boolean.parseBoolean(e
						.attributeValue("contents-only"))));
			} else if ("validate".equals(e.getName())) {
				root.addActivity(new Validate(e.elementText("varName"), e.elementText("comment"), getXPath(e, "location"), e.elementText("resultVarName")));
			} else if ("alert".equals(e.getName())) {
				root.addActivity(new Alert(e.elementText("severity"), e.elementText("description"), getXqueryText(e, "payload")));
			} else if ("report".equals(e.getName())) {
				String key = null, varName = null, xquery = null;
				Element eLabel = e.element("labels");
				if (eLabel != null) {
					key = eLabel.elementText("key");
					varName = eLabel.elementText("varName");
					xquery = getXqueryText(eLabel, "value");
				}
				root.addActivity(new Report(e.elementText("comment"), key, varName, xquery));
			} else if ("log".equals(e.getName())) {
				root.addActivity(new Log(e.elementTextTrim("logLevel"), e.elementText("comment"), getXqueryText(e, "expr"), e.elementText("message")));
			} else if ("foreach".equals(e.getName())) {
				Foreach foreach = new Foreach(e.elementText("comment"), e.elementText("variable"), getXPath(e, "expression"), e.elementText("value-variable"), e.elementText("index-variable"),
						e.elementText("total-variable"));
				root.addActivity(foreach);
				Element eActions = e.element("actions");
				if (eActions != null) {
					parseActivities(serviceDependencies, eActions.elements(), foreach);
				}
			} else if ("dynamic-route".equals(e.getName())) {
				DynamicRoute dynamicRoute = new DynamicRoute(e.elementText("comment"), getXqueryText(e, "xqueryText"));
				root.addActivity(dynamicRoute);
				parseService(serviceDependencies, e, root);
				parseOutboundTransform(serviceDependencies, e, dynamicRoute);
			} else if ("route".equals(e.getName())) {
				parseRoute(serviceDependencies, e, root);
			} else if ("routingTable".equals(e.getName())) {
				parseRoutingTable(serviceDependencies, e.getParent(), root);
			} else if ("transport-headers".equals(e.getName())) {
				TransportHeaders transportHeaders = new TransportHeaders(Boolean.parseBoolean(e.attributeValue("copy-all")), e.elementText("comment"), e.elementText("header-set"));
				List<?> headers = e.elements("header");
				if (headers != null) {
					for (Object oo : headers) {
						Element element = (Element) oo;
						transportHeaders.addHeader(new Header(element.attributeValue("name"), element.attributeValue("value"), getXqueryText(element, "header")));
					}
				}

				root.addActivity(transportHeaders);
			} else if ("wsCallout".equals(e.getName())) {
				parseWsCallout(serviceDependencies, e, root);
			} else if ("routing-options".equals(e.getName())) {
				RoutingOptions routingOptions = new RoutingOptions();
				root.addActivity(routingOptions);
			}
		}
	}

	private final String getXqueryText(Element e, String eName) {
		Element ePayload = e.getName().equals(eName) ? e : e.element(eName);
		if (ePayload != null) {
			return ePayload.elementText("xqueryText");
		}
		return null;
	}

	private final String getXPath(Element e, String eName) {
		Element ePayload = e.getName().equals(eName) ? e : e.element(eName);
		if (ePayload != null) {
			return ePayload.elementText("xpathText");
		}
		return null;
	}

	/**
	 * 
	 * element: wsCallout
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param eWsCallout
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseWsCallout(ServiceDependencies serviceDependencies, Element eWsCallout, OsbActivity root) {
		boolean configuredSoapBody = false;
		String requestBody = null, requestHeader = null, responseBody = null, responseHeader = null;
		Element eRequest = eWsCallout.element("request");
		if (eRequest != null) {
			configuredSoapBody = Boolean.parseBoolean(eRequest.attributeValue("wrapped"));
			requestBody = eRequest.elementText("body");
			requestHeader = eRequest.elementText("header");
			if (requestBody == null) {
				requestBody = eRequest.elementText("payload");
			}
		}
		Element eResponse = eWsCallout.element("response");
		if (eResponse != null) {
			responseBody = eResponse.elementText("body");
			responseHeader = eResponse.elementText("header");
			if (responseBody == null) {
				responseBody = eResponse.elementText("payload");
			}
		}

		WsCallout wsCallout = new WsCallout(eWsCallout.elementText("comment"), eWsCallout.elementText("operation"), requestBody, requestHeader, responseBody, responseHeader);
		wsCallout.setConfiguredSoapBody(configuredSoapBody);
		root.addActivity(wsCallout);
		parseService(serviceDependencies, eWsCallout, wsCallout);

		Element e = eWsCallout.element("requestTransform");
		if (e != null) {
			WsCalloutRequestTransform requestTransform = new WsCalloutRequestTransform();
			wsCallout.addActivity(requestTransform);
			parseActivities(serviceDependencies, e.elements(), requestTransform);
		}

		e = eWsCallout.element("responseTransform");
		if (e != null) {
			WsCalloutResponseTransform responseTransform = new WsCalloutResponseTransform();
			wsCallout.addActivity(responseTransform);
			parseActivities(serviceDependencies, e.elements(), responseTransform);
		}
	}

	/**
	 * 
	 * parse content of element: router
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param elements
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseFlowStructure(ServiceDependencies serviceDependencies, List<?> elements, OsbActivity root) {
		for (Object o : elements) {
			Element e = (Element) o;

			if ("pipeline-node".equals(e.getName())) {
				parsePipeLineNodeFlow(e, root);
			} else if ("branch-node".equals(e.getName())) {
				parseBranchNodeFlow(serviceDependencies, e, root);
			} else if ("route-node".equals(e.getName())) {
				parseRouteNode(serviceDependencies, e, root);
			}
		}
	}

	/**
	 * 
	 * element: route-node
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param eRouteNode
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseRouteNode(ServiceDependencies serviceDependencies, Element eRouteNode, OsbActivity root) {
		RouteNode routeNode = new RouteNode(eRouteNode.attributeValue("name"), eRouteNode.elementText("comment"), eRouteNode.attributeValue("errorHandler"));
		root.addActivity(routeNode);
		Element eActions = eRouteNode.element("actions");
		if (eActions != null) {
			parseRoutingTable(serviceDependencies, eActions, routeNode);
			// parseRoute(serviceDependencies, eActions.element("route"),
			// routeNode);
			parseActivities(serviceDependencies, eActions.elements(), routeNode);
		}
	}

	/**
	 * 
	 * parse element: routingTable
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param e
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseRoutingTable(ServiceDependencies serviceDependencies, Element e, OsbActivity root) {
		Element eRoutingTable = e.element("routingTable");
		if (eRoutingTable != null) {

			RoutingTable routingTable = new RoutingTable(e.elementText("comment"), getXqueryText(e, "expression"));
			root.addActivity(routingTable);

			List<?> elements = eRoutingTable.elements();
			if (elements != null) {
				for (Object o : elements) {
					Element element = (Element) o;
					if ("case".equals(element.getName())) {
						RoutingTableCase routingTableCase = new RoutingTableCase(e.elementText("operator"), e.elementText("value"));
						routingTable.addActivity(routingTableCase);
						parseRoute(serviceDependencies, element.element("route"), routingTableCase);
					} else if ("defaultCase".equals(element.getName())) {
						RoutingTableDefaultCase routingTableDefaultCase = new RoutingTableDefaultCase();
						routingTable.addActivity(routingTableDefaultCase);
						parseRoute(serviceDependencies, element.element("route"), routingTableDefaultCase);
					}
				}
			}
		}
	}

	/**
	 * parse element: route
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param element
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseRoute(ServiceDependencies serviceDependencies, Element element, OsbActivity root) {
		Route route = new Route(element.elementText("comment"), element.elementText("operation"));
		root.addActivity(route);
		parseService(serviceDependencies, element, route);
		parseOutboundTransform(serviceDependencies, element, route);

	}

	/**
	 * parse element: service
	 * 
	 * @param serviceDependencies
	 * @param element
	 * @param root
	 */
	private final void parseService(ServiceDependencies serviceDependencies, Element element, OsbActivity root) {
		Element eService = element.element("service");
		if (eService != null) {

			ServiceDependencyType type = ServiceDependencyType.parse(eService.attributeValue("type"));
			if (type == ServiceDependencyType.UNKNOWN) {
				Element eXqueryText = eService.element("xqueryText");
				if (eXqueryText != null) {
					eXqueryText.getTextTrim();
				}

			} else {
				ServiceDependency depndency = new ServiceDependency(root, eService.attributeValue("ref"), type);
				serviceDependencies.addDependency(depndency);
			}

		}
	}

	/**
	 * parse element: outboundTransform
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param element
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseOutboundTransform(ServiceDependencies serviceDependencies, Element element, OsbActivity root) {
		Element eOutboundTransform = element.element("outboundTransform");
		if (eOutboundTransform != null) {
			RouteOutboundTransform outboundTransform = new RouteOutboundTransform();
			root.addActivity(outboundTransform);
			parseActivities(serviceDependencies, eOutboundTransform.elements(), outboundTransform);
		}

		Element eResponseTransform = element.element("responseTransform");
		if (eResponseTransform != null) {
			RouteResponseTransform responseTransform = new RouteResponseTransform();
			root.addActivity(responseTransform);
			parseActivities(serviceDependencies, eResponseTransform.elements(), responseTransform);
		}
	}

	/**
	 * parse branch-node
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param eBranchNode
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseBranchNodeFlow(ServiceDependencies serviceDependencies, Element eBranchNode, OsbActivity root) {
		if ("condition".equals(eBranchNode.attributeValue("type"))) {
			BranchNodeCondition branchNodeCondition = new BranchNodeCondition(eBranchNode.attributeValue("name"), eBranchNode.elementText("comment"), eBranchNode.attributeValue("errorHandler"));
			root.addActivity(branchNodeCondition);
			Element eBranchTable = eBranchNode.element("branch-table");
			if (eBranchTable != null) {
				branchNodeCondition.setVariable(eBranchNode.elementText("variable"));
				List<?> eBranchs = eBranchTable.elements();
				for (Object o : eBranchs) {
					Element eBranch = (Element) o;
					if ("branch".equals(eBranch.getName())) {
						Branch branch = new Branch(eBranch.attributeValue("name"), eBranch.elementText("operator"), eBranch.elementText("value"), eBranch.attributeValue("errorHandler"));
						branchNodeCondition.addActivity(branch);
						parseFlowContent(serviceDependencies, eBranch.element("flow"), branch);
					} else if ("default-branch".equals(eBranch.getName())) {
						DefaultBranch defaultBranch = new DefaultBranch();
						branchNodeCondition.addActivity(defaultBranch);
						parseFlowContent(serviceDependencies, eBranch.element("flow"), defaultBranch);
					}

				}
			}

		} else if ("operation".equals(eBranchNode.attributeValue("type"))) {
			BranchNodeOperation branchNodeOperation = new BranchNodeOperation(eBranchNode.attributeValue("name"), eBranchNode.attributeValue("errorHandler"));
			root.addActivity(branchNodeOperation);
			Element eBranchTable = eBranchNode.element("branch-table");
			if (eBranchTable != null) {
				List<?> eBranchs = eBranchTable.elements();
				for (Object o : eBranchs) {
					Element eBranch = (Element) o;
					if ("branch".equals(eBranch.getName())) {
						Branch branch = new Branch(eBranch.attributeValue("name"), eBranch.elementText("operator"), eBranch.elementText("value"), eBranch.attributeValue("errorHandler"));
						branchNodeOperation.addActivity(branch);
						parseFlowContent(serviceDependencies, eBranch.element("flow"), branch);
					} else if ("default-branch".equals(eBranch.getName())) {
						DefaultBranch defaultBranch = new DefaultBranch();
						branchNodeOperation.addActivity(defaultBranch);
						parseFlowContent(serviceDependencies, eBranch.element("flow"), defaultBranch);
					}

				}
			}
		}
	}

	/**
	 * 
	 * parse pipeline-node
	 * 
	 * @param ePipelineNode
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parsePipeLineNodeFlow(Element ePipelineNode, OsbActivity root) {
		PipelinePairNode pipelinePairNode = new PipelinePairNode(ePipelineNode.attributeValue("name"), ePipelineNode.elementText("comment"), ePipelineNode.attributeValue("errorHandler"));
		root.addActivity(pipelinePairNode);
		List<?> elements = ePipelineNode.elements();
		for (Object o : elements) {
			Element element = (Element) o;
			if ("request".equals(element.getName())) {
				pipelinePairNode.addActivity(new PipelineRequest(element.getTextTrim(), element.attributeValue("errorHandler")));
			} else if ("response".equals(element.getName())) {
				pipelinePairNode.addActivity(new PipelineResponse(element.getTextTrim(), element.attributeValue("errorHandler")));
			}
		}
	}

}
