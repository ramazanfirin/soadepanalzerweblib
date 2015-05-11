package avea.bpm.arch.model;

import java.util.HashSet;
import java.util.Set;

public class Process
{
	private Long id;
	private String name;
	private String cvsHierarchy;
	private String description;
	private String iwisService;
	private String returnCodes;
	private String input;
	private String output;
	private String version;
	private String processPath;
	
	private Set services = new HashSet();
	
	public Process() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getServices() {
		return services;
	}

	public void setServices(Set services) {
		this.services = services;
	}
	
	public void addService(Service service){
		getServices().add(service);
		service.getProcesses().add(this);
	}

	public String getCvsHierarchy() {
		return cvsHierarchy;
	}

	public void setCvsHierarchy(String cvsHierarchy) {
		this.cvsHierarchy = cvsHierarchy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getIwisService() {
		return iwisService;
	}

	public void setIwisService(String iwisService) {
		this.iwisService = iwisService;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getReturnCodes() {
		return returnCodes;
	}

	public void setReturnCodes(String returnCodes) {
		this.returnCodes = returnCodes;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProcessPath() {
		return processPath;
	}

	public void setProcessPath(String processPath) {
		this.processPath = processPath;
	}
	
	

}
