/**
 * Created on Apr 8, 2003
 */
package avea.bpm.codeanalyzer.clazzloader;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javas.io.Streams;
import javas.lang.ClassLoaders;

/**
 * @author john
 */
public class JarClassLoader 
    extends ClassLoader
{
    /**
     * The list of jars we know about.
     */
    private final List internalJars = new LinkedList();
    
    /**
     * The Map of class Sets we know about.
     */
    private final Map classMaps = new HashMap();
    
	/**
	 * Constructor for JarJarClassLoader.
	 * @param parent
	 */
	public JarClassLoader(ClassLoader parent)
	{
		super(parent);
	}

	/**
	 * Constructor for JarJarClassLoader.
	 */
	public JarClassLoader()
	{
		super();
	}
    
    public void addInternalJar(String jar)
    {
        internalJars.add(jar);
    }
    
    /**
	 * @see java.lang.ClassLoader#findClass(String)
	 */
	protected Class findClass(String name) 
        throws ClassNotFoundException
	{
        Class theClass = null;
        
        try {
        	theClass = getClass().getClassLoader().loadClass(name);
		} catch (ClassNotFoundException e) {

	        if (null != name)
	        {        
	            for (Iterator itr = internalJars.iterator(); itr.hasNext();)
	    		{
	    			String jar = (String) itr.next();
	    			
	                String filename = ClassLoaders.convertClassNameToFilename(name);
	                
	                if (null != (theClass = defineClass(name, jar, filename)))
	                {
	                    break;
	                }
	    		}
	        }
	        
	        if (null == theClass)
	        {
	            throw new ClassNotFoundException(name);
	        }
		}
		return theClass;
	}
    
    private Class defineClass(String className, String jar, String filename)
    {
        Class theClass = null;
        
        Map classMap = (Map)classMaps.get(jar);
        
        if (null == classMap)
        {
            classMap = createClassMapForJar(jar);
            
            if (null != classMap)
            {
                classMaps.put(jar, classMap);
            }            
        }    
        
        if (null != classMap)
        {
            byte[] classBytes = (byte[])classMap.get(filename);
            
            if (null != classBytes)
            {
                classMap.remove(filename);
                
                if (classMap.size() <= 0)
                {
                    removeClassMap(classMap);
                }
                
                theClass = defineClass(className, classBytes, 0, classBytes.length);
            }
        }
        
        return theClass;
    }
    
    private void removeClassMap(Map classMap)
    {
        // TODO: Implement me.
    }

	private Map createClassMapForJar(String jar)
	{
        Map map = new HashMap();
        
        try
        {
        	InputStream is = new FileInputStream(jar);
            
            if (null != is)
            {
                JarInputStream jis = new JarInputStream(is);
                
                JarEntry entry;
                
                while (null != (entry = jis.getNextJarEntry()))
                {
                    String filename = entry.getName();
                    
                    // Technically a bug here if we have class files bigger than Integer.MAX_VALUE
                    // but uh... yeah, right...
                    
                    int size = (int)entry.getSize();
                    byte[] data;
                    
                    if (size <= 0)
                    {
                        data = Streams.readByteArray(jis);
                    }
                    else
                    {
                        data = Streams.readByteArray(jis, size);
                    }
                    
                    if (null != data)
                    {
                        map.put(filename, data);
                    }
                }
                
                jis.close();
            }
        }
        catch (IOException suppressed) {}
        
		return map;
	}

	public InputStream getResourceAsStream(String name) {
		InputStream stream = super.getResourceAsStream(name);
		if ( stream == null )
		{
			Iterator mapIter = this.classMaps.values().iterator();
			while ( mapIter.hasNext() ) {
				Map classMap = (Map) mapIter.next();
				byte[] bytes = (byte[])classMap.get(name);
				if ( bytes != null ) {
					stream = new ByteArrayInputStream(bytes);
					break;
				}
			}
		}
		return stream;
	}
}
