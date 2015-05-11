package test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tomecode.soa.ora.osb10g.parser.OraSB10gMWorkspaceParser;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.Workspace;

public class Test2 {
	public static void main(String[] args) {
		
		OraSB10gMWorkspaceParser oraSB10gParser = new OraSB10gMWorkspaceParser();;
		OraSB10gMultiWorkspace multiWorkspace = new OraSB10gMultiWorkspace("MWS", new File("C:"));
		
		List<String> path  = new ArrayList<String>();
		path.add("D:/mw/workspaces/osb1030/osb-new_test");
		
		oraSB10gParser.parse(multiWorkspace, path);
		
		
		Workspace ws = multiWorkspace.getWorkspaces().get(0);
		
		List<Project> list  =  ws.getProjects();
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			OraSB10gProject project = (OraSB10gProject) iterator.next();
			System.out.println(project.getName());
			
			List<OSBService> osbServices = project.getServices();
			
			for (Iterator iterator2 = osbServices.iterator(); iterator2
					.hasNext();) {
				OSBService osbService = (OSBService) iterator2.next();
				
				System.out.println(osbService.getName());
				
			}
			
			//project.
		}
		
		
		System.out.println("bitti");
	}
}
