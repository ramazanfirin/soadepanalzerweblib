package avea.bpm.codeanalyzer.model.factory;

import avea.bpm.codeanalyzer.model.Process;


public class ProcessFactory {
	
	public static Process generateInstanceByName(String name) {
		Process process = new Process();
		process.setName(name);
		return process;
	}
}
