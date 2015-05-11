package avea.bpm.arch.model;

import java.util.Date;

public class Stencil {
	private Long id;
	private Date generationDate;
	private String textContent;
	
	public Stencil() {
		super();
		setGenerationDate(new Date());
	}

	public Date getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(Date generationDate) {
		this.generationDate = generationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
}
