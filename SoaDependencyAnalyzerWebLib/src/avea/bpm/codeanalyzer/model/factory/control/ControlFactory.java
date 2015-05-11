package avea.bpm.codeanalyzer.model.factory.control;

import avea.bpm.codeanalyzer.model.base.Control;

public abstract class ControlFactory {

	public abstract Control generateInstance();
	
	public Control generateInstance(String name) {
		Control control = generateInstance();
		control.setName(name);
		return control;
	}

	public Control generateInstance(String name, String pack) {
		Control control = generateInstance(name);
		control.setPack(pack);
		return control;
	}
}
