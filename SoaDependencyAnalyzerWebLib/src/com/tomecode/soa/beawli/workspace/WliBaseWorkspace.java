package com.tomecode.soa.beawli.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.beawli.project.WliBaseProject;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
//import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

@PropertyGroupView(title="Workspace...", type="Wli Base WorkSpace", parentMethod="getMultiWorkspace")
public final class WliBaseWorkspace implements Workspace
{

	//@PropertyViewData(title="Name")
	  private String name;

	  //@PropertyViewData(title="Path")
	  private File file;
	  private MultiWorkspace parent;
	  private final List<Project> projects;

	  public WliBaseWorkspace(String name, File file)
	  {
	    this.projects = new ArrayList();
	    this.name = name;
	    this.file = file;
	  }

	  public final File getFile()
	  {
	    return this.file;
	  }

	  public final MultiWorkspace getMultiWorkspace()
	  {
	    return this.parent;
	  }

	  public final void setMultiWorkspace(WliBaseMultiWorkspace multiWorkspace) {
	    this.parent = multiWorkspace;
	  }

	  public final String getName()
	  {
	    return this.name;
	  }

	  public final Workspace.WorkspaceType getWorkspaceType()
	  {
	    return Workspace.WorkspaceType.ORACLE_SERVICE_BUS_10G_11G;
	  }

	  public final void addProject(WliBaseProject project) {
	    project.setWorkspace(this);
	    this.projects.add(project);
	  }

	  public final List<Project> getProjects() {
	    return this.projects;
	  }

	  public final String toString() {
	    return this.name;
	  }

	  public final Image getImage(boolean small) {
	    return ImageFactory.WORKSPACE;
	  }

	  public final String getToolTip()
	  {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Type: \t\t").append(getType());
	    sb.append("\nName:\t\t").append(this.name);
	    sb.append("\nPath: \t\t").append(this.file != null ? this.file : "");
	    return sb.toString();
	  }

	  public final String getType()
	  {
	    return "Oracle Service Bus 10g/11g Workspace";
	  }

 
}