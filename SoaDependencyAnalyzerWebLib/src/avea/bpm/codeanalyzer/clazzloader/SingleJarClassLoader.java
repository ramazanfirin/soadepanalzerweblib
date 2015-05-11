package avea.bpm.codeanalyzer.clazzloader;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javas.io.Streams;
import javas.lang.ClassLoaders;

/**
 * @author john
 */
public class SingleJarClassLoader 
    extends ClassLoader
{
    /**
     * The Map of class Sets we know about.
     */
    private Map classMap;
    
    private String jarPath;
    
	/**
	 * Constructor for JarJarClassLoader.
	 * @param parent
	 */
	public SingleJarClassLoader(ClassLoader parent)
	{
		super(parent);
	}

	/**
	 * Constructor for JarJarClassLoader.
	 */
	public SingleJarClassLoader()
	{
		super();
	}
    
    public SingleJarClassLoader(String jarPath) {
		super();
		this.jarPath = jarPath;
	}

	/**
	 * @see java.lang.ClassLoader#findClass(String)
	 */
	protected Class findClass(String name) 
        throws ClassNotFoundException
	{
        Class theClass = null;

        if (null != name)
        {        
            String filename = ClassLoaders.convertClassNameToFilename(name);
            
        	theClass = defineClass(name, jarPath, filename);
        }
        
        if (null == theClass)
        {
            throw new ClassNotFoundException(name);
        }
        
		return theClass;
	}
    
    private Class defineClass(String className, String jar, String filename)
    {
        Class theClass = null;
        
        if (null == classMap)
        {
            classMap = createClassMapForJar(jar);
        }    
        byte[] classBytes = (byte[])classMap.get(filename);
        
        if (null != classBytes)
        {
            classMap.remove(filename);
            
            theClass = defineClass(className, classBytes, 0, classBytes.length);
        }
        
        return theClass;
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
			byte[] bytes = (byte[])classMap.get(name);
			stream = new ByteArrayInputStream(bytes);
		}
		return stream;
	}
}
