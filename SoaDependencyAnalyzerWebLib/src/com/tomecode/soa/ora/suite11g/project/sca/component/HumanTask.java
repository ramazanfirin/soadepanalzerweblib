package com.tomecode.soa.ora.suite11g.project.sca.component;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.ora.suite11g.project.sca.ScaComponent;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Human task
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
//TODO: Dokoncit parsoanie HumanTask v SOA Suite 11g
public final class HumanTask extends ScaComponent {

	public HumanTask(String name, File file) {
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
		return null;
	}

}
