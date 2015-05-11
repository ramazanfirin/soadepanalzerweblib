package com.tomecode.soa.beawli.workspace;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
//import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.graphics.Image;

@PropertyGroupView(title="Multi-Workspace...", type="Wli Base WorkSpace")
public final class WliBaseMultiWorkspace implements MultiWorkspace

{
	  //@PropertyViewData(title="Name")
	  private String name;

	  //@PropertyViewData(title="Path")
	  private File file;
	  private final List<Workspace> workspaces;

	  public WliBaseMultiWorkspace(String name, File file)
	  {
	    this.workspaces = new ArrayList();
	    this.name = name;
	    this.file = file;
	  }

	  public final List<Workspace> getWorkspaces() {
	    return this.workspaces;
	  }

	  public final File getPath()
	  {
	    return this.file;
	  }

	  public final String getName()
	  {
	    return this.name;
	  }

	  public Workspace.WorkspaceType getWorkspaceType()
	  {
	    return Workspace.WorkspaceType.WLI_BASE;
	  }

	  public final void addWorkspace(WliBaseWorkspace workspace) {
	    workspace.setMultiWorkspace(this);
	    this.workspaces.add(workspace);
	  }

	  public final String toString() {
	    return this.name;
	  }

	  public final boolean removeWorkspace(Workspace workspace) {
	    for (int i = 0; i <= this.workspaces.size() - 1; i++) {
	      if (((Workspace)this.workspaces.get(i)).equals(workspace)) {
	        this.workspaces.remove(i);
	        return true;
	      }
	    }
	    return false;
	  }

	  public final boolean containsWorkspace(Workspace workspace)
	  {
	    for (Workspace oraSB10gWorkspace : this.workspaces) {
	      if (oraSB10gWorkspace.equals(workspace)) {
	        return true;
	      }
	    }
	    return false;
	  }

	  public final Image getImage(boolean small)
	  {
	    return ImageFactory.MULTI_WORKSPACE;
	  }

	  public final String getToolTip()
	  {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Type: \t\t").append(getType());
	    sb.append("\nName:\t\t").append(this.name);
	    sb.append("\nPath: \t\t").append(this.file != null ? this.file : "");
	    return sb.toString();
	  }

	  public String getType()
	  {
	    return "Oracle Service Bus 10g/11g Multi-Workspace";
	  }
  
}