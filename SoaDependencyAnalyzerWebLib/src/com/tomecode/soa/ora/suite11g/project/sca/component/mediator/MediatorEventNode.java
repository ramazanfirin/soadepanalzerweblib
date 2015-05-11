package com.tomecode.soa.ora.suite11g.project.sca.component.mediator;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.ora.suite11g.project.sca.component.Event;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * wrapper for {@link Mediator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class MediatorEventNode implements ImageFace {

	/**
	 * {@link Mediator}
	 */
	//@PropertyViewDataInsideObject
	private final Mediator mediator;

	private final MediatorEventHandler mediatorEventHandler;

	private Event event;

	public MediatorEventNode(Mediator mediator, MediatorEventHandler mediatorEventHandler) {
		this.mediator = mediator;
		this.mediatorEventHandler = mediatorEventHandler;
	}

	/**
	 * @return the mediator
	 */
	public final Mediator getMediator() {
		return mediator;
	}

	/**
	 * @return the mediatorEventHandler
	 */
	public final MediatorEventHandler getMediatorEventHandler() {
		return mediatorEventHandler;
	}

	@Override
	public final Image getImage(boolean small) {
		return mediator.getImage(small);
	}

	@Override
	public final String getToolTip() {
		return mediator.getToolTip();
	}

	/**
	 * @return the event
	 */
	public final Event getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public final void setEvent(Event event) {
		this.event = event;
	}

	public final String toString() {
		return mediator.toString();
	}
}
