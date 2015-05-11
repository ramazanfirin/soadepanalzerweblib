package avea.bpm.codeanalyzer.model;

import java.util.Collection;

import avea.bpm.codeanalyzer.model.base.AbstractBasePojo;

public class ProcessInfo extends AbstractBasePojo implements Comparable
{
	private int processId;
	
	private String processNo;
	private String name;
	private String description;
	private String iwisService;
	private String returnCodes;
	private String input;
	private String output;
	
	private Process process;
	
	private String processRefNo;

	private Collection errorCodes;
	
	public ProcessInfo() {
	}

	public ProcessInfo(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Object obj) {
		ProcessInfo process = (ProcessInfo)obj;
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

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
//		if ( input != null && input.length() > 3999)
//			input = input.substring(0, 3999);
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
//		if ( output != null && output.length() > 3999)
//			output = output.substring(0, 3999);
		this.output = output;
	}

	public String getIwisService() {
		return iwisService;
	}

	public void setIwisService(String owner) {
		this.iwisService = owner;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getReturnCodes() {
		return returnCodes;
	}

	public void setReturnCodes(String type) {
		this.returnCodes = type;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		if ( process == null )
			setProcessId(0);
		else {
			this.process = process;
			setProcessId(process.getId());
		}
	}
	
	public String getProcessRefNo() {
		return processRefNo;
	}

	public void setProcessRefNo(String processRefNo) {
		this.processRefNo = processRefNo;
	}

	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	public Collection getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(Collection errorCodes) {
		this.errorCodes = errorCodes;
	}


}
