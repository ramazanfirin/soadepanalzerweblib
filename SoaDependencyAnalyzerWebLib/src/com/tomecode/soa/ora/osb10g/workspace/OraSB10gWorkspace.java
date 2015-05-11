package com.tomecode.soa.ora.osb10g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * OSB 10g workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Workspace...", type = "Oracle Service Bus 10g/11g", parentMethod = "getMultiWorkspace")
public final class OraSB10gWorkspace implements Workspace {

	/**
	 * workspace name
	 */
	//@PropertyViewData(title = "Name")
	private String name;
	/**
	 * workspace file
	 */
	//@PropertyViewData(title = "Path")
	private File file;

	/**
	 * parent {@link MultiWorkspace}
	 */
	private MultiWorkspace parent;

	/**
	 * list of {@link OraSB10gProject}
	 */
	private final List<Project> projects;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param file
	 *            workspace file
	 */
	public OraSB10gWorkspace(String name, File file) {
		this.projects = new ArrayList<Project>();
		this.name = name;
		this.file = file;
	}

	@Override
	public final File getFile() {
		return file;
	}

	@Override
	public final MultiWorkspace getMultiWorkspace() {
		return parent;
	}

	public final void setMultiWorkspace(OraSB10gMultiWorkspace multiWorkspace) {
		this.parent = multiWorkspace;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final WorkspaceType getWorkspaceType() {
		return WorkspaceType.ORACLE_SERVICE_BUS_10G_11G;
	}

	public final void addProject(OraSB10gProject project) {
		project.setWorkspace(this);
		this.projects.add(project);
	}

	public final List<Project> getProjects() {
		return this.projects;
	}

	public final String toString() {
		return name;
	}

	public final Image getImage(boolean small) {
		return ImageFactory.WORKSPACE;
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
		return "Oracle Service Bus 10g/11g Workspace";
	}

}
