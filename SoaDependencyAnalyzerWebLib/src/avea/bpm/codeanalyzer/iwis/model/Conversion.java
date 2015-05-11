package avea.bpm.codeanalyzer.iwis.model;

import java.util.ArrayList;
import java.util.List;

import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public class Conversion  implements Pojo
{
	
	private String name;
	private String description;

	private List services = new ArrayList();
	private List attributes = new ArrayList();

	public Conversion() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean addService(Service service) {
		return services.add(service);
	}

	public boolean removeService(Service service) {
		return services.remove(service);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List getAttributes() {
		return attributes;
	}

	public boolean addAttribute(Attribute attribute) {
		return attributes.add(attribute);
	}

	public boolean removeAttribute(Attribute attribute) {
		return attributes.remove(attribute);
	}


}
