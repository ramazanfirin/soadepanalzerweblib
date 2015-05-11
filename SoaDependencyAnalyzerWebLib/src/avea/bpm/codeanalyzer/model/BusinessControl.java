package avea.bpm.codeanalyzer.model;

import avea.bpm.codeanalyzer.model.base.Control;

public class BusinessControl extends Control implements Comparable
{
	public BusinessControl() {
	}

	public BusinessControl(String name) {
		super(name);
	}

	public int compareTo(Object obj) {
		BusinessControl businessControl = (BusinessControl)obj;
		return getName().compareTo(businessControl.getName());
	}

	public String getPersistanceString() {
		return getId()+" "+getName();
	}

	public void setControlId(String string) {
		// TODO Auto-generated method stub
	}

	public String getControlId() {
		// TODO Auto-generated method stub
		return "";
	}
}
