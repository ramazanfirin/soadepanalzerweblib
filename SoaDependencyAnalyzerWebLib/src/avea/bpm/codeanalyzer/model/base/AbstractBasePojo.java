package avea.bpm.codeanalyzer.model.base;

public abstract class AbstractBasePojo implements BasePojo
{

	private int id;
	
	public AbstractBasePojo(){
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public abstract String getPersistanceString();
}
