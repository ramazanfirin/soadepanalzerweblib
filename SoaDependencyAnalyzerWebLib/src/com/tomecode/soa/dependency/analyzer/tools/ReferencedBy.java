package com.tomecode.soa.dependency.analyzer.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ReferencedBy  implements Serializable{
	
	List<Object> referencedByList = null;

	public List<Object> getRefefencedByList() {
		if(referencedByList==null)
			referencedByList = new ArrayList<Object>();
		return referencedByList;
	}

	public void setRefefencedByList(List<Object> referencedByList) {		
		this.referencedByList = referencedByList;
	}
	
	public void addReferencedByService(Object obj){
		if(referencedByList==null){
			referencedByList = new ArrayList<Object>();
		}
		if (!this.referencedByList.contains(obj))
			referencedByList.add(obj);
	}
	
	public  String getObjectData(){
		return null;
	}

}
