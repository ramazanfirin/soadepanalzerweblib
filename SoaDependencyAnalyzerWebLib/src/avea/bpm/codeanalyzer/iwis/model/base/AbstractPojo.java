package avea.bpm.codeanalyzer.iwis.model.base;

public class AbstractPojo implements Pojo
{
	private String name;
	private String description;
	
	public AbstractPojo() {
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
