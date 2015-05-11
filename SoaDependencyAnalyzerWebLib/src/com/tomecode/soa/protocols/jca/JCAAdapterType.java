package com.tomecode.soa.protocols.jca;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * JCA adapter types
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public enum JCAAdapterType {
	/***
	 * JCA database adapter
	 */
	DATABASE("Database Adapter"),

	/**
	 * JCA file adapter
	 */
	FILE("File Adapter"),
	/**
	 * JCA FTP adapter
	 */
	FTP("Ftp Adapter"),
	/**
	 * JCA JMS Adapter
	 */
	JMS("Jms Adapter"),
	/**
	 * JCA Socket Adapter
	 */
	SOCKET("Socket Adapter"),
	/**
	 * JCA Unknown adapter(maybe custom)
	 */
	UNKNOWN("Unknown Adapter");

	private final String name;

	private JCAAdapterType(String name) {
		this.name = name;
	}

	public final static JCAAdapterType parseAdapter(String jca) {
		for (JCAAdapterType adapterType : values()) {
			if (adapterType.name.equals(jca)) {
				return adapterType;
			}
		}
		return UNKNOWN;
	}

	public final static JCAAdapterType parse(String jca) {
		for (JCAAdapterType adapterType : values()) {
			if (adapterType.name().equals(jca)) {
				return adapterType;
			}
		}
		return UNKNOWN;
	}

	public final String getName() {
		return "JCA " + name;
	}

}
