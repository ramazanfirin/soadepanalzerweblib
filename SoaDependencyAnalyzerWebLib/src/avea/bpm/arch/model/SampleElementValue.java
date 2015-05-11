package avea.bpm.arch.model;


public class SampleElementValue {
	private Long id;
	private String elementName;
	private String elementValue;

	public SampleElementValue() {
		super();
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}

}
