package avea.bpm.codeanalyzer.jtag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TagElement;

import avea.bpm.codeanalyzer.logger.AnalyzerLogger;
import avea.bpm.codeanalyzer.util.StringUtil;

public class JDoc {
	
	private Javadoc javadoc;
	private HashMap tags;

	public JDoc(){
		this.tags = new HashMap();
	}
	
	public static JDoc getInstance(Javadoc javadoc) throws JTagParseException {
		JDoc doc = new JDoc();
		doc.javadoc = javadoc;
		List tags = javadoc.tags();
		for (Iterator iter = tags.iterator(); iter.hasNext();) {
			TagElement tagElement = (TagElement) iter.next();
			String tagName = tagElement.getTagName();
			String tagFragmentStr = StringUtil.listToString( tagElement.fragments(), " ").trim();
			if ( tagName != null ) {
				if ( JTagNameRegistry.isRegistered(tagName) ) {
					if ( tagFragmentStr.length() >0 && tagFragmentStr.charAt(0) == ':' ) {
						int indexOfSpace = tagFragmentStr.indexOf(' ');
						if ( indexOfSpace == -1 ) {
							tagName = tagName  + tagFragmentStr;
							tagFragmentStr = "";
						}
						else {
							tagName += tagFragmentStr.substring(0, indexOfSpace);
							tagFragmentStr = tagFragmentStr.substring(indexOfSpace);
						}
					}
					JTag tag = new JTag();
					tag.setName(tagName);
					tag.setFragmentsStr(tagFragmentStr);
					doc.tags.put(tag.getName(), tag);
				}
				else
					AnalyzerLogger.logWarning(
							"<unregistered>" +
								"<jtag>"+tagName+"</jtag>" +
								"<fragments>" +tagFragmentStr+ "</fragments>"+
							"</unregistered>");
			}
		}
		
		return doc;
	}

	public Javadoc getJavadoc() {
		return javadoc;
	}

	public HashMap getTags() {
		return tags;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator iter = getTags().values().iterator(); iter.hasNext();) {
			JTag tag = (JTag) iter.next();
			buffer.append( tag +"\n" );
		}
		
		return buffer.toString();
	}

	public JTag getTag(String tagName) {
		return (JTag) this.tags.get(tagName);
	}
}
