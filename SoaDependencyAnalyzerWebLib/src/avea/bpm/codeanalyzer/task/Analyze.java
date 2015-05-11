package avea.bpm.codeanalyzer.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import avea.bpm.codeanalyzer.runner.CoreRunner;

public class Analyze extends Task
{
	private static String workdir;
	private static String iwisConfigFilePath;
	
	public Analyze(){
	}
	
	public void execute() throws BuildException {

		CoreRunner coreRunner = new CoreRunner();
		try {
			coreRunner.run(workdir, iwisConfigFilePath);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
		
		super.execute();
	}

	public static String getWorkdir() {
		return workdir;
	}

	public static void setWorkdir(String userDir) {
		Analyze.workdir = userDir;
	}

	public static String getIwisConfigFilePath() {
		return iwisConfigFilePath;
	}

	public static void setIwisConfigFilePath(String iwisConfigFilePath) {
		Analyze.iwisConfigFilePath = iwisConfigFilePath;
	}
}
