package avea.bpm.codeanalyzer.finder.cmd;

import java.util.HashMap;

public interface Command {

	public void execute() throws Exception;
	
	public void execute(HashMap parameterMap) throws Exception;
}
