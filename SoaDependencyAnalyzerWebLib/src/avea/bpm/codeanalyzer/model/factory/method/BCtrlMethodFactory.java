package avea.bpm.codeanalyzer.model.factory.method;

import avea.bpm.codeanalyzer.model.BCtrlMethod;
import avea.bpm.codeanalyzer.model.base.Method;

public class BCtrlMethodFactory extends MethodFactory {
	
	private static BCtrlMethodFactory instance;
	
	public static synchronized BCtrlMethodFactory getInstance() {
		if ( instance == null )
			instance = new BCtrlMethodFactory();
		return instance;
	}

	public Method generateInstance() {
		return new BCtrlMethod();
	}

}
