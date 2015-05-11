package com.tomecode.soa.ora.suite10g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle 10g SOA SUITE workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "Workspace...", type = "Oracle SOA Suite 10g", parentMethod = "getMultiWorkspace")
public final class Ora10gWorkspace implements Workspace {

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

	private Ora10gMultiWorkspace multiWorkspace;

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param file
	 *            workspace file (.jws)
	 */
	public Ora10gWorkspace(String name, File file) {
		this.projects = new ArrayList<Project>();
		this.name = name;
		this.file = file;
	}

	@Override
	//@PropertyViewData(title = "Path")
	public final File getFile() {
		if (this.file.getParentFile() == null) {
			return file;
		}
		return this.file.getParentFile();
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
	public final void addProject(Project project) {
		projects.add(project);
	}

	public final String toString() {
		return name;
	}

	@Override
	public final WorkspaceType getWorkspaceType() {
		return WorkspaceType.ORACLE_1OG;
	}

	public final void setMultiWorkspace(Ora10gMultiWorkspace multiWorkspace) {
		this.multiWorkspace = multiWorkspace;
	}

	@Override
	public MultiWorkspace getMultiWorkspace() {
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
		return "Oracle SOA Suite 10g Workspace";
	}
}
