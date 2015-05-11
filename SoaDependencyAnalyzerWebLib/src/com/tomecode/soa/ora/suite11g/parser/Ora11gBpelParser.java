package com.tomecode.soa.ora.suite11g.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.PartnerLinkReference;
import com.tomecode.soa.ora.suite10g.activity.Case;
import com.tomecode.soa.ora.suite10g.activity.Catch;
import com.tomecode.soa.ora.suite10g.activity.CatchAll;
import com.tomecode.soa.ora.suite10g.activity.CompensationHandler;
import com.tomecode.soa.ora.suite10g.activity.Email;
import com.tomecode.soa.ora.suite10g.activity.FaultHandlers;
import com.tomecode.soa.ora.suite10g.activity.HumanTask;
import com.tomecode.soa.ora.suite10g.activity.PartnerLink;
import com.tomecode.soa.ora.suite10g.activity.PartnerLinks;
import com.tomecode.soa.ora.suite10g.activity.Variable;
import com.tomecode.soa.ora.suite10g.activity.Variables;
import com.tomecode.soa.ora.suite11g.activity.Assert;
import com.tomecode.soa.ora.suite11g.activity.Assign11g;
import com.tomecode.soa.ora.suite11g.activity.BindEntity;
import com.tomecode.soa.ora.suite11g.activity.Checkpoint;
import com.tomecode.soa.ora.suite11g.activity.Compensate;
import com.tomecode.soa.ora.suite11g.activity.CorrelationSet;
import com.tomecode.soa.ora.suite11g.activity.CorrelationSets;
import com.tomecode.soa.ora.suite11g.activity.CreateEntity;
import com.tomecode.soa.ora.suite11g.activity.Empty11g;
import com.tomecode.soa.ora.suite11g.activity.EventHandlers11g;
import com.tomecode.soa.ora.suite11g.activity.Flow11g;
import com.tomecode.soa.ora.suite11g.activity.FlowN11g;
import com.tomecode.soa.ora.suite11g.activity.Im;
import com.tomecode.soa.ora.suite11g.activity.Invoke11g;
import com.tomecode.soa.ora.suite11g.activity.JavaEmbedding11g;
import com.tomecode.soa.ora.suite11g.activity.Milestone;
import com.tomecode.soa.ora.suite11g.activity.OnAlarm11g;
import com.tomecode.soa.ora.suite11g.activity.OnMessage11g;
import com.tomecode.soa.ora.suite11g.activity.OnMessageEvent;
import com.tomecode.soa.ora.suite11g.activity.Otherwise11g;
import com.tomecode.soa.ora.suite11g.activity.Phase;
import com.tomecode.soa.ora.suite11g.activity.Pick11g;
import com.tomecode.soa.ora.suite11g.activity.Receive11g;
import com.tomecode.soa.ora.suite11g.activity.ReceiveSignal;
import com.tomecode.soa.ora.suite11g.activity.RemoveEntity;
import com.tomecode.soa.ora.suite11g.activity.Replay;
import com.tomecode.soa.ora.suite11g.activity.Reply11g;
import com.tomecode.soa.ora.suite11g.activity.Rule;
import com.tomecode.soa.ora.suite11g.activity.Scope11g;
import com.tomecode.soa.ora.suite11g.activity.Sequence11g;
import com.tomecode.soa.ora.suite11g.activity.Signal;
import com.tomecode.soa.ora.suite11g.activity.Sms11g;
import com.tomecode.soa.ora.suite11g.activity.Switch11g;
import com.tomecode.soa.ora.suite11g.activity.Terminate11g;
import com.tomecode.soa.ora.suite11g.activity.Throw11g;
import com.tomecode.soa.ora.suite11g.activity.Transform11g;
import com.tomecode.soa.ora.suite11g.activity.UserNotification;
import com.tomecode.soa.ora.suite11g.activity.Validate;
import com.tomecode.soa.ora.suite11g.activity.Voice11g;
import com.tomecode.soa.ora.suite11g.activity.Wait11g;
import com.tomecode.soa.ora.suite11g.activity.While11g;
import com.tomecode.soa.ora.suite11g.project.sca.component.bpel.Ora11gBpelActivityDependency;
import com.tomecode.soa.ora.suite11g.project.sca.component.bpel.Ora11gBpelProcess;
import com.tomecode.soa.ora.suite11g.project.sca.component.bpel.Ora11gBpelReference;
import com.tomecode.soa.parser.AbstractParser;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Parser for BPEL process in composite project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
final class Ora11gBpelParser extends AbstractParser {

