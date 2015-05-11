package com.tomecode.soa.ora.osb10g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle Service Bus 10g MULTI workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "Multi-Workspace...", type="Oracle Service Bus 10g/11g")
public final class OraSB10gMultiWorkspace implements MultiWorkspace {

	/**
	 * workspace name
	 */
	//@PropertyViewData(title = "Name")
	private String name;

	/**
	 * workspace folder
	 */
	//@PropertyViewData(title = "Path")
	private File file;

	/**
	 * list of {@link Workspace}
	 */
	private final List<Workspace> workspaces;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param file
	 *            workspace folder
	 */
	public OraSB10gMultiWorkspace(String name, File file) {
		this.workspaces = new ArrayList<Workspace>();
		this.name = name;
		this.file = file;
	}

	public final List<Workspace> getWorkspaces() {
		return workspaces;
	}

	@Override
	public final File getPath() {
		return file;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public WorkspaceType getWorkspaceType() {
		return WorkspaceType.ORACLE_SERVICE_BUS_10G_11G;
	}

	public final void addWorkspace(OraSB10gWorkspace workspace) {
		workspace.setMultiWorkspace(this);
		this.workspaces.add(workspace);
	}

	public final String toString() {
		return name;
	}

	public final boolean removeWorkspace(Workspace workspace) {
		for (int i = 0; i <= workspaces.size() - 1; i++) {
			if (workspaces.get(i).equals(workspace)) {
				workspaces.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public final boolean containsWorkspace(Workspace workspace) {
		for (Workspace oraSB10gWorkspace : workspaces) {
			if (oraSB10gWorkspace.equals(workspace)) {
				return true;
			}
		}
		return false;
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
		return "Oracle Service Bus 10g/11g Multi-Workspace";
	}

}
