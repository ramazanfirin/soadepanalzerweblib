package test;

import java.io.File;

//import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
//import com.tomecode.soa.dependency.analyzer.gui.wizards.AddWorkspaceWizard.WorkspaceConfig;
import com.tomecode.soa.ora.osb10g.parser.OraSB10gMWorkspaceParser;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.ora.suite10g.parser.Ora10gMWorkspaceParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.workspace.MultiWorkspace;

public class Test {

	public static void main(String[] args) {


		OraSB10gMWorkspaceParser oraSB10gParser = new OraSB10gMWorkspaceParser();
		
		
		
		OraSB10gMultiWorkspace multiWorkspace = new OraSB10gMultiWorkspace("multiWorkSpace", new File("c:"));
		OraSB10gWorkspace workspace = new OraSB10gWorkspace("OSB_WORKSPACE", new File("D:/mw/workspaces/osb1030/osb-ws"));
		multiWorkspace.addWorkspace(workspace);
		try {
			oraSB10gParser.parse(multiWorkspace);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println("bitti");
		
	}
	
}
