package avea.bpm.codeanalyzer.model;

import avea.bpm.codeanalyzer.model.base.AbstractBasePojo;
import avea.bpm.codeanalyzer.model.base.Service;

public class ServiceInfo extends AbstractBasePojo implements Comparable
{
	private int serviceId;
	private String name;
	private String description;
	private String owner;
	private String iwisName;
	
//	private String type;
//	private String trigger;
//	private String input;
//	private String output;
//	private String timing;
//	private String responsibilities;
//	private String requiredInfrastructure;
	
	private Service service;

	public ServiceInfo() {
	}

	public ServiceInfo(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Object obj) {
		ServiceInfo process = (ServiceInfo)obj;
		return getName().compareTo(process.getName());
	}

	public String getPersistanceString() {
		return getId()+" "+getName();
	}

	public String toString() {
		return getName();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		if ( service == null )
			setServiceId(0);
		else {
			this.service = service;
			setServiceId(service.getId());
		}
	}

	public String getIwisName() {
		return iwisName;
	}

	public void setIwisName(String iwisName) {
		this.iwisName = iwisName;
	}

}
