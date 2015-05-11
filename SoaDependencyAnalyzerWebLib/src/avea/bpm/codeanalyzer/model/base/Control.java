package avea.bpm.codeanalyzer.model.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Control extends AbstractBasePojo implements Comparable
{
	private String name;
	private String pack;
	
	private List methods = new ArrayList();

	public Control() {
	}

	public Control(String name) {
		setName(name);
	}

	public Control(String name, String pack) {
		setName(name);
		setPack(pack);
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final boolean addMethod(Method method) {
		return methods.add(method);
	}

	public final boolean removeMethod(Method method) {
		return methods.remove(method);
	}
	
	public final Iterator methodsIterator() {
		return methods.iterator();
	}

	public final Method getMethodByName(String methodName) {
		Method foundMethod = null;
		for (Iterator iter = methodsIterator(); iter.hasNext();) {
			Method method = (Method) iter.next();
			if ( methodName.equals(method.getName()) ){
				foundMethod = method;
				break;
			}
		}
		return foundMethod;
	}

	public int compareTo(Object obj) {
		Control businessControl = (Control)obj;
		int cmp = getPack().compareTo(businessControl.getPack());
		if ( cmp == 0 )
			cmp = getName().compareTo(businessControl.getName());
		return cmp;
	}

	public String getPersistanceString() {
		return getId()+" "+getPack()+" "+getName();
	}

	public final String getQualifiedName() {
		return getPack()+"."+getName();
	}

	public String toString() {
		return getQualifiedName();
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}
}
