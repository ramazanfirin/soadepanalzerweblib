package com.tomecode.soa.workspace;

import java.io.File;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Basic interface for all multi workspaces
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public interface MultiWorkspace extends ImageFace, ToolTip {

	/**
	 * Multi workspace name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Multi workspace folder
	 * 
	 * @return
	 */
	File getPath();

	/**
	 * multi workspace type
	 */
	WorkspaceType getWorkspaceType();

	boolean containsWorkspace(Workspace workspace);

	/**
	 * list of {@link Workspace}
	 * 
	 * @return
	 */
	List<Workspace> getWorkspaces();

	/**
	 * remove workspace
	 * 
	 * @param removeWorkspace
	 * @return
	 */
	boolean removeWorkspace(Workspace removeWorkspace);

}
