package avea.bpm.codeanalyzer.iwis.model;

import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public class Pool implements Pojo
{

	private String name;
	private String description;
	
	public Pool() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
