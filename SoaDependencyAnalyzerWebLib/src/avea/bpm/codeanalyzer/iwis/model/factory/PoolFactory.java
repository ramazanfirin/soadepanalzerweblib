package avea.bpm.codeanalyzer.iwis.model.factory;

import avea.bpm.codeanalyzer.iwis.model.Pool;
import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public class PoolFactory extends AbstractPojoFactory {

	public Pojo instanciate() {
		return new Pool();
	}
}
