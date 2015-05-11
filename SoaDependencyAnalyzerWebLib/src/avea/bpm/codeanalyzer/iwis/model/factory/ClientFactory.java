package avea.bpm.codeanalyzer.iwis.model.factory;

import avea.bpm.codeanalyzer.iwis.model.Client;
import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public class ClientFactory extends AbstractPojoFactory {

	public Pojo instanciate() {
		return new Client();
	}
}
