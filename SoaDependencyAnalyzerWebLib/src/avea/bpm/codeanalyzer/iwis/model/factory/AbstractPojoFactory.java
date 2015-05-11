package avea.bpm.codeanalyzer.iwis.model.factory;

import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public abstract class AbstractPojoFactory
{	
	public abstract Pojo instanciate();

	public Pojo instanciate(String name, String description) {
		Pojo pojo = instanciate();
		pojo.setName(name);
		pojo.setDescription(description);
		return pojo;
	}
}
