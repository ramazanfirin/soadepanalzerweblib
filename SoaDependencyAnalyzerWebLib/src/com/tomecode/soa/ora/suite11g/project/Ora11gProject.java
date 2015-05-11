package com.tomecode.soa.ora.suite11g.project;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.ora.suite11g.project.sca.CompositeService;
import com.tomecode.soa.ora.suite11g.workspace.Ora11gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle 11g project (SOA)
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "SOA Project...", type = "Oracle SOA Suite 11g", parentMethod = "getWorkpsace")
public final class Ora11gProject implements Project {

	/**
	 * parent {@link Ora11gWorkspace}
	 */
	private Ora11gWorkspace workspace;

	/**
	 * composite in project
	 */
	private Ora11gComposite composite;
	/**
	 * project name;
	 */
	private String name;
	/**
	 * project folder
	 */
	private File file;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            project name
	 * @param jprFile
	 *            project file
	 */
	public Ora11gProject(String name, File jprFile) {
		int index = name.indexOf(".");
		if (index != -1) {
			this.name = name.substring(0, index);
		} else {
			this.name = name;
		}
		this.file = jprFile;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_11G_PROJECT;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: 		").append(getType());
		sb.append("\nName:		").append(getName());
		sb.append("\nPath: 		").append(file != null ? file : "");
		return sb.toString();
	}

	@Override
	public final String getType() {
		return "Oracle SOA Suite 11g - SOA Project";
	}

	//@PropertyViewData(title = "Name")
	@Override
	public final String getName() {
		return name;
	}

	//@PropertyViewData(title = "Path")
	@Override
	public final File getFile() {
		return file;
	}

	@Override
	public final ProjectType getProjectType() {
		return ProjectType.ORACLE11G_SCA;
	}

	@Override
	public final Workspace getWorkpsace() {
		return workspace;
	}

	@Override
	public final boolean isFolder() {
		return true;
	}

	public final String toString() {
		return name;
	}

	/**
	 * adding parent {@link Ora11gWorkspace}
	 * 
	 * @param ora11gWorkspace
	 */
	public final void setWorkspace(Ora11gWorkspace workspace) {
		this.workspace = workspace;
	}

	public final void setCompoiste(Ora11gComposite composite) {
		if (composite != null) {
			composite.setProject(this);
		}
		this.composite = composite;
	}

	public final Ora11gComposite getComposite() {
		return composite;
	}

	@Override
	public final void calculateEndpointList(EndpointList endpointLists) {
		if (composite != null) {
			for (CompositeService compositeService : composite.getCompositeServices()) {

			}
		}
	}

}
