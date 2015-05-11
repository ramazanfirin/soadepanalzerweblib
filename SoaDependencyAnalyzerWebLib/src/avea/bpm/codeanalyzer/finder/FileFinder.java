package avea.bpm.codeanalyzer.finder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {


	public static List findProcessFiles(String path) 
	{
		return findFiles(path, ".jpd");
	}
	
	public static List findControlFiles(String path) 
	{
		return findFiles(path, "CtrlImpl.jcs");
	}
	
	public static List findFiles(String path, String fileSuffix) 
	{
		List fileList = new ArrayList();
		File rootDir = new File(path);
		if ( rootDir.isDirectory() )
		{
			findFiles(rootDir, fileList, fileSuffix);
		}
		return fileList;
	}

	private static void findFiles(File dir, List fileList, String fileSuffix) 
	{
		File fileAr[] = dir.listFiles();
		for (int i = 0; i < fileAr.length; i++) {
			File file = fileAr[i];
			if ( file.isDirectory() ) {
				findFiles(file, fileList, fileSuffix);
			}
			else if ( file.getName().endsWith(fileSuffix) ){
				fileList.add(file);
			}
		}
	}
}
