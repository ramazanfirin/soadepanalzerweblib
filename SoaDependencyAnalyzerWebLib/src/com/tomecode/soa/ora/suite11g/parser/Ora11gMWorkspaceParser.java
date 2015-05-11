package com.tomecode.soa.ora.suite11g.parser;

import java.io.File;
import java.util.List;

import com.tomecode.soa.ora.suite11g.workspace.Ora11gMultiWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Parser for {@link Ora11gMultiWorkspace}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Ora11gMWorkspaceParser extends AbstractParser {

	/**
	 * workspace parser
	 */
	private final Ora11gWorkspaceParser workspaceParser = new Ora11gWorkspaceParser();

	/**
	 * parse new {@link Ora11gMultiWorkspace}
	 * 
	 * @param multiWorkspace
	 * @param files
	 */
	public final void parse(Ora11gMultiWorkspace multiWorkspace, List<String> files) {
		for (String f : files) {
			File jwsFile = findFileWithExcetension(new File(f), ORACLE_SOA_SUITE_APPLIATION_FILE_EXTENSTION);

			if (jwsFile == null) {
				throw new RuntimeException("fuha tak toto je problem!!!");
				// TODO: ak sa jws file nenajde tak potom zobrazit msg
			} else {
				multiWorkspace.addWorkspace(workspaceParser.parse(jwsFile));
			}
		}

		analyzeDependencies(multiWorkspace);
	}

	/**
	 * parse {@link Ora11gMultiWorkspace} from dir
	 * 
	 * @param file
	 * @return
	 */
	public final Ora11gMultiWorkspace parse(File file) {

		Ora11gMultiWorkspace multiWorkspace = new Ora11gMultiWorkspace(getNameWithouExtension(file.getName()), file);
		File jwsFile = findFileWithExcetension(file, ORACLE_SOA_SUITE_APPLIATION_FILE_EXTENSTION);

		if (jwsFile == null) {
			// TODO: ak sa jws file nenajde tak potom zobrazit msg
		} else {
			multiWorkspace.addWorkspace(workspaceParser.parse(jwsFile));
		}

		analyzeDependencies(multiWorkspace);

		return multiWorkspace;
	}

	/**
	 * analysis of dependnecies in {@link Ora11gMultiWorkspace}
	 * 
	 * @param multiWorkspace
	 */
	private final void analyzeDependencies(Ora11gMultiWorkspace multiWorkspace) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			workspaceParser.analyzeDependencies(workspace);
		}
	}

	/**
	 * parse restored {@link Ora11gMultiWorkspace}
	 * 
	 * @param ora11gMultiWorkspace
	 */
	public final void parse(Ora11gMultiWorkspace ora11gMultiWorkspace) {
		for (Workspace workspace : ora11gMultiWorkspace.getWorkspaces()) {
			workspaceParser.parse(workspace);
		}
		analyzeDependencies(ora11gMultiWorkspace);
	}
}
