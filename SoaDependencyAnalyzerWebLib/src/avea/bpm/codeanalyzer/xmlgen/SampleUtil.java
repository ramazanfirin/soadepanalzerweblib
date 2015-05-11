package avea.bpm.codeanalyzer.xmlgen;

public class SampleUtil 
{

	public static Number getNumberInstance(Class clazz)
	{
		String clazzName = clazz.getName();
		return getNumberInstanceWithValue(clazzName, "0");
	}

	public static Number getNumberInstance(Class clazz, String value) {
		String clazzName = clazz.getName();
		return getNumberInstanceWithValue(clazzName, value);
	}
	
	private static Number getNumberInstanceWithValue(String clazzName, String value) {
		if ( "java.lang.Integer".equals( clazzName ) )
			return new Integer(value);
		else if ( "java.lang.Long".equals( clazzName ) )
			return new Long(value);
		else if ( "java.lang.Short".equals( clazzName ) )
			return new Short(value);
		else if ( "java.lang.Double".equals( clazzName ) )
			return new Double(value);
		else if ( "java.lang.Float".equals( clazzName ) )
			return new Float(value);
		else if ( "java.lang.Byte".equals( clazzName ) )
			return new Byte(value);
		else 
			return new ZeroNumber();
	}

	public static Object getPrimitiveNumberInstance(Class clazz)
	{
		if ( clazz.equals( Integer.TYPE ) ) {
			return new Integer(0);
		}
		else if ( clazz.equals( Long.TYPE ) ) {
			return new Long(0);
		}
		else if ( clazz.equals( Short.TYPE ) ) {
			return new Short((short)0);
		}
		else if ( clazz.equals( Double.TYPE ) ) {
			return new Double(0);
		}
		else if ( clazz.equals( Float.TYPE ) ) {
			return new Float(0);
		}
		else if ( clazz.equals( Byte.TYPE ) ) {
			return new Byte((byte)0);
		}
		else if ( clazz.equals( Boolean.TYPE ) ) {
			return new Boolean(false);
		}
		else {
			return null;
		}
	}
}
