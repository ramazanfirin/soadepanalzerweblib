package avea.bpm.codeanalyzer.model.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Method extends AbstractBasePojo {

	private String name;
	private int controlId;
	
	private Control control;
	private List calledMethods = new ArrayList();
	private List callerMethods = new ArrayList();

	
	public Method() {
	}

	public Method(String name) {
		setName(name);
	}
	
	public Control getControl() {
		return control;
	}

	public void setControl(Control coreControl) {
		this.control = coreControl;
		if ( coreControl != null )
			setControlId(coreControl.getId());
		else
			setControlId(0);
	}

	public int getControlId() {
		if ( getControl() != null )
			setControlId(getControl().getId());
		return controlId;
	}

	public void setControlId(int controlId) {
		this.controlId = controlId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean addCallerMethod(Method method) {
		return callerMethods.add(method);
	}

	public boolean removeCallerMethod(Method method) {
		return callerMethods.remove(method);
	}

	public Iterator callerMethodsIterator() {
		return callerMethods.iterator();
	}

	public Iterator calledMethodsIterator() {
		return calledMethods.iterator();
	}

	public boolean addCalledMethod(Method method) {
		return calledMethods.add(method);
	}

	public boolean removeCalledMethod(Method method) {
		return calledMethods.remove(method);
	}

	public String getFullName() {
		return getControl().getQualifiedName()+"."+getName();
	}
	
	public String getPersistanceString() {
		// TODO Auto-generated method stub
		return "";
	}
}
