package com.tomecode.soa.dependency.analyzer.tools;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.tools.ToolOsbList.OsbVariable;
import com.tomecode.soa.ora.suite10g.activity.PartnerLink;
import com.tomecode.soa.ora.suite10g.activity.Variable;

/**
 * (c) Copyright Tomecode.com, 2010 -2011. All rights reserved.
 * 
 * 
 * interface for implementing method for create {@link BpelVariableUsage}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public interface ToolBpelList {

	/**
	 * create list of variables in process
	 * 
	 * @param bpelVariableUsage
	 */
	void createListOfBpelVariables(BpelVariableUsage bpelVariableUsage);

	/**
	 * find uses for variables in the process
	 * 
	 * @param variableUsage
	 */
	void findUsesForBpelVariables(BpelVariableUsage variableUsage);

	/**
	 * create list of partnerlinks in process
	 * 
	 * @param partnerLinksUsage
	 */
	void createListOfPartnerLinks(PartnerLinksUsage partnerLinksUsage);

	/**
	 * find uses for partnerlinks in the process
	 * 
	 * @param partnerLinksUsage
	 */
	void findUsesForPartnerLink(PartnerLinksUsage partnerLinksUsage);

	/**
	 * (c) Copyright Tomecode.com, 2010 -2011. All rights reserved.
	 * 
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public final static class PartnerLinksUsage {

		private final Hashtable<PartnerLink, List<Activity>> partnerLinksTable;

		public PartnerLinksUsage() {
			this.partnerLinksTable = new Hashtable<PartnerLink, List<Activity>>();
		}

		public final void addPartnerLink(PartnerLink partnerLink) {
			if (!partnerLinksTable.containsKey(partnerLink)) {
				partnerLinksTable.put(partnerLink, new ArrayList<Activity>());
			}
		}

		public final void addActivity(String partnerLinkName, Activity activity) {
			Enumeration<PartnerLink> e = partnerLinksTable.keys();
			while (e.hasMoreElements()) {
				PartnerLink partnerLink = e.nextElement();
				if (partnerLink.getName().equals(partnerLinkName)) {
					List<Activity> activities = partnerLinksTable.get(partnerLink);
					if (!activities.contains(activity)) {
						activities.add(activity);
					}
				}
			}
		}

		public final Hashtable<PartnerLink, List<Activity>> getPartnerLinksTable() {
			return this.partnerLinksTable;
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010 -2011. All rights reserved.
	 * 
	 * 
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public final class BpelVariableUsage {

		private final List<BpelVariable> bpelVariables;

		public BpelVariableUsage() {
			this.bpelVariables = new ArrayList<ToolBpelList.BpelVariable>();
		}

		public final List<BpelVariable> getBpelVariables() {
			return this.bpelVariables;
		}

		public final boolean containsVariable(String text, String variableName) {
			if (text != null) {
				if (text.contains("getVariableData('" + variableName + "'") || text.contains("$" + variableName) || text.equals(variableName)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * create new {@link OsbVariable} if not exists in list of
		 * {@link OsbVariable}
		 * 
		 * @param variable
		 * @return
		 */
		public final BpelVariable addVariable(Variable variable) {
			BpelVariable bpelVariable = findExistsOsbVariable(variable);
			if (bpelVariable != null) {
				return bpelVariable;
			}
			bpelVariable = new BpelVariable(variable);
			bpelVariables.add(bpelVariable);
			return bpelVariable;
		}

		private final BpelVariable findExistsOsbVariable(Variable variable) {
			for (BpelVariable bpelVariable : bpelVariables) {
				if (bpelVariable.getVariable().equals(variable) || bpelVariable.getVariable().getName().equals(variable.getName())) {
					return bpelVariable;
				}
			}
			return null;
		}

	}

	/**
	 * (c) Copyright Tomecode.com, 2010 -2011. All rights reserved.
	 * 
	 * 
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public final class BpelVariable {

		private final Variable variable;

		/**
		 * list of activities when is this variable
		 */
		private final List<Activity> usageInActivities;

		private BpelVariable(Variable variable) {
			this.usageInActivities = new ArrayList<Activity>();
			this.variable = variable;
		}

		public final Variable getVariable() {
			return this.variable;
		}

		/**
		 * @return the usageInActivities
		 */
		public final List<Activity> getUsageInActivities() {
			return usageInActivities;
		}

		public final void addUsage(Activity activity) {
			if (!usageInActivities.contains(activity)) {
				usageInActivities.add(activity);
			}
		}

	}

}
