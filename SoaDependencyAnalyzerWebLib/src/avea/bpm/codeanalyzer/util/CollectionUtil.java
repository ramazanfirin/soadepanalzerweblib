package avea.bpm.codeanalyzer.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import avea.bpm.codeanalyzer.model.base.BasePojo;

public class CollectionUtil {

	public static List filterCollection(Collection collection, ObjectFilter filter){
		ArrayList list = new ArrayList();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			Object object = (Object) iter.next();
			if ( filter.accept(object) )
				list.add(object);
		}
		return list;
	}
	
	public static Object findByFilter(Collection collection, ObjectFilter filter){
		Object foundObject  = null;
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			Object object = (Object) iter.next();
			if ( filter.accept(object) ) {
				foundObject = object;
				break;
			}
		}
		return foundObject;
	}
	
	public static HashMap putAllInHashMap(Collection collection) {
		HashMap hashMap = new HashMap();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			BasePojo basePojo = (BasePojo) iter.next();
			hashMap.put(new Integer(basePojo.getId()), basePojo);
		}
		return hashMap;
	}

	public static List cloneCollectionUsingArrayList(Collection collection){
	    if (collection == null)
	        return new ArrayList();
	    else
	        return  new ArrayList(collection);
	}

	public static void putInHashMapByGivenAttribute(HashMap map, Object obj, String attribName) {
		try {
			Class class1 = obj.getClass();
			String methodName = "get"+Character.toUpperCase(attribName.charAt(0))+attribName.substring(1);
			Method method = class1.getMethod(methodName, new Class[0]);
			Object attributeValue = method.invoke(obj, new Object[0]);
			map.put(attributeValue, obj);
		} catch (Exception e) { e.printStackTrace(); } 
	}
	
	public static void putAllInHashMapByGivenAttribute(HashMap map, Collection col, String attribName){
	
		Iterator iter = col.iterator();
		while ( iter.hasNext() )
			putInHashMapByGivenAttribute(map, iter.next(), attribName);
	}
	
	public static HashMap putAllInHashMapByGivenAttribute(Collection col, String attribName){
		HashMap map = new HashMap();
		putAllInHashMapByGivenAttribute(map, col, attribName);
		return map;
	}
	
	public static List filterCollectionWithAttributeValue(Collection col, String attribName, Object attribCompareVal){
		
		ArrayList list = new ArrayList(col);
		ArrayList filteredCol = new ArrayList();
		
		try 
		{
			Object obj;
			Class class1;
			Method method;
			
			if ( list.size() > 0 ) {
				obj = list.get(0);
				class1 = obj.getClass();
				String methodName = "get"+Character.toUpperCase(attribName.charAt(0))+attribName.substring(1);
				method = class1.getMethod(methodName, new Class[0]);

				for (int i=0; i<list.size(); i++) {
					obj = list.get(i);
					
					Object attributeValue = method.invoke(obj, new Object[0]);
					if ( attributeValue.equals(attribCompareVal) )
						filteredCol.add(obj);
				}
			}
		} 
		catch (Exception e) { e.printStackTrace();}
		
		return filteredCol;
	}

	public static String[] generateStringValues(Collection col, String attribName) {
		ArrayList list = new ArrayList(col);
		String[] values = new String[list.size()];
		
		try 
		{
			Object obj;
			Class class1;
			Method method;
			
			if ( list.size() > 0 ) {
				obj = list.get(0);
				class1 = obj.getClass();
				String methodName = "get"+Character.toUpperCase(attribName.charAt(0))+attribName.substring(1);
				method = class1.getMethod(methodName, new Class[0]);

				for (int i=0; i<list.size(); i++) {
					obj = list.get(i);
					
					Object attributeValue = method.invoke(obj, new Object[0]);
					values[i] = attributeValue.toString();
				}
			}
		} 
		catch (Exception e) { e.printStackTrace();}
		
		return values;
	}

	public static String generateInStrForInteger(String[] strValues) {
		return generateInStr(strValues, "");
	}
	
	public static String generateInStr(String[] values) {
		return generateInStr(values, "'");
	}

	private static String generateInStr(String[] values, String quote) {
		
		StringBuffer buffer = new StringBuffer("( ");
		int i;
		for (i = 0; i < values.length - 1; i++)
			buffer.append( quote+values[i]+quote+", ");
		buffer.append( quote+values[i]+quote+" )");
		return buffer.toString();
	}
}
