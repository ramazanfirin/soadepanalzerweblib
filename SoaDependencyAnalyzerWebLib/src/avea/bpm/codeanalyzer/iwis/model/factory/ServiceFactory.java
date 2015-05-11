package avea.bpm.codeanalyzer.iwis.model.factory;

import avea.bpm.codeanalyzer.iwis.model.Service;
import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public class ServiceFactory extends AbstractPojoFactory {

	public Pojo instanciate() {
		return new Service();
	}
}