	Ora11gBpelParser() {
	}

	/**
	 * parse {@link Ora11gBpelProcess}
	 * 
	 * @param name
	 * @param file
	 * @return
	 */
	final Ora11gBpelProcess parse(String name, File file) {
		Ora11gBpelProcess bpelProcess = new Ora11gBpelProcess(name, file);

		try {
			Element element = parseXml(file);
			if (element != null && "process".equals(element.getName())) {
				parseActivities(element.elements(), bpelProcess.getStructure());

				parseComponentTypeFile(file, bpelProcess);
			}
		} catch (Exception e) {
			// TODO error handler
			e.printStackTrace();
		}

		parseReferenciesWithActivities(bpelProcess);
		return bpelProcess;
	}

	/**
	 * 
	 * 
	 * @param bpelProcess
	 */
	private final void parseReferenciesWithActivities(Ora11gBpelProcess bpelProcess) {

		if (bpelProcess.getName().equals("BPELProcess1")) {
			toString();
		}

		for (Ora11gBpelReference reference : bpelProcess.getBpelReferences()) {
			List<Activity> activities = new ArrayList<Activity>();
			findAllActivitiesForReference(reference.getName(), activities, bpelProcess.getStructure());

			for (Activity activity : activities) {
				bpelProcess.addActitivtyDependency(new Ora11gBpelActivityDependency(activity, reference));
			}
		}
	}

	/**
	 * 
	 * 
	 * find all activities which implementing {@link PartnerLinkReference}
	 * 
	 * @param name
	 * @param activitiesWithPartnerLink
	 *            list of activities for current partnerlink
	 * @param root
	 */
	private final void findAllActivitiesForReference(String name, List<Activity> activitiesWithPartnerLink, Activity root) {
		for (Activity activity : root.getActivities()) {
			if (activity instanceof PartnerLinkReference) {
				PartnerLinkReference partnerLinkReference = (PartnerLinkReference) activity;
				if (name.equals(partnerLinkReference.getPartnerLink())) {
					if (!activitiesWithPartnerLink.contains(activity)) {
						activitiesWithPartnerLink.add(activity);
					}
				}
			}
			findAllActivitiesForReference(name, activitiesWithPartnerLink, activity);
		}
	}

	/**
	 * parse file *.componentType
	 * 
	 * @param name
	 * @param bpelProcess
	 */
	private final void parseComponentTypeFile(File file, Ora11gBpelProcess bpelProcess) {
		int index = file.getName().lastIndexOf(".bpel");
		if (index != -1) {
			String name = file.getName().substring(0, index);
			try {
				Element element = parseXml(new File(file.getParent() + File.separator + name + ".componentType"));
				if (element != null && "componentType".equals(element.getName())) {
					parseBpelComponentTypes(element, bpelProcess);
				}
			} catch (Exception e) {
				// TODO error handler
				e.printStackTrace();
			}
		}
	}

	/**
	 * parse data from componentType XML
	 * 
	 * @param element
	 * @param bpelProcess
	 */
	private final void parseBpelComponentTypes(Element element, Ora11gBpelProcess bpelProcess) {

		Element eService = element.element("service");
		if (eService != null) {
			bpelProcess.addBpelReference(parseBpelReference(eService, true));
		}

		List<?> eReferences = element.elements("reference");
		if (eReferences != null) {
			for (Object o : eReferences) {
				bpelProcess.addBpelReference(parseBpelReference((Element) o, false));
			}
		}
	}

