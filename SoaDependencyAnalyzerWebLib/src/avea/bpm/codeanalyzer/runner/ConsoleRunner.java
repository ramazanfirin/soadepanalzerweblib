package avea.bpm.codeanalyzer.runner;

import avea.bpm.codeanalyzer.util.CVSUtil;
import avea.bpm.codeanalyzer.util.Files;



public class ConsoleRunner 
{
	private static String workdir;
	private static String cvsRootStr;
	
	public static void main(String[] args) throws Exception 
	{
		workdir = System.getProperty("user.dir") +"/workdir";
		cvsRootStr = ":pserver:reader:reader@10.17.249.20:/IWIS/cvs/IceAge";

//		Files.delete(workdir);
//		CVSUtil.checkout(cvsRootStr, "BPMBase", workdir);
//		CVSUtil.checkout(cvsRootStr, "BPMProcesses/business/general", workdir);
//		CVSUtil.checkout(cvsRootStr, "BPMProcesses/business/prepaid", workdir);
//		CVSUtil.checkout(cvsRootStr, "BPMProcesses/business/postpaid", workdir);
		
		CoreRunner runner = new CoreRunner();
		runner.run("D:/mw/workspaces/osb1030/soa-analizer-test-ws", 
				"D:/mw/workspaces/osb1030/soa-analizer-test-ws/BPMBase/IWIS_Config.xml");
	}

}
