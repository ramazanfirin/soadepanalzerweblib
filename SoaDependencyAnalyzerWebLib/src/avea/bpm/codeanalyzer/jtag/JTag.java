package avea.bpm.codeanalyzer.jtag;


public class JTag 
{
	private String name;
	private String fragmentsStr;
	
	public JTag(){
	}
//	
//	public static JTag parse(String tagStr) throws JTagParseException {
//		tagStr = tagStr.trim();
//		JTag tag = new JTag();
//		int indexOfSpace = tagStr.indexOf(" ");
//		if ( indexOfSpace == -1 ) {
//			String name = tagStr.substring(1);
//			tag.tagName = name;
//		}
//		else {
//			String name = tagStr.substring(1, indexOfSpace);
//			tag.tagName = name;
//			
//			String fragmentStr = tagStr.substring(indexOfSpace).trim();
//			if ( fragmentStr.length() == 0 )
//				return tag;
//			
//			int indexOfEqual = fragmentStr.indexOf("=");
//			int indexOfDoubleColon = fragmentStr.indexOf("::");
//			if ( indexOfEqual == -1 && indexOfDoubleColon == -1 )
//				throw new JTagParseException("Comments are not supported >"+fragmentStr+"<");
//			
//			while ( fragmentStr.length() > 0 ) 
//			{
//				String attribName;
//				String attribValue;
//				
//				if ( indexOfDoubleColon == -1 || 
//						(indexOfEqual != -1 && indexOfEqual < indexOfDoubleColon) ) 
//				{
//					int indexOfSecondDoubleQuote = fragmentStr.indexOf("\"", indexOfEqual);
//					indexOfSecondDoubleQuote = fragmentStr.indexOf("\"", indexOfSecondDoubleQuote+1);
//					attribName = fragmentStr.substring(0, indexOfEqual);
//					attribValue = fragmentStr.substring(indexOfEqual+1, indexOfSecondDoubleQuote).trim();
//					attribValue = attribValue.substring(1, attribValue.length());
//					fragmentStr = fragmentStr.substring(indexOfSecondDoubleQuote+1).trim();
//				}
//				else if ( indexOfEqual == -1 || 
//						(indexOfDoubleColon != -1 && indexOfDoubleColon < indexOfEqual) )
//				{
//					int indexOfSecondDoubleColon = fragmentStr.indexOf("::", indexOfDoubleColon+1);
//					attribName = fragmentStr.substring(0, indexOfDoubleColon);
//					attribValue = fragmentStr.substring(indexOfDoubleColon+2, indexOfSecondDoubleColon);
//					fragmentStr = fragmentStr.substring(indexOfSecondDoubleColon+2).trim();
//				}
//				else 
//					throw new JTagParseException("Comments are not supported >"+fragmentStr+"<");
//	
//				tag.attributeMap.put(attribName, attribValue);
//				indexOfEqual = fragmentStr.indexOf("=");
//				indexOfDoubleColon = fragmentStr.indexOf("::");
//			}
//		}
//		return tag;
//	}

	public String getName() {
		return this.name;
	}

	public String getFragmentsStr() {
		return fragmentsStr;
	}

	public void setFragmentsStr(String fragments) {
		this.fragmentsStr = fragments;
	}

	public void setName(String tagName) {
		this.name = tagName;
	}

}
