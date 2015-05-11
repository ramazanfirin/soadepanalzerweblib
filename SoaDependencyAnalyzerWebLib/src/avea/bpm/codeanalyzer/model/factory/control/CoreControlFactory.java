package avea.bpm.codeanalyzer.model.factory.control;

import avea.bpm.codeanalyzer.model.CoreControl;
import avea.bpm.codeanalyzer.model.base.Control;

public class CoreControlFactory extends ControlFactory {
	
	private static CoreControlFactory instance;
	
	public static synchronized CoreControlFactory getInstance() {
		if ( instance == null )
			instance = new CoreControlFactory();
		return instance;
	}

	public Control generateInstance() {
		return new CoreControl();
	}

}
