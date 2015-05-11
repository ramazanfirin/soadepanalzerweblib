package avea.bpm.codeanalyzer.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil {
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);
	}

	public static String getLastToken(String str, String split) {
		StringTokenizer tokenizer = new StringTokenizer(str, split);
		String token = null;
		while ( tokenizer.hasMoreElements() )
			token = tokenizer.nextToken();
		
		return token;
	}
	
	public static boolean doesArrayContainsArray(String[] containerAr, String[] containedAr) 
	{
		boolean containsAll = true;
		for (int i = 0; i < containedAr.length; i++) {
			String contained = containedAr[i];
			boolean contains = false;
			for (int j = 0; j < containerAr.length; j++) {
				String container = containerAr[j];
				if ( contained.equals(container) ) {
					contains = true;
					break;
				}
			}
			if ( !contains ) {
				containsAll = false;
				break;
			}
		}
		return containsAll;
	}

	public static StringIndex[] findStringIndicessBetween(String content, String beforeString, String afterString, boolean trim) {
		ArrayList foundList = new ArrayList();
		int beforeIndex = content.indexOf(beforeString);
		while ( beforeIndex != -1 ) {
			int afterIndex = content.indexOf(afterString, beforeIndex+beforeString.length());
			String foundStr = content.substring(beforeIndex+beforeString.length(), afterIndex);
			if ( trim )
				foundStr = foundStr.trim();
			foundList.add(new StringIndex(foundStr, beforeIndex+beforeString.length()));
			beforeIndex = content.indexOf(beforeString, afterIndex+afterString.length());
		}
		StringIndex[] foundAr = new StringIndex[foundList.size()];
		foundList.toArray(foundAr);
		return foundAr;
	}
	
	public static String[] findStringsBetween(String content, String beforeString, String afterString, boolean trim) {
		StringIndex[] foundAr = findStringIndicessBetween(content, beforeString, afterString, trim);
		ArrayList foundList = new ArrayList();
		for (int i = 0; i < foundAr.length; i++) {
			StringIndex stringIndex = foundAr[i];
			foundList.add(stringIndex);
		}
		String[] foundStringAr = new String[foundList.size()];
		foundList.toArray(foundStringAr);
		return foundStringAr;
	}
	
	public static class StringIndex
	{
		private String string;
		private int index;

		public StringIndex(){
		}

		public StringIndex(String string, int index){
			setString(string);
			setIndex(index);
		}
		
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}
	}
	
	public static String listToString(List list, String seperator) {
		StringBuffer buffer = new StringBuffer();
		buffer.append( list.get(0) );
		for (int i=1; i<list.size(); i++) {
			buffer.append( seperator + list.get(i) );
		}
		return buffer.toString();
	}

	public static String collectionToDelimetedString(Collection collection, String delimeter) {
		StringBuffer buffer = new StringBuffer();
		ArrayList list = new ArrayList(collection);
		int i=0;
		for (; i<(list.size()-1); i++) {
			Object object = list.get(i);
			buffer.append(object+delimeter);
		}
		buffer.append(list.get(i));
		
		return buffer.toString();
	}
}
