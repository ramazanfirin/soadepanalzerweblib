package avea.bpm.codeanalyzer.iwis.model.factory;

import avea.bpm.codeanalyzer.iwis.model.Conversion;
import avea.bpm.codeanalyzer.iwis.model.base.Pojo;

public class ConversionFactory extends AbstractPojoFactory {

	public Pojo instanciate() {
		return new Conversion();
	}
}
