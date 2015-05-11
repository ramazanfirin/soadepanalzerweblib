package com.tomecode.soa.ora.suite11g.parser;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.suite11g.jca.JCADependencyNode;
import com.tomecode.soa.ora.suite11g.project.Ora11gComposite;
import com.tomecode.soa.ora.suite11g.project.Ora11gComposite.BindingJCA;
import com.tomecode.soa.ora.suite11g.project.sca.CompositeService;
import com.tomecode.soa.ora.suite11g.project.sca.CompositeService.CompositeServiceType;
import com.tomecode.soa.ora.suite11g.project.sca.ScaComponent;
import com.tomecode.soa.ora.suite11g.project.sca.ScaService;
import com.tomecode.soa.ora.suite11g.project.sca.Wire;
import com.tomecode.soa.ora.suite11g.project.sca.component.Event;
import com.tomecode.soa.ora.suite11g.project.sca.component.Event.EventType;
import com.tomecode.soa.ora.suite11g.project.sca.component.HumanTask;
import com.tomecode.soa.ora.suite11g.project.sca.component.Rule;
import com.tomecode.soa.ora.suite11g.project.sca.component.bpel.Ora11gBpelActivityDependency;
import com.tomecode.soa.ora.suite11g.project.sca.component.bpel.Ora11gBpelProcess;
import com.tomecode.soa.ora.suite11g.project.sca.component.bpel.Ora11gBpelReference;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.Mediator;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.Mediator.MediatorActions;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.MediatorEventHandler;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.MediatorEventNode;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.MediatorOperation;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.MediatorOperationCase;
import com.tomecode.soa.ora.suite11g.project.sca.component.mediator.MediatorOperationNode;
import com.tomecode.soa.ora.suite11g.project.sca.service.DBAdapter;
import com.tomecode.soa.ora.suite11g.project.sca.service.FileAdapter;
import com.tomecode.soa.ora.suite11g.project.sca.service.FtpAdapter;
import com.tomecode.soa.ora.suite11g.project.sca.service.WebService;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.protocols.jca.parser.JcaAdapterParser;
import com.tomecode.soa.services.jca.JCABase;
import com.tomecode.soa.services.jca.JCAEndpointConfig;
import com.tomecode.soa.wsdl.Wsdl;
import com.tomecode.soa.wsdl.WsdlOperation;
import com.tomecode.soa.wsdl.WsdlPortType;
import com.tomecode.soa.wsdl.parser.WsdlParser;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Parser for composite.xml
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
final class Ora11gCompositeParser extends AbstractParser {

	/***
	 * jca adapter parser
	 */
	private final JcaAdapterParser jcaAdapterParser = new JcaAdapterParser();

	private final Ora11gBpelParser bpelProcessParser = new Ora11gBpelParser();

	/**
	 * wsdl file parser
	 */
	private final WsdlParser wsdlParser = new WsdlParser();

	Ora11gCompositeParser() {
	}

