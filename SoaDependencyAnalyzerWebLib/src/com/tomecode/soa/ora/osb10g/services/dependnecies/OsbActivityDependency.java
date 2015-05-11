package com.tomecode.soa.ora.osb10g.services.dependnecies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.activity.WsCallout;
import com.tomecode.soa.ora.osb10g.services.OSBService;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Contains all dependencies for activity
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OsbActivityDependency implements ImageFace {

	private OSBService parentService;

	/**
	 * 
	 */
	private OsbActivity activity;

	/**
	 * list of services
	 */
	private List<OSBService> services;

	/**
	 * list of dependencies
	 */
	private final List<OsbActivityDependency> activityDependencies;

	private OsbActivityDependency() {
		this.activityDependencies = new ArrayList<OsbActivityDependency>();
		this.services = new ArrayList<OSBService>();
	}

	public OsbActivityDependency(OSBService service) {
		this();
		this.parentService = service;
	}

	public OsbActivityDependency(OsbActivity targetActivity, OSBService targetService) {
		this();
		this.activity = targetActivity;
		this.services.add(targetService);
	}

	/**
	 * @return the parentService
	 */
	public final OSBService getParentService() {
		return parentService;
	}

	/**
	 * @return the osbActivity
	 */
	public final OsbActivity getActivity() {
		return activity;
	}

	/**
	 * @return the service
	 */
	public final List<OSBService> getServices() {
		return services;
	}

	public final void setTargetService(OSBService targetService) {
		if (!services.contains(targetService)) {
			this.services.add(targetService);
		}
	}

	public final String getServiceName() {
		if (!services.isEmpty()) {
			return services.get(0).toString();
		}
		return null;
	}

	public final void addDependecy(OsbActivity targetActivity, OSBService targetService) {
		if (targetActivity instanceof WsCallout) {
			String.class.toString();
		}

		if (!exists(targetActivity, targetService)) {
			this.activityDependencies.add(new OsbActivityDependency(targetActivity, targetService));
		}
	}

	private final boolean exists(OsbActivity targetActivity, OSBService targetService) {
		for (OsbActivityDependency dependency : activityDependencies) {
			if (dependency.getActivity().equals(targetActivity) && services.contains(targetService)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the activityDependencies
	 */
	public final List<OsbActivityDependency> getActivityDependencies() {
		return activityDependencies;
	}

	public final String toString() {
		return activity.toString();
	}

	public final Image getImage(boolean small) {
		return activity.getImage();
	}

	@Override
	public final String getToolTip() {
		return activity.getToolTip();
	}

}
