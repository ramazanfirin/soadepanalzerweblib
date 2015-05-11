package com.tomecode.soa.ora.suite11g.project.sca.component;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.ora.suite11g.project.sca.ScaComponent;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Rule
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
// TODO Dokoncit parsovanie pre Rule v SOA Suite 11g
public final class Rule extends ScaComponent {

	public Rule(String name, File file) {
		super(name, file);
	}

	@Override
	public Image getImage(boolean small) {
		return null;
	}

	@Override
	public String getToolTip() {
		return null;
	}

	@Override
	public String getType() {
		return "";
	}

}
