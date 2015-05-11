package avea.bpm.codeanalyzer.logger;

import org.apache.log4j.Logger;

import avea.bpm.codeanalyzer.finder.CodeAnalyzer;

public class AnalyzerLogger {
	
	private static AnalyzerLogger instance;
	
	private Logger logger;
	
	private AnalyzerLogger() {
		logger = Logger.getLogger(CodeAnalyzer.class);
	}
	
	private static synchronized AnalyzerLogger getInstance(){
		if ( instance == null )
			instance = new AnalyzerLogger();
		return instance;
	}
	
	public static void logInfo(String warning) {
		getInstance().logger.info(warning);
	}

	public static void logWarning(String warning) {
		getInstance().logger.warn(warning);
	}

	public static void logWarning(String warning, Throwable throwable) {
		getInstance().logger.warn(warning, throwable);
	}
	
	public static void logError(String error) {
		getInstance().logger.error(error);
	}

	public static void logError(String error, Throwable throwable) {
		getInstance().logger.error(error, throwable);
	}
}
