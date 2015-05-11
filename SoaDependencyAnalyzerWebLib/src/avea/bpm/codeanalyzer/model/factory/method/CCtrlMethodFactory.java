package avea.bpm.codeanalyzer.model.factory.method;

import avea.bpm.codeanalyzer.model.CCtrlMethod;
import avea.bpm.codeanalyzer.model.base.Method;

public class CCtrlMethodFactory extends MethodFactory {
	
	private static CCtrlMethodFactory instance;
	
	public static synchronized CCtrlMethodFactory getInstance() {
		if ( instance == null )
			instance = new CCtrlMethodFactory();
		return instance;
	}

	public Method generateInstance() {
		return new CCtrlMethod();
	}

}