	/**
	 * parse reference element
	 * 
	 * @param eReference
	 * @return
	 */
	private final Ora11gBpelReference parseBpelReference(Element eReference, boolean isService) {
		Ora11gBpelReference bpelReference = new Ora11gBpelReference(eReference.attributeValue("name"), eReference.attributeValue("wsdlLocation"), isService);

		Element eInterfaceWsdl = eReference.element("interface.wsdl");
		if (eInterfaceWsdl != null) {
			bpelReference.setReferenceInterface(eInterfaceWsdl.attributeValue("interface"));
			bpelReference.setReferenceCallbackInterface(eInterfaceWsdl.attributeValue("callbackInterface"));
		}
		return bpelReference;
	}

	/**
	 * recursive parse all activities in bpel process
	 * 
	 * @param elements
	 * @param rootActivity
	 */
	private final void parseActivities(List<?> elements, Activity rootActivity) {
		if (elements != null) {
			for (Object o : elements) {
					try{
						Element element = (Element) o;
						if ("partnerLinks".equals(element.getName())) {
							PartnerLinks partnerLinks = new PartnerLinks();
							rootActivity.addActivity(partnerLinks);
							parsePartnerLinks(element.elements(), partnerLinks);
						} else if ("variables".equals(element.getName())) {
							Variables variables = new Variables();
							rootActivity.addActivity(variables);
							parseVariables(element.elements(), variables);
						} else if ("sequence".equals(element.getName())) {
							Sequence11g sequence = new Sequence11g(element.attributeValue("name"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(sequence);
							parseActivities(element.elements(), sequence);
						} else if ("receive".equals(element.getName())) {
							Receive11g receive = new Receive11g(element.attributeValue("name"), element.attributeValue("partnerLink"), element.attributeValue("portType"), element.attributeValue("operation"),
									element.attributeValue("variable"), element.attributeValue("createInstance"), element.attributeValue("eventName"), element.attributeValue("conversionId"),
									element.attributeValue("headerVariable"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(receive);
							parseActivities(element.elements(), receive);
						} else if ("scope".equals(element.getName())) {
							parseScopes(element, rootActivity);
						} else if ("faultHandlers".equals(element.getName())) {
							FaultHandlers faultHandlers = new FaultHandlers();
							rootActivity.addActivity(faultHandlers);
							parseActivities(element.elements(), faultHandlers);
						} else if ("correlationSets".equals(element.getName())) {
							CorrelationSets correlationSets = new CorrelationSets();
							rootActivity.addActivity(correlationSets);
							parseActivities(element.elements(), correlationSets);
						} else if ("correlationSet".equals(element.getName())) {
							CorrelationSet correlationSet = new CorrelationSet(element.attributeValue("name"), element.attributeValue("properties"));
							rootActivity.addActivity(correlationSet);
							parseActivities(element.elements(), correlationSet);
						} else if ("catch".equals(element.getName())) {
							Catch catchh = new Catch(element.attributeValue("faultName"), element.attributeValue("faultVariable"), getDocumentation(element));
							rootActivity.addActivity(catchh);
							parseActivities(element.elements(), catchh);
						} else if ("empty".equals(element.getName())) {
							Empty11g empty = new Empty11g(element.attributeValue("name"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(empty);
							parseActivities(element.elements(), empty);
						} else if ("catchAll".equals(element.getName())) {
							CatchAll catchAll = new CatchAll(element.attributeValue("name"), getDocumentation(element));
							rootActivity.addActivity(catchAll);
							parseActivities(element.elements(), catchAll);
						} else if ("assert".equals(element.getName())) {
							Assert assertt = new Assert(element.attributeValue("name"), element.attributeValue("message"), element.attributeValue("expression"), element.attributeValue("skipCondition"));
							rootActivity.addActivity(assertt);
							parseActivities(element.elements(), assertt);
						} else if ("bindEntity".equals(element.getName())) {
							BindEntity bindEntity = new BindEntity(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("skipCondition"));
							rootActivity.addActivity(bindEntity);
							parseActivities(element.elements(), bindEntity);
						} else if ("checkpoint".equals(element.getName())) {
							Checkpoint checkpoint = new Checkpoint(element.attributeValue("name"), element.attributeValue("skipCondition"));
							rootActivity.addActivity(checkpoint);
							parseActivities(element.elements(), checkpoint);
						} else if ("compensate".equals(element.getName())) {
							Compensate compensate = new Compensate(element.attributeValue("name"), element.attributeValue("scope"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(compensate);
							parseActivities(element.elements(), compensate);
						} else if ("createEntity".equals(element.getName())) {
							CreateEntity createEntity = new CreateEntity(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("skipCondition"));
							rootActivity.addActivity(createEntity);
							parseActivities(element.elements(), createEntity);
						} else if ("exec".equals(element.getName())) {
							JavaEmbedding11g embedding = new JavaEmbedding11g(element.attributeValue("name"), element.attributeValue("version"), element.attributeValue("language"), element.getTextTrim(),
									getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(embedding);
							parseActivities(element.elements(), embedding);
						} else if ("invoke".equals(element.getName())) {
							Invoke11g invoke11g = new Invoke11g(element.attributeValue("name"), element.attributeValue("inputVariable"), element.attributeValue("outputVariable"),
									element.attributeValue("eventName"), element.attributeValue("partnerLink"), element.attributeValue("portType"), element.attributeValue("operation"),
									element.attributeValue("conversionId"), element.attributeValue("invokeAsDetail"), element.attributeValue("detailLabel"), element.attributeValue("headerVariable"),
									getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(invoke11g);
							parseActivities(element.elements(), invoke11g);
						} else if ("receiveSignal".equals(element.getName())) {
							ReceiveSignal receiveSignal = new ReceiveSignal(element.attributeValue("name"), element.attributeValue("label"), element.attributeValue("from"));
							rootActivity.addActivity(receiveSignal);
							parseActivities(element.elements(), receiveSignal);
						} else if ("removeEntity".equals(element.getName())) {
							RemoveEntity removeEntity = new RemoveEntity(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("skipCondition"));
							rootActivity.addActivity(removeEntity);
							parseActivities(element.elements(), removeEntity);
						} else if ("replay".equals(element.getName())) {
							Replay replay = new Replay(element.attributeValue("name"), element.attributeValue("scope"), element.attributeValue("skipCondition"));
							rootActivity.addActivity(replay);
							parseActivities(element.elements(), replay);
						} else if ("reply".equals(element.getName())) {
							Reply11g reply11g = new Reply11g(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("portType"),
									element.attributeValue("operation"), element.attributeValue("eventName"), element.attributeValue("faultName"), element.attributeValue("headerVariable"),
									getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(reply11g);
							parseActivities(element.elements(), reply11g);
						} else if ("signal".equals(element.getName())) {
							Signal signal = new Signal(element.attributeValue("name"), element.attributeValue("label"), element.attributeValue("to"));
							rootActivity.addActivity(signal);
							parseActivities(element.elements(), signal);
						} else if ("terminate".equals(element.getName())) {
							Terminate11g terminate = new Terminate11g(element.attributeValue("name"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(terminate);
							parseActivities(element.elements(), terminate);
						} else if ("validate".equals(element.getName())) {
							Validate validate = new Validate(element.attributeValue("name"), element.attributeValue("variables"), element.attributeValue("skipCondition"));
							rootActivity.addActivity(validate);
							parseActivities(element.elements(), validate);
						} else if ("compensationHandler".equals(element.getName())) {
							CompensationHandler compensationHandler = new CompensationHandler(element.attributeValue("name"), getDocumentation(element));
							rootActivity.addActivity(compensationHandler);
							parseActivities(element.elements(), compensationHandler);
						} else if ("eventHandlers".equals(element.getName())) {
							EventHandlers11g eventHandlers11g = new EventHandlers11g();
							rootActivity.addActivity(eventHandlers11g);
							parseActivities(element.elements(), eventHandlers11g);
						} else if ("onMessage".equals(element.getName())) {
							Activity a = null;
							String eventName = element.attributeValue("eventName");
							if (eventName != null) {
								a = new OnMessage11g(element.attributeValue("conversionId"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("portType"),
										element.attributeValue("operation"), element.attributeValue("headerVariable"), getDocumentation(element), element.attributeValue("skipCondition"));
							} else {
								a = new OnMessageEvent(element.attributeValue("conversionId"), element.attributeValue("variable"), eventName);
							}
							rootActivity.addActivity(a);
							parseActivities(element.elements(), a);
						} else if ("assign".equals(element.getName())) {
							parseAssign(element, rootActivity);
						} else if ("wait".equals(element.getName())) {
							Wait11g wait = new Wait11g(element.attributeValue("name"), element.attributeValue("for"), element.attributeValue("until"), getDocumentation(element),
									element.attributeValue("skipCondition"));
							rootActivity.addActivity(wait);
							parseActivities(element.elements(), wait);
						} else if ("flow".equals(element.getName())) {
							Flow11g flow = new Flow11g(element.attributeValue("name"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(flow);
							parseActivities(element.elements(), flow);
						} else if ("flowN".equals(element.getName())) {
							FlowN11g flowN = new FlowN11g(element.attributeValue("name"), element.attributeValue("N"), element.attributeValue("indexVariable"), getDocumentation(element),
									element.attributeValue("skipCondition"));
							rootActivity.addActivity(flowN);
							parseActivities(element.elements(), flowN);
						} else if ("pick".equals(element.getName())) {
							Pick11g pick = new Pick11g(element.attributeValue("name"), element.attributeValue("createInstance"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(pick);
							parseActivities(element.elements(), pick);
						} else if ("onAlarm".equals(element.getName())) {
							OnAlarm11g onAlarm = new OnAlarm11g(element.attributeValue("for"), element.attributeValue("until"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(onAlarm);
							parseActivities(element.elements(), onAlarm);
						} else if ("switch".equals(element.getName())) {
							Switch11g switchh = new Switch11g(element.attributeValue("name"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(switchh);
							parseActivities(element.elements(), switchh);
						} else if ("case".equals(element.getName())) {
							String name = null;
							Element eAnnotation = element.element("annotation");
							if (eAnnotation != null) {
								name = eAnnotation.element("pattern").getTextTrim();
							}
							Case casee = new Case(name, element.attributeValue("condition"), getDocumentation(element));
							rootActivity.addActivity(casee);
							parseActivities(element.elements(), casee);
						} else if ("otherwise".equals(element.getName())) {
							Otherwise11g otherwise = new Otherwise11g(getDocumentation(element));
							rootActivity.addActivity(otherwise);
							parseActivities(element.elements(), otherwise);
						} else if ("while".equals(element.getName())) {
							While11g whilee = new While11g(element.attributeValue("name"), element.attributeValue("condition"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(whilee);
							parseActivities(element.elements(), whilee);
						} else if ("throw".equals(element.getName())) {
							Throw11g throw11g = new Throw11g(element.attributeValue("name"), element.attributeValue("faultVariable"), getDocumentation(element), element.attributeValue("skipCondition"));
							rootActivity.addActivity(throw11g);
							parseActivities(element.elements(), throw11g);
						}
						
			  }catch(Exception ex){
				  System.out.println(ex.getMessage());		  
			  }
			}
		}
	}

	private final String getDocumentation(Element element) {
		Element eAnnotation = element.element("annotation");
		if (eAnnotation != null) {
			return eAnnotation.elementTextTrim("documentation");
		}
		return null;
	}

	/**
	 * parse element scope
	 * 
	 * @param element
	 */
	private final void parseScopes(Element element, Activity rootActivity) {
		String pattern = getScopePattern(element);
		if (pattern != null && pattern.contains(":email")) {
			Email email = new Email(element.attributeValue("name"));
			rootActivity.addActivity(email);
			parseActivities(element.elements(), email);
		} else if (pattern != null && pattern.contains(":im")) {
			Im im = new Im(element.attributeValue("name"), element.attributeValue("skipCondition"));
			rootActivity.addActivity(im);
			parseActivities(element.elements(), im);
		} else if (pattern != null && pattern.contains(":milestone")) {
			Milestone milestone = new Milestone(element.attributeValue("name"), element.attributeValue("skipCondition"));
			rootActivity.addActivity(milestone);
			parseActivities(element.elements(), milestone);
		} else if (pattern != null && pattern.contains(":phase")) {
			Phase phase = new Phase(element.attributeValue("name"), element.attributeValue("skipCondition"));
			rootActivity.addActivity(phase);
			parseActivities(element.elements(), phase);
		} else if (pattern != null && pattern.contains(":decide")) {
			Rule rule = new Rule(element.attributeValue("name"), element.attributeValue("variableAccessSerializable"), getDocumentation(element), element.attributeValue("skipCondition"));
			rootActivity.addActivity(rule);
			parseActivities(element.elements(), rule);
		} else if (pattern != null && pattern.contains(":sms")) {
			Sms11g sms = new Sms11g(element.attributeValue("name"), element.attributeValue("skipCondition"));
			rootActivity.addActivity(sms);
			parseActivities(element.elements(), sms);
		} else if (pattern != null && pattern.contains(":usrnotif")) {
			UserNotification userNotification = new UserNotification(element.attributeValue("name"), element.attributeValue("skipCondition"));
			rootActivity.addActivity(userNotification);
			parseActivities(element.elements(), userNotification);
		} else if (pattern != null && pattern.contains(":workflow")) {
			HumanTask humanTask = new HumanTask(element.attributeValue("name"), getDocumentation(element));
			rootActivity.addActivity(humanTask);
			parseActivities(element.elements(), humanTask);
		} else if (pattern != null && pattern.contains(":voice")) {
			Voice11g voice = new Voice11g(element.attributeValue("name"), getDocumentation(element), element.attributeValue("skipCondition"));
			rootActivity.addActivity(voice);
			parseActivities(element.elements(), voice);
		} else {
			Scope11g scope = new Scope11g(element.attributeValue("name"), element.attributeValue("variableAccessSerializable"), getDocumentation(element), element.attributeValue("skipCondition"));
			rootActivity.addActivity(scope);
			parseActivities(element.elements(), scope);
		}
	}

	/**
	 * parse assign activities
	 * 
	 * @param element
	 * @param rootActivity
	 */
	private final void parseAssign(Element element, Activity rootActivity) {
		String pattern = getAssignPattern(element);

		if (pattern != null && pattern.contains("transformation")) {
			Transform11g transform = new Transform11g(element.attributeValue("name"), null, null, getDocumentation(element), element.attributeValue("skipCondition"));
			rootActivity.addActivity(transform);
			parseActivities(element.elements(), transform);
		} else {
			Assign11g assign = new Assign11g(element.attributeValue("name"), getDocumentation(element), element.attributeValue("skipCondition"));
			rootActivity.addActivity(assign);
			parseActivities(element.elements(), assign);
		}
	}

	/**
	 * parse activity pattern
	 * 
	 * @param element
	 * @return
	 */
	private final String getScopePattern(Element element) {
		Element eAnnotation = element.element("annotation");
		if (eAnnotation != null) {
			Element ePattern = eAnnotation.element("pattern");
			if (ePattern != null) {
				return ePattern.attributeValue("patternName");
			}
		}
		return null;
	}

	private final String getAssignPattern(Element element) {
		Element eAnnotation = element.element("annotation");
		if (eAnnotation != null) {
			return eAnnotation.elementTextTrim("pattern");
		}
		return null;
	}

	/**
	 * parse variables
	 * 
	 * @param elements
	 * @param variables
	 */
	private final void parseVariables(List<?> elements, Variables variables) {
		if (elements != null) {
			for (Object o : elements) {
				Element element = (Element) o;
				if ("variable".equals(element.getName())) {
					variables.addActivity(new Variable(element.attributeValue("name"), element.attributeValue("messageType"), element.attributeValue("element"), element.attributeValue("type")));
				}
			}
		}
	}

	/**
	 * parse partner links
	 * 
	 * @param elements
	 * @param rootActivity
	 */
	private final void parsePartnerLinks(List<?> elements, Activity rootActivity) {
		if (elements != null) {
			for (Object o : elements) {
				Element ePartnerLink = (Element) o;
				if ("partnerLink".equals(ePartnerLink.getName())) {
					rootActivity.addActivity(new PartnerLink(ePartnerLink.attributeValue("name"), ePartnerLink.attributeValue("partnerLinkType"), ePartnerLink.attributeValue("myRole"), ePartnerLink
							.attributeValue("partnerRole")));
				}
			}
		}
	}

}
