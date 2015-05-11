package com.tomecode.soa.ora.suite11g.project.sca.component.mediator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.suite11g.project.sca.ScaComponent;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Mediator - for routing events (messages) between different components.
 * (provides enterprise service bus (ESB) functionality)
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Oracle SOA Suite 11g - Mediator")
public final class Mediator extends ScaComponent {

	/**
	 * list of operation
	 */
	private final List<MediatorActions> mediatorActions;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            mediator name
	 * @param file
	 *            mediator mplan file
	 */
	public Mediator(String name, File file) {
		super(name, file);
		this.mediatorActions = new ArrayList<Mediator.MediatorActions>();
	}

	/**
	 * @return the mediatorActions
	 */
	public final List<MediatorActions> getMediatorActions() {
		return mediatorActions;
	}

	/**
	 * add new {@link MediatorOperation}
	 * 
	 * @param operation
	 */
	public final void addMediatorOperation(MediatorOperation operation) {
		operation.setMediator(this);
		this.mediatorActions.add(operation);
	}

	/**
	 * add new {@link MediatorEventHandler}
	 * 
	 * @param eventHandler
	 */
	public final void addMediatorEventHandler(MediatorEventHandler eventHandler) {
		eventHandler.setMediator(this);
		this.mediatorActions.add(eventHandler);
	}

	public final String toString() {
		return name;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_11G_MEDIATOR;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		sb.append("\nFile:			").append(file);
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Mediator Component";
	}

	/**
	 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
	 * 
	 * basic class for {@link MediatorOperation} or {@link MediatorEventHandler}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public static abstract class MediatorActions implements ImageFace, ToolTip {

		protected final List<MediatorOperationCase> operationCases;

		// private MediatorOperationType mediatorOperationType;

		/**
		 * owner {@link Mediator}
		 */
		protected Mediator mediator;

		public MediatorActions() {
			this.operationCases = new ArrayList<MediatorOperationCase>();
		}

		// /**
		// * @return the mediatorOperationType
		// */
		// public final MediatorOperationType getMediatorOperationType() {
		// return mediatorOperationType;
		// }
		//
		// /**
		// * @param mediatorOperationType
		// * the mediatorOperationType to set
		// */
		// public final void setMediatorOperationType(MediatorOperationType
		// mediatorOperationType) {
		// this.mediatorOperationType = mediatorOperationType;
		// }

		/**
		 * @return the operationCases
		 */
		public final List<MediatorOperationCase> getOperationCases() {
			return operationCases;
		}

		public final void addOperationCase(MediatorOperationCase operationCase) {
			operationCase.setMediatorActions(this);
			this.operationCases.add(operationCase);
		}

		/**
		 * @return the mediator
		 */
		public final Mediator getMediator() {
			return mediator;
		}

		/**
		 * @param mediator
		 *            the mediator to set
		 */
		public final void setMediator(Mediator mediator) {
			this.mediator = mediator;
		}
	}
}
