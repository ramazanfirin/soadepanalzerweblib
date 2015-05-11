package com.tomecode.soa.ora.osb10g.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.AddDataToExistsGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.dependency.analyzer.tools.ReferencedBy;
import com.tomecode.soa.dependency.analyzer.tools.ToolEndpointList;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointUNKNOWN;
import com.tomecode.soa.ora.osb10g.services.dependnecies.OsbActivityDependency;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Service - basic interface for all services in OSB 10g project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class OSBService extends ReferencedBy implements ImageFace, ToolEndpointList {

	/**
	 * service name
	 */
	//@PropertyViewData(title = "Name")
	protected String name;

	/**
	 * service file
	 */
	//@PropertyViewData(title = "Path")
	protected File file;

	//@PropertyViewData(title = "Description", showWhenIsEmpty = false)
	protected String description;
	/**
	 * service binding
	 */
	private Binding binding;

	protected String orginalName;

	/**
	 * parent project
	 */
	protected Project project;

	/**
	 * current folder
	 */
	private OraSB10gFolder folder;

	/**
	 * list of {@link ServiceDependencies}
	 */
	private final ServiceDependencies serviceDependencies;

	private final OsbActivityDependency activityDependency;

	/**
	 * type of dependency
	 */
	protected ServiceDependencyType type;

	/**
	 * service endpoint config
	 */

	@AddDataToExistsGroupView
	protected EndpointConfig<?> endpointConfig;

	// private EndpointHttp endpointHttp = new EndpointHttp();

	/**
	 * Constructor
	 * 
	 * @param file
	 * @param type
	 */
	public OSBService(File file, ServiceDependencyType type) {
		this.file = file;
		this.type = type;
		this.serviceDependencies = new ServiceDependencies(this);
		this.activityDependency = new OsbActivityDependency(this);
	}

	public final String getName() {
		return name;
	}

	public final Project getProject() {
		return project;
	}

	public final void setProject(Project project) {
		this.project = project;
	}

	public Image getImage(boolean small) {
		return getImage();
	}

	public abstract Image getImage();

	//@PropertyViewData(title = "Folder", showWhenIsEmpty = false)
	public final OraSB10gFolder getFolder() {
		return folder;
	}

	public final Object[] tofolders() {
		List<Object> list = new ArrayList<Object>();
		list.add(this);

		if (folder != null) {
			list.add(0, folder);

			OraSB10gFolder f = folder.getParent();
			while (null != f) {
				list.add(0, f);
				f = f.getParent();
			}
		}

		return list.toArray();
	}

	public final void setFolder(OraSB10gFolder folder) {
		this.folder = folder;
	}

	public final File getFile() {
		return file;
	}

	public String toString() {
		return name;
	}

	public final ServiceDependencies getServiceDependencies() {
		return serviceDependencies;
	}

	/**
	 * @return the activityDependency
	 */
	public final OsbActivityDependency getActivityDependency() {
		return activityDependency;
	}

	public final ServiceDependencyType getDependencyType() {
		return type;
	}

	public final String getOrginalName() {
		return orginalName;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the endpointConfig
	 */
	public final EndpointConfig<?> getEndpointConfig() {
		return endpointConfig;
	}

	/**
	 * set new endpoint config
	 * 
	 * @param endpointConfig
	 */
	public final void setEndpointConfig(EndpointConfig<?> endpointConfig) {
		this.endpointConfig = endpointConfig;
	}

	public final Binding getBinding() {
		return binding;
	}

	public final void setBinding(Binding binding) {
		this.binding = binding;
	}

	@Override
	public void calculateEndpointList(EndpointList endpointLists) {
	}

	@Override
	public String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type:			").append(getType());
		sb.append("\nName:			").append(name);
		if (description != null && description.length() != 0) {
			sb.append("\nDescription:		").append(description);
		}
		if (file != null) {
			sb.append("\nFile:				").append(file.toString());
		}

		OraSB10gFolder folder = getFolder();
		if (folder != null) {
			sb.append("\nFolder:		").append(folder.toString());
		}

		if (endpointConfig != null) {
			sb.append("\nEndpoint Type:		");

			if (endpointConfig.getProtocol() == ProviderProtocol.UNKNOWN) {
				sb.append(((EndpointUNKNOWN) endpointConfig).getProviderId());
			} else {
				sb.append(endpointConfig.getProtocol().toString());
				if (!endpointConfig.getUris().isEmpty()) {
					sb.append("\nURIs: ");

					Iterator<String> i = endpointConfig.getUris().iterator();
					while (i.hasNext()) {
						sb.append(i.next());
						if (i.hasNext()) {
							sb.append(",\n");
						}
					}
				}
			}
		}

		return sb.toString();

	}

	public final String getType() {
		return type.toString();
	}
}