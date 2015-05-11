package avea.bpm.codeanalyzer.model.base;

public interface BasePojo {

	public int getId();

	public void setId(int id);
	
	public abstract String getPersistanceString();
}
