package avea.bpm.codeanalyzer.iwis.model;

import java.util.ArrayList;
import java.util.List;

import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public class Service implements Pojo
{
	/* Attributes */
	private String name;
	private String description;
	private String version;

	/* References */
	private List clients = new ArrayList();
	private Pool pool;
	private DetailedService detailedService;
	private Conversion in;
	private Conversion out;
	
	public Service(){
	}

	public DetailedService getDetailedService() {
		return detailedService;
	}

	public void setDetailedService(DetailedService detailedService) {
		this.detailedService = detailedService;
	}

	public Conversion getIn() {
		return in;
	}

	public void setIn(Conversion in) {
		this.in = in;
	}

	public Conversion getOut() {
		return out;
	}

	public void setOut(Conversion out) {
		this.out = out;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public boolean addClient(Client client) {
		return clients.add(client);
	}

	public boolean removeClient(Client client) {
		return clients.remove(client);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDetailedService(String detailedService) {
		setDetailedService(new DetailedService());
		getDetailedService().setName(detailedService);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
