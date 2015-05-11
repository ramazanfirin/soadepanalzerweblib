package avea.bpm.codeanalyzer.iwis.model;

import java.util.ArrayList;
import java.util.List;

import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public class Client implements Pojo 
{

	private String name;
	private String description;
	
	private List services = new ArrayList();
	
	public Client() {
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

}
