package com.tomecode.soa.ora.osb10g.services.config;

import java.io.Serializable;

public class DbTransportParameter implements Serializable{
	public  enum Directions {
		IN, OUT
	};

	public  enum SqlType {
		VARCHAR, NUMERIC
	};

	private Directions direction;
	private SqlType sqlType;
	private boolean isMandatory = false;
	private int jdbcIndex = 0;
	private String parameterName = null;

	public int getJdbcIndex() {
		return jdbcIndex;
	}

	public void setJdbcIndex(int jdbcIndex) {
		this.jdbcIndex = jdbcIndex;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
	public Directions getDirection() {
		return direction;
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
	}

	public SqlType getSqlType() {
		return sqlType;
	}

	public void setSqlType(SqlType sqlType) {
		this.sqlType = sqlType;
	}



}