	/**
	 * parse {@link Ora11gComposite} xml file
	 * 
	 * @param projectFile
	 * @return
	 */
	final Ora11gComposite parse(File projectFile) {

		if (projectFile.toString().contains("AsyncMediatorSample")) {
			toString();
		}
		File compositeFile = findFile(projectFile, ORACLE_SOA_SUITE_11G_COMPOSITE_XML);
		try {
			if (compositeFile != null) {
				Element element = parseXml(compositeFile);

				if (element != null && element.getName().equals("composite")) {
					Ora11gComposite composite = new Ora11gComposite(element.attributeValue("name"), compositeFile);

					parseServices(element, composite);
					parseReferences(element, composite);
					parseComponents(element, composite);
					parseWire(element, composite);
					return composite;
				}

			}
		} catch (Exception e) {
			// TODO error handler
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * parse element WIRE
	 * 
	 * @param element
	 * @param composite
	 */
	private void parseWire(Element element, Ora11gComposite composite) {
		List<?> list = element.elements("wire");
		if (list != null) {
			for (Object o : list) {
				Element eWire = (Element) o;

				Wire wire = new Wire();
				composite.addWire(wire);
				String uri = eWire.elementTextTrim("source.uri");
				if (uri != null) {
					int slash = uri.indexOf("/");
					if (slash != -1) {
						uri = uri.substring(0, slash);
					}
					ScaService cs = findService(uri, composite);
					if (cs != null) {
						wire.setSource(cs);
					} else {
						wire.setSource(findComponent(uri, composite, CompositeServiceType.COMPONENT));
					}
				}
				uri = eWire.elementTextTrim("target.uri");
				if (uri != null) {
					int slash = uri.indexOf("/");
					if (slash != -1) {
						uri = uri.substring(0, slash);
					}

					ScaService cs = findService(uri, composite);
					if (cs != null) {
						wire.setTarget(cs);
					} else {
						wire.setTarget(findComponent(uri, composite, CompositeServiceType.COMPONENT));
					}
				}
			}
		}
	}

	/**
	 * find {@link ScaComponent} by name
	 * 
	 * @param componentName
	 * @param composite
	 * @return
	 */
	private final ScaComponent findComponent(String componentName, Ora11gComposite composite, CompositeServiceType type) {
		for (CompositeService compositeService : composite.getCompositeServices()) {
			if (compositeService.getCompositeServiceType() == type) {
				if (compositeService.getName().equals(componentName)) {
					return (ScaComponent) compositeService;
				}
			}
		}
		return null;
	}

	/**
	 * find service project
	 * 
	 * @param serviceName
	 * @param composite
	 * @return
	 */
	private final ScaService findService(String serviceName, Ora11gComposite composite) {
		for (CompositeService compositeService : composite.getCompositeServices()) {
			if (compositeService.getCompositeServiceType() != CompositeServiceType.COMPONENT) {
				if (compositeService.getName().equals(serviceName)) {
					return (ScaService) compositeService;
				}
			}
		}
		return null;
	}

	/**
	 * parse element SERVICE
	 * 
	 * @param eComposite
	 * @param composite
	 */
	private void parseServices(Element eComposite, Ora11gComposite composite) {
		List<?> elements = eComposite.elements("service");
		if (elements != null) {
			parseServicesOrRefernces(elements, composite, CompositeServiceType.SERVICE);
		}
	}

	/**
	 * parse element SERVICE or element REFERENCE
	 * 
	 * @param elements
	 * @param composite
	 * @param serivceType
	 */
	private final void parseServicesOrRefernces(List<?> elements, Ora11gComposite composite, CompositeServiceType serivceType) {

		for (Object o : elements) {
			Element eService = (Element) o;

			Element eInterfaceWsdl = eService.element("interface.wsdl");
			boolean isWS = eService.element("binding.ws") != null;
			if (eInterfaceWsdl != null) {
				String interfacee = eInterfaceWsdl.attributeValue("interface");

				if (isWS) {					
					WebService webService = new WebService(eService.attributeValue("name"), eService.attributeValue("wsdlLocation"), serivceType);
					composite.addService(webService);
					Wsdl serviceWsdl = parseWsdlLocation(composite.getFile().getParentFile(), webService.getWsdlLocation());
					boolean isInMds= serviceWsdl ==null ? true:false;
					if(!isInMds) webService.setWsdl(serviceWsdl);
						webService.addPortType(getPortTypeFormInterface(interfacee));
					if(!isInMds) webService.setInterface(interfacee);
						parseBindingWS(eService, webService);					
					continue;
				}

				if (interfacee.contains("/pcbpel/adapter/ftp/")) {
					FtpAdapter ftpAdapter = new FtpAdapter(eService.attributeValue("name"), eService.attributeValue("wsdlLocation"), serivceType);
					composite.addService(ftpAdapter);
					ftpAdapter.setWsdl(parseWsdlLocation(composite.getFile().getParentFile(), ftpAdapter.getWsdlLocation()));
					ftpAdapter.addPortType(getPortTypeFormInterface(interfacee));
					parseBindingJCA(eService, ftpAdapter);
				} else if (interfacee.contains("/pcbpel/adapter/db/")) {
					DBAdapter dbAdapter = new DBAdapter(eService.attributeValue("name"), eService.attributeValue("wsdlLocation"), serivceType);
					composite.addService(dbAdapter);
					dbAdapter.setWsdl(parseWsdlLocation(composite.getFile().getParentFile(), dbAdapter.getWsdlLocation()));
					dbAdapter.addPortType(getPortTypeFormInterface(interfacee));
					parseBindingJCA(eService, dbAdapter);
				} else if (interfacee.contains("/pcbpel/adapter/file/")) {
					FileAdapter fileAdapter = new FileAdapter(eService.attributeValue("name"), eService.attributeValue("wsdlLocation"), serivceType);
					composite.addService(fileAdapter);
					fileAdapter.setWsdl(parseWsdlLocation(composite.getFile().getParentFile(), fileAdapter.getWsdlLocation()));
					fileAdapter.addPortType(getPortTypeFormInterface(interfacee));
					parseBindingJCA(eService, fileAdapter);
				} else {
					// TODO: dokoncit dalsie typy
					// throw new RuntimeException("nepoznam interface: " +
					// interfacee);
				}
			}
		}
	}

	/**
	 * parse from interface.wsdl WSDL port type
	 * 
	 * @param wsdlInterface
	 * @return
	 */
	private final String getPortTypeFormInterface(String wsdlInterface) {
		int index = wsdlInterface.indexOf("wsdl.interface(");
		if (index != -1) {
			return wsdlInterface.substring(index + 15, wsdlInterface.length() - 1);
		}

		return null;
	}

	/**
	 * parse binding
	 * 
	 * @param eRoot
	 * @param composite
	 */
	private final void parseBindingWS(Element eRoot, ScaService composite) {
		Element eBinding = eRoot.element("binding.ws");
		if (eBinding != null) {
			// TODO: DOKONCIT PARSOVANIE PRE WSDL
		}

	}

	/**
	 * parse JCA binding
	 * 
	 * @param eRoot
	 * @param composite
	 */
	private final void parseBindingJCA(Element eRoot, ScaService composite) {
		Element eBinding = eRoot.element("binding.jca");
		if (eBinding != null) {
			String jca = eBinding.attributeValue("config");
			if (jca != null) {
				File jcaFile = findFile(composite.getComposite().getFile().getParentFile(), jca);
				if (jcaFile != null) {
					JCABase jcaBase = jcaAdapterParser.parseJca(jcaFile);
					((BindingJCA) composite).setJcaBase(jcaBase);
				}
			}
		}

	}

	/**
	 * parse {@link Wsdl} from wsdllocation attribute
	 * 
	 * @param folder
	 * @param wsdlLocation
	 * @return
	 */
	private final Wsdl parseWsdlLocation(File folder, String wsdlLocation) {
		File wsdlFile = findFile(folder, wsdlLocation);
		if (wsdlFile != null) {
			return wsdlParser.parseWsdl(wsdlFile);
		}

		return null;
	}

	/**
	 * parse all {@link ScaComponent} in composite.xml
	 * 
	 * @param eComposite
	 * @param composite
	 */
	private final void parseComponents(Element eComposite, Ora11gComposite composite) {
		List<?> elements = eComposite.elements("component");
		if (elements != null) {
			File projectFolder = composite.getFile().getParentFile();

			for (Object o : elements) {
				Element eComponent = (Element) o;

				List<?> eTypes = eComponent.elements();
				if (eTypes != null) {
					for (Object oe : eTypes) {
						Element eType = (Element) oe;
						if ("implementation.mediator".equals(eType.getName())) {
							parseEvents(eComponent, composite, projectFolder);
							composite.addComponent(parseMediator(eComponent, projectFolder));
						} else if ("implementation.workflow".equals(eType.getName())) {
							composite.addComponent(new HumanTask(eComponent.attributeValue("name"), null));
						} else if ("implementation.decision".equals(eType.getName())) {
							composite.addComponent(new Rule(eComponent.attributeValue("name"), null));
						} else if ("implementation.bpel".equals(eType.getName())) {
							composite.addComponent(bpelProcessParser.parse(eComponent.attributeValue("name"), findFile(composite.getFile().getParentFile(), eType.attributeValue("src"))));
						}
					}
				}
			}
		}
	}

	/**
	 * parse {@link Event}
	 * 
	 * @param eComponent
	 * @param composite
	 * @param projectFolder
	 */
	private final void parseEvents(Element eComponent, Ora11gComposite composite, File projectFolder) {
		Element eBusinessEvents = eComponent.element("business-events");
		if (eBusinessEvents != null) {
			List<?> list = eBusinessEvents.elements("publishes");
			if (list != null) {
				for (Object o : list) {
					Element ePuhlishes = (Element) o;
					// TODO: naparsovat event def subor .edl
					// TODO: naparsovate ostatne typy eventov ako subscriber atd
					composite.addComponent(new Event(ePuhlishes.attributeValue("name"), null, ePuhlishes.attributeValue("consistency"), EventType.PUBLISHES));
				}
			}
			list = eBusinessEvents.elements("subscribe");
			if (list != null) {
				for (Object o : list) {
					Element eSubscriber = (Element) o;
					composite.addComponent(new Event(eSubscriber.attributeValue("name"), null, eSubscriber.attributeValue("consistency"), EventType.SUBSCRIBE));
				}
			}
		}
	}

	/**
	 * parse {@link Mediator}
	 * 
	 * @param eComponent
	 * @param projectFolder
	 * @return
	 */
	private final Mediator parseMediator(Element eComponent, File projectFolder) {
		Element eImplementation = eComponent.element("implementation.mediator");
		if (eImplementation != null) {
			File mplan = findFile(projectFolder, eImplementation.attributeValue("src"));
			try {
				Element eMediator = parseXml(mplan);
				if (eMediator != null) {
					Mediator mediator = new Mediator(eMediator.attributeValue("name"), mplan);

					List<?> listActions = eMediator.elements("operation");
					if (listActions != null) {
						for (Object o : listActions) {
							mediator.addMediatorOperation(parseMediatorOperation((Element) o));
						}
					}
					listActions = eMediator.elements("eventHandler");
					if (listActions != null) {
						for (Object o : listActions) {
							mediator.addMediatorEventHandler(parseMediatorEventHandler((Element) o));
						}
					}
					return mediator;
				}
			} catch (ServiceParserException e) {
				// TODO error handler
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * parse {@link MediatorEventHandler}
	 * 
	 * @param eventHandler
	 * @return
	 */
	private final MediatorEventHandler parseMediatorEventHandler(Element eEventHandler) {
		MediatorEventHandler mediatorEventHandler = new MediatorEventHandler();
		mediatorEventHandler.setEvent(eEventHandler.attributeValue("event"));
		mediatorEventHandler.setDeliveryPolicy(eEventHandler.attributeValue("deliveryPolicy"));
		mediatorEventHandler.setPriority(eEventHandler.attributeValue("priority"));

		parseMediatorCases(mediatorEventHandler, eEventHandler);
		return mediatorEventHandler;
	}

	/**
	 * parse {@link MediatorOperation }
	 * 
	 * @param eOperation
	 * @return
	 */
	private final MediatorOperation parseMediatorOperation(Element eOperation) {
		MediatorOperation operation = new MediatorOperation(eOperation.attributeValue("name"));
		operation.setDeliveryPolicy(eOperation.attributeValue("deliveryPolicy"));
		operation.setPriority(eOperation.attributeValue("priority"));
		operation.setValidateSchema(eOperation.attributeValue("validateSchema"));

		Element eCustomFunction = eOperation.element("customFunction");
		if (eCustomFunction != null) {
			operation.setJavaFunction(eCustomFunction.elementTextTrim("javaFunction"));
			operation.setPLSQLFunction(eCustomFunction.elementTextTrim("PLSQLFunction"));
		}

		parseMediatorCases(operation, eOperation);
		return operation;
	}

	private final void parseMediatorCases(MediatorActions mediatorActions, Element eOperation) {
		Element eSwitch = eOperation.element("switch");
		if (eSwitch != null) {
			List<?> list = eSwitch.elements("case");
			if (list != null) {
				for (Object o : list) {
					mediatorActions.addOperationCase(parseOperationCase((Element) o));
				}
			}
		}
	}

	/**
	 * parse {@link MediatorOperationCase}
	 * 
	 * @param eCase
	 * @return
	 */
	private final MediatorOperationCase parseOperationCase(Element eCase) {
		MediatorOperationCase operationCase = new MediatorOperationCase(eCase.attributeValue("name"));
		operationCase.setExecutionType(eCase.attributeValue("executionType"));
		operationCase.setDefaultRule(eCase.attributeValue("defaultRule"));

		Element eCondition = eCase.element("condition");
		if (eCondition != null) {
			operationCase.setLanguage(eCondition.attributeValue("language"));
			operationCase.setConditionExpression(eCondition.elementTextTrim("expression"));
		}

		// TODO: DOPARSOVAT ACTION -- NAPARSVOVAT TARGET OPERATION A POTOM
		// NACITAT

		Element eAction = eCase.element("action");
		if (eAction != null) {
			Element eInvoke = eAction.element("invoke");
			if (eInvoke != null) {
				operationCase.setReference(eInvoke.attributeValue("reference"));
				operationCase.setOperation(eInvoke.attributeValue("operation"));
			}
			eInvoke = eAction.element("raise");
			if (eInvoke != null) {
				operationCase.setEvent(eInvoke.attributeValue("event"));
			}
		}

		return operationCase;
	}

	/**
	 * parse references
	 * 
	 * @param element
	 * @param composite
	 */
	private final void parseReferences(Element eComposite, Ora11gComposite composite) {
		List<?> elements = eComposite.elements("reference");
		if (elements != null) {
			parseServicesOrRefernces(elements, composite, CompositeServiceType.REFERENCE);
		}
	}

	/**
	 * analysis dependencies in {@link Ora11gComposite}
	 * 
	 * @param composite
	 */
	public final void analyzeDependencies(Ora11gComposite composite) {
		for (Wire wire : composite.getWires()) {
			if (wire.getTarget() instanceof Mediator) {
				Mediator mediator = (Mediator) wire.getTarget();
				for (MediatorActions mediatorActions : mediator.getMediatorActions()) {
					// linking WSDL operations
					if (mediatorActions instanceof MediatorOperation) {
						MediatorOperation mediatorOperation = (MediatorOperation) mediatorActions;
						// mediatorOperation.setMediatorOperationType(MediatorOperationType.SOURCE);
						if (wire.getSource() != null && wire.getSource() instanceof ScaService) {
							ScaService scaService = (ScaService) wire.getSource();
							WsdlOperation wsdlOperation = findWsdlOperation(mediatorOperation.getName(), scaService.getPortTypes());
							if (wsdlOperation != null && !containsMediatorOperation(wsdlOperation, mediatorOperation)) {
								wsdlOperation.addDependnecy(new MediatorOperationNode(wsdlOperation, mediatorOperation));
							}
						}
					}
					linkingReferenceToOperationCase(mediatorActions, composite);
				}
			} else if (wire.getTarget() instanceof Ora11gBpelProcess) {
				Ora11gBpelProcess targetBpel = (Ora11gBpelProcess) wire.getTarget();

				if (wire.getSource() != null && wire.getSource() instanceof WebService) {
					WebService webService = (WebService) wire.getSource();
					webService.getInterface();
					// porovnavanie interface
					Ora11gBpelReference reference = findInterfaceForReference(webService.getInterface(), targetBpel.getBpelReferences());
					if (reference != null) {
						reference.setDependencyService(webService);

						if (composite.toString().contains("AsyncMediatorSample")) {
							toString();
						}

						for (WsdlPortType portType : webService.getPortTypes()) {
							for (WsdlOperation operation : portType.getWsdlOperations()) {
								for (Ora11gBpelActivityDependency activityDependency : targetBpel.getActivityDependencies()) {
									if (activityDependency.compareByOperation(portType.getName(), operation.getName())) {
										operation.addDependnecy(targetBpel);
										// operation.addDependnecy(new
										// Ora11gBpelActivityDependencyWrap(bpelProcess,
										// activityDependency));
									}
								}
							}
						}
					}
				} else if (wire.getSource() != null && wire.getSource() instanceof Ora11gBpelProcess) {
					Ora11gBpelProcess sourceBpel = (Ora11gBpelProcess) wire.getSource();

					if (!sourceBpel.getBpelReferences().isEmpty()) {
						// get first service from reference list(partnerlinks)
						Ora11gBpelReference reference = findInterfaceForReference(targetBpel.getBpelReferences().get(0).getReferenceInterface(), sourceBpel.getBpelReferences());
						if (reference != null) {
							reference.setDependencyService(targetBpel);
						}

					}

				}

			} else if (wire.getSource() instanceof Mediator) {
				Mediator mediator = (Mediator) wire.getSource();

				if (mediator.getName().equals("MediatorBPEL")) {
					toString();
				}

				for (MediatorActions mediatorActions : mediator.getMediatorActions()) {
					// linking WSDL operations
					if (mediatorActions instanceof MediatorOperation) {
						MediatorOperation mediatorOperation = (MediatorOperation) mediatorActions;
						// mediatorOperation.setMediatorOperationType(MediatorOperationType.SOURCE);
						if (wire.getSource() != null && wire.getSource() instanceof ScaService) {
							ScaService scaService = (ScaService) wire.getSource();
							WsdlOperation wsdlOperation = findWsdlOperation(mediatorOperation.getName(), scaService.getPortTypes());
							if (wsdlOperation != null && !containsMediatorOperation(wsdlOperation, mediatorOperation)) {
								wsdlOperation.addDependnecy(new MediatorOperationNode(wsdlOperation, mediatorOperation));
							}
						}
					}
					linkingReferenceToOperationCase(mediatorActions, composite);
				}

			}
		}
		analyzeDependneciesEvent(composite);
		analyzeDependenciesServicesAndAdapters(composite);
	}

	private final Ora11gBpelReference findInterfaceForReference(String interfacee, List<Ora11gBpelReference> bpelReferences) {
		for (Ora11gBpelReference bpelReference : bpelReferences) {
			if (interfacee.equals(bpelReference.getReferenceInterface())) {
				return bpelReference;
			}
		}
		return null;
	}

	/**
	 * linking mediator operations with target services
	 * 
	 * @param mediatorActions
	 * @param composite
	 */
	private final void linkingReferenceToOperationCase(MediatorActions mediatorActions, Ora11gComposite composite) {
		for (MediatorOperationCase operationCase : mediatorActions.getOperationCases()) {
			// find service
			operationCase.addReferenceObject(findService(operationCase.getReference(), composite));
			// find event
			operationCase.addReferenceObject(findComponent(operationCase.getEvent(), composite, CompositeServiceType.EVENT));
		}
	}

	/**
	 * analysis (linking) dependencies between events (publishes, subscribers)
	 * 
	 * @param composite
	 */
	private final void analyzeDependneciesEvent(Ora11gComposite composite) {
		for (CompositeService compositeService : composite.getCompositeServices()) {
			if (compositeService.getCompositeServiceType() == CompositeServiceType.EVENT) {
				Event event = (Event) compositeService;
				if (event.getEventType() == EventType.PUBLISHES) {
					for (CompositeService cs : composite.getCompositeServices()) {
						if (cs.getCompositeServiceType() == CompositeServiceType.COMPONENT) {
							Mediator mediator = (Mediator) cs;
							for (MediatorActions actions : mediator.getMediatorActions()) {
								if (actions instanceof MediatorEventHandler) {
									MediatorEventHandler eventHandler = (MediatorEventHandler) actions;
									if (eventHandler.getEvent().equals(event.getName())) {
										// TODO: dokoncit visualizaciu
										event.addDependency(new MediatorEventNode(mediator, eventHandler));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * analysis (linking) dependencies between services and adapters
	 * 
	 * @param composite
	 */
	private final void analyzeDependenciesServicesAndAdapters(Ora11gComposite composite) {
		for (CompositeService compositeService : composite.getCompositeServices()) {
			if (compositeService.getCompositeServiceType() == CompositeServiceType.SERVICE && compositeService instanceof BindingJCA) {
				linkinJCAtoWsdlOperation(compositeService, true);
			} else if (compositeService.getCompositeServiceType() == CompositeServiceType.REFERENCE && compositeService instanceof BindingJCA) {
				linkinJCAtoWsdlOperation(compositeService, false);
			}
		}
	}

	private final void linkinJCAtoWsdlOperation(CompositeService compositeService, boolean isActivation) {
		BindingJCA jca = (BindingJCA) compositeService;
		ScaService scaService = (ScaService) compositeService;
		if (jca.getJcaBase() != null && scaService.getWsdl() != null) {
			for (WsdlPortType portType : scaService.getWsdl().getPortTypes()) {
				for (WsdlOperation operation : portType.getWsdlOperations()) {
					JCAEndpointConfig endpointConfig = null;
					if (isActivation) {
						endpointConfig = jca.getJcaBase().findEndpointActivation(portType.getName(), operation.getName());
					} else {
						endpointConfig = jca.getJcaBase().findEndpointInteraction(portType.getName(), operation.getName());
					}
					if (endpointConfig != null) {
						operation.addDependnecy(new JCADependencyNode(endpointConfig, operation));
					}
				}
			}
		}
	}

	private final boolean containsMediatorOperation(WsdlOperation wsdlOperation, MediatorOperation mediatorOperation) {
		for (Object obj : wsdlOperation.getDependencies()) {
			if (obj instanceof MediatorOperationNode) {
				if (((MediatorOperationNode) obj).getMediatorOperation().equals(mediatorOperation)) {
					return true;
				}
			}
		}
		return false;
	}

	private final WsdlOperation findWsdlOperation(String name, List<WsdlPortType> portTypes) {
		for (WsdlPortType portType : portTypes) {
			WsdlOperation wsdlOperation = portType.findWsdlOperations(name);
			if (wsdlOperation != null) {
				return wsdlOperation;
			}
		}
		return null;
	}
}
