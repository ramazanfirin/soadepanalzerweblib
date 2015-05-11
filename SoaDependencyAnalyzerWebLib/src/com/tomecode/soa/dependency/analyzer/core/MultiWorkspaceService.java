package com.tomecode.soa.dependency.analyzer.core;

import java.util.List;

import com.tomecode.soa.workspace.MultiWorkspace;

/**
 * @author Peter Karich, peat_hal ‘at’ users ‘dot’ sourceforge ‘dot’ net
 */
public interface MultiWorkspaceService {

    List<MultiWorkspace>  getMultiWorkspaces();
    
    String communicate(String str) ;
}
