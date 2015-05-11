package com.tomecode.soa.dependency.analyzer.tree.node;

import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * helper tree node for dependency projects
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public interface DependencyNode {
	/**
	 * {@link Project}
	 * 
	 * @return
	 */
	Project getProject();

}
