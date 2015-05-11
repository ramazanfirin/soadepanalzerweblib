package avea.bpm.codeanalyzer.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import avea.bpm.codeanalyzer.model.base.Method;

public class BCtrlMethod extends Method implements Comparable
{
	private List processes = new ArrayList();
	
	public BCtrlMethod() {
	}

	public BCtrlMethod(String name) {
		super(name);
	}

	public int compareTo(Object obj) {
		BCtrlMethod method = (BCtrlMethod)obj;
		int cmp = getName().compareTo(method.getName());
		if ( cmp == 0 )
			cmp = getControl().compareTo(method.getControl());
		return cmp;
	}

	public String getPersistanceString() {
		return getId()+" "+getControl().getId()+" "+getControl().getName()+" "+getName();
	}
	
	public boolean addProcess(Process process) {
		return this.processes.add(process);
	}

	public boolean removeProcess(Process process) {
		return this.processes.remove(process);
	}

	public Iterator processesIterator() {
		return this.processes.iterator();
	}
}
