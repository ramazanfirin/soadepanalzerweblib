package com.tomecode.soa.ora.suite11g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite11g.project.Ora11gProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle 11g SOA SUITE workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "Workspace...", type = "Oracle SOA Suite 11g", parentMethod = "getMultiWorkspace")
public final class Ora11gWorkspace implements Workspace {

	/**
	 * workspace name
	 */
	//@PropertyViewData(title = "Name")
	private String name;

	/**
	 * workspace file
	 */
	private File file;

	/**
	 * list of workspace projects
	 */
	private final List<Project> projects;

	private Ora11gMultiWorkspace multiWorkspace;

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param file
	 *            workspace file (.jws)
	 */
	public Ora11gWorkspace(String name, File file) {
		this.projects = new ArrayList<Project>();
		this.name = name;
		this.file = file;
	}

	@Override
	//@PropertyViewData(title = "Path")
	public final File getFile() {
		return this.file;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	public final Object[] getChildren() {
		return projects.toArray();
	}

	public final boolean hasChildren() {
		return !projects.isEmpty();
	}

	public final List<Project> getProjects() {
		return projects;
	}

	/**
	 * add new {@link Project} and set parent
	 * 
	 * @param project
	 */
	public final void addProject(Ora11gProject project) {
		project.setWorkspace(this);
		projects.add(project);
	}

	public final String toString() {
		return name;
	}

	@Override
	public final WorkspaceType getWorkspaceType() {
		return WorkspaceType.ORACLE_11G;
	}

	public final void setMultiWorkspace(Ora11gMultiWorkspace multiWorkspace) {
		this.multiWorkspace = multiWorkspace;
	}

	@Override
	public final MultiWorkspace getMultiWorkspace() {
		return multiWorkspace;
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
		return "Oracle SOA Suite 11g Workspace";
	}

}
