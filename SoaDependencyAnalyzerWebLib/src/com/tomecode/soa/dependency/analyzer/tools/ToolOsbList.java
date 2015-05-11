package com.tomecode.soa.dependency.analyzer.tools;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.ora.osb10g.activity.TransportHeaders;
import com.tomecode.soa.ora.osb10g.services.Proxy;

/**
 * (c) Copyright Tomecode.com, 2010,2011. All rights reserved.
 * 
 * Tools/utilities for OSB proxy services, for example: find all variables, find
 * all headers, etc , etc.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public interface ToolOsbList {

	/**
	 * create list of all variables and activities (only activity when variable
	 * is created) in proxy service
	 * 
	 * @param osbVariableList
	 */
	void createListOfOsbVariables(OsbVariableList osbVariableList);

	/**
	 * find uses for variables in the service
	 * 
	 * @param osbVariableList
	 */
	void findUsesForOsbVariables(OsbVariableList osbVariableList);

	/**
	 * find all headers
	 * 
	 * @param HeaderInActivity
	 */
	void findOsbHeaders(HeaderInActivity osbHeader);

	/**
	 * (c) Copyright Tomecode.com, 2010,2011. All rights reserved.
	 * 
	 * management class for {@link HeaderInActivity}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public static final class OsbHeaderList {

		private final List<HeaderInActivity> osbHeaders;

		public OsbHeaderList() {
			this.osbHeaders = new ArrayList<ToolOsbList.HeaderInActivity>();
		}

		public final List<HeaderInActivity> getOsbHeaders() {
			return osbHeaders;
		}

		/**
		 * if not found {@link HeaderInActivity} by {@link Proxy} then create
		 * new {@link HeaderInActivity}
		 * 
		 * @param proxy
		 * @return
		 */
		public final HeaderInActivity addProxy(Proxy proxy) {
			for (HeaderInActivity osbHeader : osbHeaders) {
				if (osbHeader.getProxy().equals(proxy)) {
					return osbHeader;
				}
			}
			HeaderInActivity osbHeader = new HeaderInActivity(proxy);
			this.osbHeaders.add(osbHeader);
			return osbHeader;
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010,2011. All rights reserved.
	 * 
	 * 
	 * contains all {@link TransportHeaders} in {@link Proxy}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public static final class HeaderInActivity {

		/**
		 * {@link Proxy}
		 */
		private final Proxy proxy;

		/**
		 * all {@link TransportHeaders} in {@link #proxy}
		 */
		private final List<TransportHeaders> listHeaders;

		/**
		 * Constructor
		 * 
		 * @param proxy
		 */
		public HeaderInActivity(Proxy proxy) {
			this.listHeaders = new ArrayList<TransportHeaders>();
			this.proxy = proxy;
		}

		public final List<TransportHeaders> getTransportHeaders() {
			return listHeaders;
		}

		public final void addActivity(TransportHeaders headers) {
			if (!listHeaders.contains(headers)) {
				this.listHeaders.add(headers);
			}
		}

		public final Proxy getProxy() {
			return proxy;
		}

	}

	/**
	 * (c) Copyright Tomecode.com, 2010,2011. All rights reserved.
	 * 
	 * management for {@link OsbVariable}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public final static class OsbVariableList {

		private final List<OsbVariable> osbVariables;

		public OsbVariableList() {
			this.osbVariables = new ArrayList<ToolOsbList.OsbVariable>();
		}

		/**
		 * create new {@link OsbVariable} if not exists in list of
		 * {@link OsbVariable}
		 * 
		 * @param variable
		 * @return
		 */
		public final OsbVariable addVariable(String variable, Activity activity) {
			OsbVariable osbVariable = findExistsOsbVariable(variable);
			if (osbVariable != null) {
				return osbVariable;
			}
			osbVariable = new OsbVariable(variable, activity);
			osbVariables.add(osbVariable);
			return osbVariable;
		}

		public final List<OsbVariable> getOsbVariables() {
			return osbVariables;
		}

		private final OsbVariable findExistsOsbVariable(String variable) {
			for (OsbVariable osbVariable : osbVariables) {
				if (osbVariable.getVariable().equals(variable)) {
					return osbVariable;
				}
			}

			return null;
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010,2011. All rights reserved.
	 * 
	 * helper class for find usage/list of variables in osb proxy services
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public final static class OsbVariable {

		private final String variable;

		private final Activity containsActivity;
		/**
		 * list of activities when is this variable
		 */
		private final List<Activity> usageInActivities;

		public OsbVariable(String variable, Activity containsActivity) {
			this.usageInActivities = new ArrayList<Activity>();
			this.variable = variable;
			this.containsActivity = containsActivity;
		}

		/**
		 * this activity contains variable
		 * 
		 * @return
		 */
		public final Activity getActivity() {
			return this.containsActivity;
		}

		public final String getVariable() {
			return variable;
		}

		public final List<Activity> getUsagesActivites() {
			return this.usageInActivities;
		}

		public final void addUsageActivty(Activity activity) {
			if (!usageInActivities.contains(activity)) {
				usageInActivities.add(activity);
			}
		}
	}

}
