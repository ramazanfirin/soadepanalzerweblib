package avea.bpm.codeanalyzer.model.factory.method;

import avea.bpm.codeanalyzer.model.base.Method;

public abstract class MethodFactory {

	public abstract Method generateInstance();
	
	public Method generateInstance(String name) {
		Method method = generateInstance();
		method.setName(name);
		return method;
	}
}
