package com.tomecode.soa.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.tools.ToolBpelList;
import com.tomecode.soa.dependency.analyzer.tools.ToolOsbList;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Basic activity for BPEL/Proxy Service/etc.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(titleMethod = "getType")
public abstract class Activity implements ImageFace, ToolTip, ToolOsbList, ToolBpelList , Serializable{

	private static final long serialVersionUID = -6772380545433052884L;

	protected List<Activity> activities;

	/**
	 * parent activity
	 */
	private Activity parent;

	//@PropertyViewData(title = "Name", showWhenIsEmpty = false)
	protected String name;

	/**
	 * Constructor
	 */
	public Activity() {
		activities = new ArrayList<Activity>();
	}

	public Activity(String name) {
		this();
		this.name = name;
	}

	/**
	 * add activity and set parent
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activity.setParent(this);
		activities.add(activity);
	}

	protected void setParent(Activity parent) {
		this.parent = parent;
	}

	public final Activity getParent() {
		return parent;
	}

	public final String getName() {
		return name;
	}

	public String toString() {
		if (name != null && name.trim().length() != 0) {
			return getType() + " - " + name;
		}
		return getType();
	}

	/**
	 * remove ':' from string
	 * 
	 * @param str
	 * @return
	 */
	protected final String format(String str) {
		if (str != null) {
			int index = str.indexOf(":");
			if (index != -1) {
				return str.substring(index + 1, str.length());
			}
		}
		return str;
	}

	// /**
	// * find usage for {@link Variable}
	// *
	// * @param findUsageVariableResult
	// */
	// public final void findUsage(FindUsageVariableResult
	// findUsageVariableResult) {
	// for (Activity activity : this.childs) {
	// if (activity.getActivtyType() != null) {
	// if (activity.getActivtyType().isContainsVariable()) {
	// findVariableInActivty(findUsageVariableResult, activity);
	// }
	// }
	//
	// activity.findVariable(findUsageVariableResult);
	// activity.findUsage(findUsageVariableResult);
	// }
	// }
	//
	// public void findVariable(FindUsageVariableResult findUsageVariableResult)
	// {
	//
	// }
	//
	// public void findPartnerLink(FindUsagePartnerLinkResult
	// findUsagePartnerLinkResult) {
	//
	// }
	//
	// /**
	// * find usage for {@link PartnerLink}
	// *
	// * @param findUsagePartnerLinkResult
	// */
	// public final void findUsage(FindUsagePartnerLinkResult
	// findUsagePartnerLinkResult) {
	// for (Activity activity : childs) {
	//
	// activity.findPartnerLink(findUsagePartnerLinkResult);
	// activity.findUsage(findUsagePartnerLinkResult);
	// }
	// }
	//
	// /**
	// * find usage for variable in bpel actvities
	// *
	// * @param findUsageVariableResult
	// * @param activity
	// */
	// private final void findVariableInActivty(FindUsageVariableResult
	// findUsageVariableResult, Activity activity) {
	// if (activity.getActivtyType() == ActivityType.RECEIVE) {
	// if (findUsageVariableResult.getVariable().getName().equals(((Receive)
	// activity).getVariable())) {
	// findUsageVariableResult.addUsage(activity);
	// }
	// } else if (activity.getActivtyType() == ActivityType.INVOKE) {
	// Invoke invoke = (Invoke) activity;
	// if
	// (findUsageVariableResult.getVariable().getName().equals(invoke.getInputVariable())
	// ||
	// findUsageVariableResult.getVariable().getName().equals(invoke.getOutputVariable()))
	// {
	// findUsageVariableResult.addUsage(activity);
	// }
	// } else if (activity.getActivtyType() == ActivityType.REPLY) {
	// if (findUsageVariableResult.getVariable().getName().equals(((Reply)
	// activity).getVariable())) {
	// findUsageVariableResult.addUsage(activity);
	// }
	// }
	// }

	public final List<Activity> getActivities() {
		return activities;
	}

	public boolean compare(Activity activity) {
		return toString().equals(activity.toString());
	}

	public abstract Image getImage();

	@Override
	public Image getImage(boolean small) {
		return getImage();
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

	@Override
	public void createListOfPartnerLinks(PartnerLinksUsage partnerLinksUsage) {
		for (Activity activity : activities) {
			activity.createListOfPartnerLinks(partnerLinksUsage);
		}
	}

	@Override
	public void findUsesForPartnerLink(PartnerLinksUsage partnerLinksUsage) {
		for (Activity activity : activities) {
			activity.findUsesForPartnerLink(partnerLinksUsage);
		}
	}

	@Override
	public void createListOfOsbVariables(OsbVariableList osbVariableList) {
		for (Activity activity : activities) {
			activity.createListOfOsbVariables(osbVariableList);
		}
	}

	/**
	 * find all headers in OSB activities
	 * 
	 * @param osbHeader
	 */
	@Override
	public void findOsbHeaders(HeaderInActivity osbHeader) {
		for (Activity activity : activities) {
			activity.findOsbHeaders(osbHeader);
		}
	}

	/**
	 * find the activities who use OSB variables
	 */
	@Override
	public void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (Activity activity : activities) {
			activity.findUsesForOsbVariables(osbVariableList);
		}
	}

	/**
	 * create list of variables in BPEL process
	 */
	@Override
	public void createListOfBpelVariables(BpelVariableUsage bpelVariableUsage) {
		for (Activity activity : activities) {
			activity.createListOfBpelVariables(bpelVariableUsage);
		}
	}

	/**
	 * find the activities who use BPEL variables (after
	 * {@link #createListOfBpelVariables(com.tomecode.soa.dependency.analyzer.tools.ToolBpelList.BpelVariableUsage)}
	 * )
	 */
	@Override
	public void findUsesForBpelVariables(BpelVariableUsage variableUsage) {
		for (Activity activity : activities) {
			activity.findUsesForBpelVariables(variableUsage);
		}
	}
}
