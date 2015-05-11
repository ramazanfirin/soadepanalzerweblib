package com.tomecode.soa.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/***
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * activity types
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public enum ActivityType {

	PARTNERLINKS("partnerLinks", null), VARIABLES("variables", null),

	// ----------OPEN ESB BPEL
	OPEN_ESB_BPEL_SEQUENCE("sequence", ImageFactory.OPEN_ESB_BPEL_SEQUENCE), OPEN_ESB_BPEL_FAULT_HANLDERS("faultHandlers", ImageFactory.OPEN_ESB_BPEL_FAULT_HANDLERS), OPEN_ESB_BPEL_CATCHALL(
			"catchAll", ImageFactory.OPEN_ESB_BPEL_CATCHALL), OPEN_ESB_BPEL_EVENT_HANLDERS("eventHandlers", ImageFactory.OPEN_ESB_BPEL_EVENT_HANLDERS), OPEN_ESB_BPEL_SCOPE("scope",
			ImageFactory.OPEN_ESB_BPEL_SCOPE), OPEN_ESB_BPEL_FLOW("flow", ImageFactory.OPEN_ESB_BPEL_FLOW), OPEN_ESB_BPEL_COMPENSATIO_HANLDER("compensationHandler",
			ImageFactory.OPEN_ESB_BPEL_COMPENSATIO_HANLDER), OPEN_ESB_BPEL_PICK("pick", ImageFactory.OPEN_ESB_BPEL_PICK), OPEN_ESB_BPEL_WAIT("wait", ImageFactory.OPEN_ESB_BPEL_WAIT), OPEN_ESB_BPEL_ASSIGN(
			"assign", ImageFactory.OPEN_ESB_BPEL_ASSIGN), ;

	private final String name;
	private final Image imageIcon;

	private final boolean containsVariable;

	private static final ActivityType[] OPEN_ESB_BPEL = new ActivityType[] { OPEN_ESB_BPEL_SEQUENCE, OPEN_ESB_BPEL_CATCHALL, OPEN_ESB_BPEL_EVENT_HANLDERS, OPEN_ESB_BPEL_SCOPE, OPEN_ESB_BPEL_FLOW,
			OPEN_ESB_BPEL_COMPENSATIO_HANLDER, OPEN_ESB_BPEL_PICK, OPEN_ESB_BPEL_WAIT, OPEN_ESB_BPEL_ASSIGN };

	/**
	 * Constructor
	 * 
	 * @param name
	 *            activity name in *.bpel file
	 * @param imageIcon
	 *            icon
	 */
	private ActivityType(String name, Image imageIcon) {
		this(name, imageIcon, false);
	}

	private ActivityType(String name) {
		this(name, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            XML element name
	 * @param imageIcon
	 *            icon
	 */
	private ActivityType(String name, Image imageIcon, boolean containsVariable) {
		this.name = name;
		this.imageIcon = imageIcon;
		this.containsVariable = containsVariable;
	}

	public final String getName() {
		return name;
	}

	public final Image getImage() {
		return imageIcon;
	}

	public final boolean isContainsVariable() {
		return containsVariable;
	}

	/**
	 * parse {@link ActivityType} for Open ESB - BPEL
	 * 
	 * @param name
	 * @return
	 */
	public static final ActivityType parseOpenEsbBpelActivtyType(String name) {
		for (ActivityType activityType : OPEN_ESB_BPEL) {
			if (activityType.getName().equals(name)) {
				return activityType;
			}
		}
		return parseNeutralTypes(name);
	}

	private static final ActivityType parseNeutralTypes(String name) {
		if (PARTNERLINKS.getName().equals(name)) {
			return PARTNERLINKS;
		} else if (VARIABLES.getName().equals(name)) {
			return VARIABLES;
		}
		return null;
	}

}
