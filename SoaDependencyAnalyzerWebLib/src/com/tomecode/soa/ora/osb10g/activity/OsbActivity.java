package com.tomecode.soa.ora.osb10g.activity;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.ErrorHandlers;
import com.tomecode.soa.ora.osb10g.services.SplitJoin;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Basic OSB 10g activity
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(titleMethod = "getType")
public abstract class OsbActivity extends Activity {

	protected String errorHandler;

	public OsbActivity() {
		super();
	}

	public OsbActivity(String name, String errorHandler) {
		super(name);
		this.errorHandler = errorHandler;
	}

	protected final void setParent(Activity parent) {
		super.setParent(parent);
	}

	/**
	 * @return the errorHandler
	 */
	public final String getErrorHandlerName() {
		return errorHandler;
	}

	/**
	 * error handlers in {@link SplitJoin}
	 * 
	 * @return
	 */
	public final Activity getErroHandlers() {
		for (Activity activity : activities) {
			if (activity instanceof ErrorHandlers) {
				return activity;
			}
		}
		return null;
	}

	/**
	 * set new error handler
	 * 
	 * @param pipelineError
	 */
	public final void setErrorHandler(PipelineError pipelineError) {
		pipelineError.setParent(this);
		activities.add(0, pipelineError);
	}

}
