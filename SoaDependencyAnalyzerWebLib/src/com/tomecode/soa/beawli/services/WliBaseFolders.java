package com.tomecode.soa.beawli.services;

import com.tomecode.soa.beawli.project.WliBaseProject;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class WliBaseFolders extends WliBaseFolder
{
  private final List<WliBaseService> allServices;

  public WliBaseFolders(WliBaseProject project, File fileSystemPath, String path, String name)
  {
    super(project, fileSystemPath, path, name);
    this.allServices = new ArrayList();
  }

  public final WliBaseFolder findAndCreate(WliBaseProject project, String path, File jarPath)
  {
    String[] paths = path.split("/");
    return findFolderAndCreate(project, 0, paths, jarPath);
  }

  public final void addToAllServices(WliBaseService service) {
    this.allServices.add(service);
  }

  public final List<WliBaseService> getAllServices()
  {
    return this.allServices;
  }
}