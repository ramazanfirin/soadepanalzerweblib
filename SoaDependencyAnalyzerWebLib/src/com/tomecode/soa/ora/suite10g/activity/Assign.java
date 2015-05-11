package com.tomecode.soa.ora.suite10g.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Assign in BPEL process
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public class Assign extends Activity {

	private static final long serialVersionUID = -181327215657391550L;

	private final List<AssignOperation> operations;

	//@PropertyViewData(title = "Documentation")
	private String documentation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param documentation
	 */
	public Assign(String name, String documentation) {
		super(name);
		this.operations = new ArrayList<AssignOperation>();
		this.documentation = documentation;
	}

	@Override
	public String getToolTip() {
		if (documentation != null) {
			StringBuilder sb = new StringBuilder(super.getToolTip());
			sb.append("\nDocumentation: ").append(documentation);
			return sb.toString();
		}
		return super.getToolTip();
	}

	public final void addOperations(AssignOperation operation) {
		operations.add(operation);
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_ASSIGN;
	}

	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (BpelVariable bpelVariable : variableUsage.getBpelVariables()) {
			for (AssignOperation assignOperation : operations) {
				if (variableUsage.containsVariable(assignOperation.getFrom(), bpelVariable.getVariable().getName())) {
					bpelVariable.addUsage(this);
				} else if (variableUsage.containsVariable(assignOperation.getTo(), bpelVariable.getVariable().getName())) {
					bpelVariable.addUsage(this);
				}
			}
		}
		super.findUsesForBpelVariables(variableUsage);
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * assign operation
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public static final class AssignOperation {

		private OperationType type;

		private String fromPartnerLink;
		private String toPartnerLink;

		private String from;
		private String to;

		/**
		 * Constructor
		 * 
		 * @param type
		 * @param from
		 * @param to
		 * @param partnerLink
		 */
		public AssignOperation(OperationType type, String from, String to, String fromPartnerLink, String toPartnerLink) {
			this.type = type;
			this.from = from;
			this.to = to;
			this.fromPartnerLink = fromPartnerLink;
			this.toPartnerLink = toPartnerLink;
		}

		public final String getFromPartnerLink() {
			return fromPartnerLink;
		}

		public final String getToPartnerLink() {
			return toPartnerLink;
		}

		public final OperationType getType() {
			return type;
		}

		public final String getFrom() {
			return from;
		}

		public final String getTo() {
			return to;
		}

	}

	@Override
	public final String getType() {
		return "Assign";
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * assign operation type
	 * 
	 * @author Tomas Frastia
	 * 
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public static enum OperationType {
		COPY, APPEND, INSERT_AFTER, INSERT_BEFORE, COPYLIST, REMOVE, RENAME;
	}

}
