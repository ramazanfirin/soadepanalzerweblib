package com.tomecode.soa.beawli.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.beawli.project.WliBaseProject;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolder;

public class WliBaseFolder 
  implements ImageFace
{
  protected final List<WliBaseFolder> folders;
  protected final List<WliBaseService> services;
  private File fileSystemPath;
  private String path;
  private String name;
  private WliBaseFolder parent;
  private WliBaseProject project;

  public WliBaseFolder(WliBaseProject project, File fileSystemPath, String path, String name)
  {

    this.project = project;
    this.services = new ArrayList();
    this.folders = new ArrayList();
    this.fileSystemPath = fileSystemPath;
    this.path = path;
    this.name = name;
  }

  public final WliBaseFolder getParent()
  {
    return this.parent;
  }

  public final void setParent(WliBaseFolder parent)
  {
    this.parent = parent;
  }

  public final void addFolder(WliBaseFolder folder)
  {
    folder.setParent(this);
    this.folders.add(folder);
  }

  public final List<WliBaseFolder> getFolders()
  {
    return this.folders;
  }

  public final void addService(WliBaseService service)
  {
    this.services.add(service);
    //this.project.addService(service);
  }

  public final List<WliBaseService> getServices()
  {
    return this.services;
  }

  public final File getFileSystemPath()
  {
    return this.fileSystemPath;
  }

  public final String getPath()
  {
    return this.path;
  }

  public final String getName()
  {
    return this.name;
  }

  public final String toString() {
    return this.name;
  }

  protected final WliBaseFolder findFolderAndCreate(WliBaseProject project, int index, String[] paths, File jarPath)
  {
    String targetFolderName = paths[index];
    if (this.folders.isEmpty()) {
      String realPath = makePath(index, paths);
      addFolder(new WliBaseFolder(project, new File(jarPath + "/$/" + realPath), realPath, targetFolderName));
    }

    for (WliBaseFolder folder : this.folders) {
      if (folder.getName().equals(targetFolderName)) {
        if (index >= paths.length - 1) {
          return folder;
        }
        index++;
        return folder.findFolderAndCreate(project, index, paths, jarPath);
      }
    }

    String realPath = makePath(index, paths);
    WliBaseFolder newFolder = new WliBaseFolder(project, new File(jarPath + "/$/" + realPath), realPath, targetFolderName);
    addFolder(newFolder);

    index++;
    if (index <= paths.length - 1) {
      return newFolder.findFolderAndCreate(project, index, paths, jarPath);
    }

    return newFolder;
  }

  private static final String makePath(int index, String[] paths)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= index; i++) {
      sb.append(paths[i]);
      if (i++ < index) {
        sb.append("/");
        i--;
      }
    }
    return sb.toString();
  }

  public final Object[] getAll() {
    Object[] target = new Object[this.folders.size() + this.services.size()];

    System.arraycopy(this.folders.toArray(), 0, target, 0, this.folders.size());
    System.arraycopy(this.services.toArray(), 0, target, this.folders.size(), this.services.size());

    return target;
  }

  public final Image getImage(boolean small)
  {
    return ImageFactory.OSB_10G_OSB_FOLDER;
  }

  public final String getToolTip()
  {
    return "Folder: " + this.name;
  }
}