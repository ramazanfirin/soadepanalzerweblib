package com.tomecode.soa.ora.suite11g.workspace;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Contains one or more {@link Ora10gWorkspace}
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "Multi-Workspace...", type = "Oracle SOA Suite 11g")
public final class Ora11gMultiWorkspace implements MultiWorkspace, Serializable {

	/**
	 * workspace name
	 */
	//@PropertyViewData(title = "Name")
	private String name;;
	/**
	 * workspace folder
	 */
	//@PropertyViewData(title = "Path")
	private File file;

	/**
	 * list of {@link Ora10gWorkspace}
	 */
	private List<Workspace> workspaces;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name (user enter)
	 * @param file
	 *            workspace folder( user enter)
	 */
	public Ora11gMultiWorkspace(String name, File file) {
		this.workspaces = new ArrayList<Workspace>();
		this.name = name;
		this.file = file;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public final File getPath() {
		return this.file;
	}

	public final String toString() {
		return getName();
	}

	public final boolean hasChildren() {
		return !this.workspaces.isEmpty();
	}

	public final Object[] getChildren() {
		return workspaces.toArray();
	}

	public final List<Workspace> getWorkspaces() {
		return workspaces;
	}

	@Override
	public final WorkspaceType getWorkspaceType() {
		return WorkspaceType.ORACLE_11G;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.MULTI_WORKSPACE;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: 		").append(getType());
		sb.append("\nName:		").append(name);
		sb.append("\nPath: 		").append(file != null ? file : "");
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Oracle SOA Suite 11g Multi-Workspace";
	}

	@Override
	public final boolean containsWorkspace(Workspace workspace) {
		return workspaces.contains(workspace);
	}

	@Override
	public final boolean removeWorkspace(Workspace workspace) {
		for (int i = 0; i <= workspaces.size() - 1; i++) {
			if (workspaces.get(i).equals(workspace)) {
				workspaces.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * adding new {@link Ora11gWorkspace} to list of {@link #workspaces}
	 * 
	 * @param workspace
	 */
	public final void addWorkspace(Ora11gWorkspace workspace) {
		workspace.setMultiWorkspace(this);
		this.workspaces.add(workspace);
	}
}
