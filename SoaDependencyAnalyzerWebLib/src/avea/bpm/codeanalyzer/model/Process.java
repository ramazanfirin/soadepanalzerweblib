package avea.bpm.codeanalyzer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import avea.bpm.codeanalyzer.model.base.AbstractBasePojo;

public class Process extends AbstractBasePojo implements Comparable
{
	private String processId;
	private String name;
	private String cvsHierarchy;
	private String path;



	private List bcMethods = new ArrayList();
	private ProcessInfo processInfo;
	
	public Process() {
	}

	public Process(String name) {
		setName(name);
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String controlId) {
		this.processId = controlId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Object obj) {
		Process process = (Process)obj;
		return getName().compareTo(process.getName());
	}

	public String getPersistanceString() {
		return getId()+" "+getProcessId()+" "+getName();
	}
	
	public boolean addBCMethod(BCtrlMethod method) {
		return bcMethods.add(method);
	}

	public boolean removeBCMethod(BCtrlMethod method) {
		return bcMethods.remove(method);
	}

	public Iterator methodsIterator() {
		return bcMethods.iterator();
	}
	
	public String toString() {
		return getName();
	}

	public Collection getCControlMetods() {
		TreeSet treeSet = new TreeSet();
		for (Iterator iter = this.bcMethods.iterator(); iter.hasNext();) {
			BCtrlMethod bctrlMethod = (BCtrlMethod) iter.next();
			for (Iterator iterator = bctrlMethod.calledMethodsIterator(); iterator.hasNext();) {
				CCtrlMethod	ctrlMethod = (CCtrlMethod) iterator.next();
				treeSet.add(ctrlMethod);
			}
		}
		return treeSet;
	}

	public String getCvsHierarchy() {
		return cvsHierarchy;
	}

	public void setCvsHierarchy(String cvsPosition) {
		this.cvsHierarchy = cvsPosition;
	}

	public ProcessInfo getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(ProcessInfo processInfo) {
		this.processInfo = processInfo;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
