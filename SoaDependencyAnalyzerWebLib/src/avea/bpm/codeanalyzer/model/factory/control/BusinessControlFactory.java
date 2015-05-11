package avea.bpm.codeanalyzer.model.factory.control;

import avea.bpm.codeanalyzer.model.BusinessControl;
import avea.bpm.codeanalyzer.model.base.Control;

public class BusinessControlFactory extends ControlFactory {
	
	private static BusinessControlFactory instance;
	
	public static synchronized BusinessControlFactory getInstance() {
		if ( instance == null )
			instance = new BusinessControlFactory();
		return instance;
	}

	public Control generateInstance() {
		return new BusinessControl();
	}

}
