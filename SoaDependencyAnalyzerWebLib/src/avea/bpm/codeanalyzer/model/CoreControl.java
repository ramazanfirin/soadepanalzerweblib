package avea.bpm.codeanalyzer.model;

import avea.bpm.codeanalyzer.model.base.Control;

public class CoreControl extends Control implements Comparable
{

	public CoreControl() {
	}

	public CoreControl(String name) {
		super(name);
	}

	public CoreControl(String name, String pack) {
		super(name, pack);
	}
	
	public int compareTo(Object obj) {
		CoreControl businessControl = (CoreControl)obj;
		return getName().compareTo(businessControl.getName());
	}

	public String getPersistanceString() {
		return getId()+" "+getPack()+" "+getName();
	}
	
	public String toString() {
		return getPack()+"."+getName();
	}
}
