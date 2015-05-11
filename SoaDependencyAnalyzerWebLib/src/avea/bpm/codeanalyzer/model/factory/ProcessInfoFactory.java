package avea.bpm.codeanalyzer.model.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;

import avea.bpm.codeanalyzer.jtag.JDoc;
import avea.bpm.codeanalyzer.jtag.JTag;
import avea.bpm.codeanalyzer.logger.AnalyzerLogger;
import avea.bpm.codeanalyzer.model.ProcessInfo;

public class ProcessInfoFactory {

	public static ProcessInfo generateInstance(JDoc doc) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, InstantiationException {
		ProcessInfo processInfo = new ProcessInfo();
		
		for (Iterator iter = doc.getTags().values().iterator(); iter.hasNext();) {
			JTag tag = (JTag) iter.next();
			String tagName = tag.getName(); 
			if ( tagName.startsWith( "@apd-") ) {
				String tAttributeStr = tagName.substring(5);
				StringTokenizer tokenizer = new StringTokenizer(tAttributeStr, "-");
				StringBuffer buffer = new StringBuffer();
				if ( tokenizer.hasMoreTokens() ) {
					buffer.append(tokenizer.nextToken());
					while ( tokenizer.hasMoreTokens() ) {
						String token = tokenizer.nextToken(); 
						buffer.append( token.substring(0, 1).toUpperCase(Locale.ENGLISH)+token.substring(1) );
					}
					PropertyUtils.setProperty(processInfo, buffer.toString(), tag.getFragmentsStr());
				}
			}
			else if ( tagName.startsWith("@apdx-") ) {
				if ( tagName.equals("@apdx-request-type") ) {
				}
				else if ( tagName.equals("@apdx-response-type") ) {
				}
				else if ( tagName.equals("@apdx-process-ref-no") ) {
					PropertyUtils.setProperty(processInfo, "processRefNo", tag.getFragmentsStr().trim());
				}
				else {
					AnalyzerLogger.logWarning("<unknown_process_tag> "+tag.getName()+" </unknown_process_tag>");
				}
			}
		}
		
		return processInfo;
	}

}
