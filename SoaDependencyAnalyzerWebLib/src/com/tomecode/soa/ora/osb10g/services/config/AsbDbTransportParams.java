package com.tomecode.soa.ora.osb10g.services.config;

import java.io.Serializable;
import java.util.ArrayList;

public class AsbDbTransportParams implements Serializable{

	private String sqlStatement;
	private String operationType;
	private String workManager;
	private int timeoutInSeconds;
	private String procedureName;

	private ArrayList<DbTransportParameter> parameterList = new ArrayList<DbTransportParameter>();

	public String getSqlStatement() {
		return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement) {		 
		this.sqlStatement = sqlStatement;
		procedureName = sqlStatement;
		try{
			procedureName = sqlStatement.toUpperCase().replaceAll("SELECT", "").trim();
			procedureName = procedureName.replaceFirst("BEGIN", "").trim();
			procedureName = procedureName.substring(0, procedureName.indexOf("("));
		}catch(Exception ex){};
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getWorkManager() {
		return workManager;
	}

	public void setWorkManager(String workManager) {
		this.workManager = workManager;
	}

	public int getTimeoutInSeconds() {
		return timeoutInSeconds;
	}

	public void setTimeoutInSeconds(int timeoutInSeconds) {
		this.timeoutInSeconds = timeoutInSeconds;
	}

	public ArrayList<DbTransportParameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(ArrayList<DbTransportParameter> parameterList) {
		this.parameterList = parameterList;
	}

	public String getProcedureName() {
		return procedureName;
	}
	
}

