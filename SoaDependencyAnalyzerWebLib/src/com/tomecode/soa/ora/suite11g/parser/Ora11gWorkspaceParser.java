package com.tomecode.soa.ora.suite11g.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.suite11g.project.Ora11gProject;
import com.tomecode.soa.ora.suite11g.workspace.Ora11gWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Parser for {@link Ora11gWorkspace}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
final class Ora11gWorkspaceParser extends AbstractParser {

	/**
	 * composite project parser
	 */
	private final Ora11gProjectParser projectParser = new Ora11gProjectParser();

	Ora11gWorkspaceParser() {

	}

	/**
	 * parse {@link Ora11gWorkspace}
	 * 
	 * @param jwsFile
	 * @return
	 */
	final Ora11gWorkspace parse(File jwsFile) {
		Ora11gWorkspace workspace = new Ora11gWorkspace(getNameWithouExtension(jwsFile.getName()), jwsFile);
		// loading all .jpr (project) files
		List<File> jprFiles = parseProjectsFormJws(jwsFile);

		for (File jprFile : jprFiles) {
			Ora11gProject project = projectParser.parse(jprFile);
			workspace.addProject(project);
		}

		return workspace;
	}

	/**
	 * read all project files (.jpr) from .jws file
	 * 
	 * @param jwsFile
	 * @return
	 */
	private final List<File> parseProjectsFormJws(File jwsFile) {
		List<File> files = new ArrayList<File>();
		try {
			Element element = parseXml(jwsFile);
			if (element != null && "workspace".equals(element.getName())) {
				Element elist = element.element("list");
				if (elist != null) {
					List<?> eHashs = elist.elements("hash");
					for (Object o : eHashs) {
						Element eUrl = ((Element) o).element("url");
						if (eUrl != null) {
							files.add(new File(jwsFile.getParent() + File.separator + eUrl.attributeValue("path")));
						}
					}
				}

			}

		} catch (ServiceParserException e) {
			e.printStackTrace();
			// TODO: co ak nenajdem
		}

		return files;
	}

	/**
	 * analysis dependencies between projects in workspace
	 * 
	 * @param workspace
	 */
	final void analyzeDependencies(Workspace workspace) {
		for (Project project : workspace.getProjects()) {
			projectParser.analyzeDependencies((Ora11gProject) project);
		}
	}

	/**
	 * parse all projects in workspace
	 * 
	 * @param workspace
	 */
	final void parse(Workspace workspace) {
		for (Project project : workspace.getProjects()) {
			projectParser.parse((Ora11gProject) project);
		}
	}

}
