package avea.bpm.codeanalyzer.util;

import java.io.IOException;
//
//import org.netbeans.lib.cvsclient.CVSRoot;
//import org.netbeans.lib.cvsclient.Client;
//import org.netbeans.lib.cvsclient.admin.StandardAdminHandler;
//import org.netbeans.lib.cvsclient.command.CommandAbortedException;
//import org.netbeans.lib.cvsclient.command.CommandException;
//import org.netbeans.lib.cvsclient.command.GlobalOptions;
//import org.netbeans.lib.cvsclient.command.checkout.CheckoutCommand;
//import org.netbeans.lib.cvsclient.commandLine.BasicListener;
//import org.netbeans.lib.cvsclient.connection.AuthenticationException;
//import org.netbeans.lib.cvsclient.connection.Connection;
//import org.netbeans.lib.cvsclient.connection.PServerConnection;

public class CVSUtil {

//	public static void checkout(String cvsRootStr, String module, String localWorkPath) throws CommandException, CommandAbortedException, AuthenticationException, IOException {

//		CVSRoot root = CVSRoot.parse(cvsRootStr);
//		Connection connection = new PServerConnection(root);
//		try {
//			
//			connection.open();
//			
//			Client client = new Client(connection, new StandardAdminHandler()); 
//			client.setLocalPath(localWorkPath);
//			client.getEventManager().addCVSListener(new BasicListener());
//			
//			CheckoutCommand command = new CheckoutCommand(true, module);
//			
//			GlobalOptions globalOptions = new GlobalOptions();
//			globalOptions.setCVSRoot(cvsRootStr);
//			
//			client.executeCommand(command, globalOptions);
//		} catch (CommandAbortedException e) {
//			e.printStackTrace();
//			throw e;
//		} catch (CommandException e) {
//			e.printStackTrace();
//			throw e;
//		} catch (AuthenticationException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		finally {
//			connection.close();
//		}
//	}
}
