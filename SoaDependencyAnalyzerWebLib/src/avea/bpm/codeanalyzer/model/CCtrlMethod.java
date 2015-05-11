package avea.bpm.codeanalyzer.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import avea.bpm.codeanalyzer.model.base.Method;
import avea.bpm.codeanalyzer.model.base.Service;

public class CCtrlMethod extends Method implements Service, Comparable
{
	private ServiceInfo serviceInfo;
	
	private String signature;

	public CCtrlMethod() {
	}

	public CCtrlMethod(String name) {
		super(name);
	}

	public int compareTo(Object obj) {
		CCtrlMethod method = (CCtrlMethod)obj;
		int cmp = getControl().compareTo(method.getControl());
		if ( cmp == 0 )
			cmp = getName().compareTo(method.getName());
		return cmp;
	}

	public String getPersistanceString() {
		return getId()+" "+getControl().getId()+" "+getControl().getName()+" "+getName();
	}

	public Collection getProcesses() 
	{
		TreeSet treeSet = new TreeSet();
		for (Iterator iter = this.callerMethodsIterator(); iter.hasNext();) {
			BCtrlMethod bctrlMethod = (BCtrlMethod) iter.next();
			for (Iterator iterator = bctrlMethod.processesIterator()	; iterator.hasNext();) {
				Process	process = (Process) iterator.next();
				treeSet.add(process);
			}
		}
		return treeSet;
	}
	
	public String toString() {
		return getControl().toString()+"."+getName();
	}

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
