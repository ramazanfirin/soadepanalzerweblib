package avea.bpm.codeanalyzer.jtag;

import java.util.Iterator;
import java.util.TreeSet;

public class JTagNameRegistry 
{
	private static TreeSet registrySet;
	
	static {
		registrySet = new TreeSet();
		registrySet.add("@asd-");
		registrySet.add("@apd-");
		registrySet.add("@apdx-");

		registrySet.add("@common");
		registrySet.add("@jpd");
		registrySet.add("@jc");
	}
	
	private JTagNameRegistry(){
	}
	
	public static synchronized void register(String jtagName) {
		registrySet.add(jtagName);
	}
	
	public static synchronized void unRegister(String jtagName) {
		registrySet.remove(jtagName);
	}
	
	public static synchronized boolean isRegistered(String jtagName) {
		boolean result = false;
		for (Iterator iter = registrySet.iterator(); iter.hasNext();) {
			String tagName = (String) iter.next();
			if ( jtagName.startsWith(tagName) ) {
				result = true;
				break;
			}
		}
		return result;
	}
}