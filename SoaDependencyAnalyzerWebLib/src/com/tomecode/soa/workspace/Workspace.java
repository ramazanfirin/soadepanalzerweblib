package com.tomecode.soa.workspace;

import java.io.File;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Basic interface for all workspaces
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public interface Workspace extends ImageFace, ToolTip {

	/**
	 * workspace name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * workspace folder
	 * 
	 * @return
	 */
	File getFile();

	/**
	 * workspace type
	 * 
	 * @return
	 */
	WorkspaceType getWorkspaceType();

	/**
	 * list of {@link Project}
	 * 
	 * @return
	 */
	List<Project> getProjects();

	/**
	 * parent {@link MultiWorkspace}
	 * 
	 * @return
	 */
	MultiWorkspace getMultiWorkspace();

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Supported workspace type
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public enum WorkspaceType {
		ORACLE_1OG("Oracle SOA Suite 10g"), ORACLE_11G("Oracle SOA Suite 11g"), OPEN_ESB("Open ESB"), WLI_BASE("WLI BASE"),ORACLE_SERVICE_BUS_10G_11G("Oracle Service Bus 10g/11g");

		private final String title;

		WorkspaceType(String titile) {
			this.title = titile;
		}

		public final String toString() {
			return title;
		}
	}

}
