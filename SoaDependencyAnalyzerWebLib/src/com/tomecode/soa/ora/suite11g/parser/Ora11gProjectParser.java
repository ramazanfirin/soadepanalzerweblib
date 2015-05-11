package com.tomecode.soa.ora.suite11g.parser;

import java.io.File;
import java.io.IOException;

import com.tomecode.soa.ora.suite11g.project.Ora11gProject;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Parser for {@link Ora11gProject}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
final class Ora11gProjectParser extends AbstractParser {

	/**
	 * parser for composite.xml, etc
	 */
	private final Ora11gCompositeParser compositeParser = new Ora11gCompositeParser();

	Ora11gProjectParser() {

	}

	/**
	 * parse .jpr file and return {@link Ora11gProject}
	 * 
	 * @param jprFile
	 * @return
	 */
	final Ora11gProject parse(File jprFile) {
		try {
			jprFile = jprFile.getCanonicalFile();
		} catch (IOException e) {
		}

		Ora11gProject ora11gProject = new Ora11gProject(getNameWithouExtension(jprFile.getName()), jprFile);
		ora11gProject.setCompoiste(compositeParser.parse(jprFile.getParentFile()));
		return ora11gProject;
	}

	/**
	 * analyze dependencie in {@link Project}
	 * 
	 * @param project
	 */
	final void analyzeDependencies(Ora11gProject project) {
		if (project.getComposite() != null) {
			compositeParser.analyzeDependencies(project.getComposite());
		}
	}

	/**
	 * parse {@link Ora11gProject}
	 * 
	 * @param project
	 */
	final void parse(Ora11gProject project) {
		project.setCompoiste(compositeParser.parse(project.getFile().getParentFile()));
	}
}
