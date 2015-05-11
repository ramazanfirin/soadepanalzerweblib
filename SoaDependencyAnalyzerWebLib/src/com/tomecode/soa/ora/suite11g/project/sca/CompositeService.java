package com.tomecode.soa.ora.suite11g.project.sca;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.dependency.analyzer.tools.ReferencedBy;
import com.tomecode.soa.dependency.analyzer.tools.ToolEndpointList;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.suite11g.project.Ora11gComposite;
import com.tomecode.soa.ora.suite11g.project.sca.component.Event;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Service in {@link Ora11gComposite} - Exposed Services
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class CompositeService extends ReferencedBy implements ImageFace, ToolTip, ToolEndpointList {

	private Ora11gComposite composite;
	
	private OSBService refOsbService;
	
	/**
	 * composite service name
	 */
	//@PropertyViewData(title = "Name: ")
	protected final String name;

	/**
	 * composite service type
	 */
	protected final CompositeServiceType compositeServiceType;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            service name
	 * @param type
	 *            service type
	 */
	public CompositeService(String name, CompositeServiceType type) {
		this.compositeServiceType = type;
		this.name = name;
	}

	public final CompositeServiceType getCompositeServiceType() {
		return compositeServiceType;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

	/**
	 * @return the composite
	 */
	public final Ora11gComposite getComposite() {
		return composite;
	}

	/**
	 * @param composite
	 *            the composite to set
	 */
	public final void setComposite(Ora11gComposite composite) {
		this.composite = composite;
	}

	public final void calculateEndpointList(EndpointList endpointList) {

	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder("Type:		");
		sb.append(getType());
		if (name != null && name.trim().length() != 0) {
			sb.append("\nName:		").append(name);
		}
		return sb.toString();
	}

	/**
	 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
	 * 
	 * 
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public enum CompositeServiceType {
		COMPONENT,

		/**
		 * Service - Provide the outside world with an entry point to the SOA
		 * composite application. The WSDL file of the service advertises its
		 * capabilities to external applications. These capabilities are used
		 * for contacting the SOA composite application components.
		 */
		SERVICE,

		/**
		 * 
		 * Reference â- Enable messages to be sent from the SOA composite
		 * application to external services in the outside world (for example,
		 * the same functionality as partner links provide for BPEL processes,
		 * but at the higher SOA composite application level). Each BPEL partner
		 * link has a corresponding reference at the composite level.
		 */
		REFERENCE,
		/**
		 * business event - {@link Event}
		 */
		EVENT;
	}

	
	public OSBService getOsbService() {
		return refOsbService;
	}

	public void setOsbService(OSBService osbService) {
		this.refOsbService = osbService;
	}
}
