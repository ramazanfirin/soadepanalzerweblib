package avea.bpm.arch.model;

import java.util.HashSet;
import java.util.Set;

public class Service {
	private Long id;
	private String methodName;
	private String controlName;
	private String controlPack;
	private String methodSignature;

	private String name;
	private String description;
	private String owner;
	private String iwisName;
	private String backendImplementation;
	private String version;

	private Set processes = new HashSet();
	
	public Service() {
		super();
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getControlPack() {
		return controlPack;
	}

	public void setControlPack(String controlPack) {
		this.controlPack = controlPack;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String name) {
		this.methodName = name;
	}

	public Set getProcesses() {
		return processes;
	}

	public void setProcesses(Set processes) {
		this.processes = processes;
	}
	
	public void addProcess(Process process){
		getProcesses().add(process);
		process.getServices().add(this);
	}

	public String getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(String signature) {
		this.methodSignature = signature;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIwisName() {
		return iwisName;
	}

	public void setIwisName(String iwisName) {
		this.iwisName = iwisName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBackendImplementation() {
		return backendImplementation;
	}

	public void setBackendImplementation(String backendImplementation) {
		this.backendImplementation = backendImplementation;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
