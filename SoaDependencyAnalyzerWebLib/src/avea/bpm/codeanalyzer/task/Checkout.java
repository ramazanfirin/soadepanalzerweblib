package avea.bpm.codeanalyzer.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import avea.bpm.codeanalyzer.logger.AnalyzerLogger;
import avea.bpm.codeanalyzer.util.CVSUtil;

public class Checkout extends Task {
	
	private String root;
	private String module;
	private String to;
	
	public Checkout(){
	}

	public void execute() throws BuildException {
		
		AnalyzerLogger.logInfo(getRoot());
		AnalyzerLogger.logInfo(module);
		
		try {
			//CVSUtil.checkout(root, module, to);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
		
		super.execute();
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
