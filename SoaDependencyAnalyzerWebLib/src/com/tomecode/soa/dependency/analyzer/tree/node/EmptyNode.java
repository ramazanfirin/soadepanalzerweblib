package com.tomecode.soa.dependency.analyzer.tree.node;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * simple empty node
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EmptyNode {

	private final Object[] objects;

	private final Object[] emptyResult;

	public EmptyNode() {
		objects = new Object[1];
		emptyResult = new Object[] {};
	}

	public EmptyNode(Object object) {
		this();
		set(object);
	}

	public final void set(Object object) {
		objects[0] = object;
	}

	public final boolean hasChildren() {
		return objects.length != 0 && objects[0] != null;
	}

	public final Object[] getChildren() {
		return objects[0] == null ? emptyResult : objects;
	}
}
