package com.tomecode.soa.ora.suite10g.esb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList.EndpointListProject;
import com.tomecode.soa.ora.suite10g.esb.BasicEsbNode.EsbNodeType;
import com.tomecode.soa.ora.suite10g.esb.protocols.EsbAdapter;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Contains esbsvc,esbgrp, esb files in project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(title = "ESB Project...", type = "Oracle SOA Suite 10g", parentMethod = "getWorkpsace")
public final class Ora10gEsbProject implements Project {

	private static final long serialVersionUID = 1517251079778988223L;

	private boolean folder;

	//@PropertyViewData(title = "Name")
	private String name;

	//@PropertyViewData(title = "Path")
	private File file;
	/**
	 * if true then project is in *.jws
	 */
	private boolean isInJws;

	private final List<BasicEsbNode> basicEsbNodes;
	/**
	 * list of esbsvc
	 */
	private final List<Project> projectDependecies;

	/**
	 * parent workspace
	 */
	private Ora10gWorkspace workspace;

	private Ora10gEsbProject() {
		basicEsbNodes = new ArrayList<BasicEsbNode>();
		projectDependecies = new ArrayList<Project>();
	}

	/**
	 * Constructor
	 * 
	 * @param projectFile
	 * @param isFolder
	 */
	public Ora10gEsbProject(File projectFile, boolean isFolder) {
		this();
		this.file = projectFile;
		this.folder = isFolder;
	}

	public final List<BasicEsbNode> getBasicEsbNodes() {
		return basicEsbNodes;
	}

	public final void addBasicEsbNode(BasicEsbNode basicEsbNode) {
		basicEsbNodes.add(basicEsbNode);
	}

	public final String toString() {
		return (file != null) ? file.getName() : name;
	}

	public final List<Project> getProjectDependecies() {
		return projectDependecies;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * find {@link Ora10gEsbProject} by qName and service URL
	 * 
	 * @param qName
	 * @param serviceURL
	 * @return if return null then not found
	 */
	public final Ora10gEsbProject findEsbProjectByQname(String qName, URL serviceURL) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getQname().equals(qName)) {
				return this;
			} else if (basicEsbNode.getNodeType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				if (esbSys.getQname().equals(qName)) {
					return this;
				} else {
					if (esbSys.findEsbProjectByQname(qName, serviceURL) != null) {
						return this;
					}
				}
			} else if (basicEsbNode.getNodeType() == EsbNodeType.ESBGRP) {
				EsbGrp esbGrp = (EsbGrp) basicEsbNode;
				if (esbGrp.getQname().equals(qName)) {
					return this;
				} else {
					if (esbGrp.findEsbProjectByQname(qName, serviceURL) != null) {
						return this;
					}
				}
			} else if (basicEsbNode.getNodeType() == EsbNodeType.ESBSYS) {
				if (((EsbSys) basicEsbNode).findEsbProjectByQname(qName, serviceURL) != null) {
					return this;
				}
			}
		}

		return null;
	}

	public final EsbSys findEsbSysByQname(String qname) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getNodeType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				if (esbSys.getQname().equals(qname)) {
					return esbSys;
				}
			}
		}

		return null;
	}

	public final EsbSvc findEsbSvcByQname(String qname) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getNodeType() == EsbNodeType.ESBSVC) {
				EsbSvc esbSvc = (EsbSvc) basicEsbNode.get();
				if (esbSvc.getQname().equals(qname)) {
					return esbSvc;
				}
			} else if (basicEsbNode.getNodeType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode;
				EsbSvc esbSvc = esbSys.findEsbSvcByQname(qname);
				if (esbSvc != null) {
					return esbSvc;
				}
			} else if (basicEsbNode.getNodeType() == EsbNodeType.ESBGRP) {
				EsbGrp esbGrp = (EsbGrp) basicEsbNode;
				EsbSvc esbSvc = esbGrp.findEsbSvcByQname(qname);
				if (esbSvc != null) {
					return esbSvc;
				}
			}
		}
		return null;
	}

	public final EsbGrp findEsbGrpByQname(String qname) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getNodeType() == EsbNodeType.ESBGRP) {
				if (basicEsbNode.getQname().equals(qname)) {
					return (EsbGrp) basicEsbNode.get();
				} else {
					EsbGrp esbGrp = (EsbGrp) basicEsbNode.get();
					esbGrp = esbGrp.findEsbGrpByQname(qname);
					if (esbGrp != null) {
						return esbGrp;
					}
				}
			} else if (basicEsbNode.getNodeType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				EsbGrp esbGrp = esbSys.findEsbGrpByQname(qname);
				if (esbGrp != null) {
					return esbGrp;
				}
			}
		}

		return null;
	}

	public final void addDependency(Project project) {
		if (!projectDependecies.contains(project)) {
			projectDependecies.add(project);
		}
	}

	public final List<EsbSvc> getAllEsbSvc() {
		List<EsbSvc> esbSvcs = new ArrayList<EsbSvc>();
		findAllEsbSvc(esbSvcs);
		return esbSvcs;
	}

	private final void findAllEsbSvc(List<EsbSvc> esbSvcs) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getNodeType() == EsbNodeType.ESBGRP) {
				EsbGrp esbGrp = (EsbGrp) basicEsbNode.get();
				esbGrp.findAllEsbSvc(esbSvcs);
			} else if (basicEsbNode.getNodeType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				esbSys.findAllEsbSvc(esbSvcs);
			} else if (basicEsbNode.getNodeType() == EsbNodeType.ESBSVC) {
				esbSvcs.add((EsbSvc) basicEsbNode.get());
			}
		}

	}

	public final boolean isInJws() {
		return isInJws;
	}

	public final void setInJws(boolean isInJws) {
		this.isInJws = isInJws;
	}

	@Override
	public final File getFile() {
		return file;
	}

	@Override
	public final ProjectType getProjectType() {
		return ProjectType.ORACLE10G_ESB;
	}

	public final void setWorkspace(Ora10gWorkspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final Workspace getWorkpsace() {
		return workspace;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_10G_ESB;
	}

	/**
	 * @return the folder
	 */
	public final boolean isFolder() {
		return folder;
	}

	/**
	 * @param folder
	 *            the folder to set
	 */
	public final void setFolder(boolean folder) {
		this.folder = folder;
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
		return "Oracle SOA Suite 10g - ESB Project";
	}

	@Override
	public final void calculateEndpointList(EndpointList endpointList) {
		List<EsbSvc> esbSvcs = getAllEsbSvc();
		EndpointListProject endpointListProject = endpointList.findProject(this);
		for (EsbSvc esbSvc : esbSvcs) {
			EsbAdapter adapter = (EsbAdapter) esbSvc.getEndpoint();
			endpointListProject.addEndpointListService(esbSvc, esbSvc.getServiceSubType().toString(), adapter.getName());
		}
	}
}
