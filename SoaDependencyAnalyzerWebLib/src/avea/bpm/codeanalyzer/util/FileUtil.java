package avea.bpm.codeanalyzer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class FileUtil {

	public static String readFile(String filePath) throws IOException {
		return readFile( new File(filePath) );
	}
	public static String readFile(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		StringBuffer buffer = new StringBuffer();
		String line = reader.readLine();
		while ( line != null) {
			buffer.append("\n"+line);
			line = reader.readLine();
		}
		reader.close();
		return buffer.toString();
	}
	
	public static void writeListToFile(List list, String fileName) throws IOException {
		writeListToFile(list, new File(fileName));
	}
	
	public static void writeListToFile(List list, File file) throws IOException 
	{
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			String str = (String) iter.next();
			writer.println(str);
		}
		writer.close();
	}
	
	public static void writeFile(String fileName, String content) throws IOException 
	{
		writeFile(new File(fileName), content);
	}

	public static void writeFile(File file, String content) throws IOException 
	{
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.println(content);
		writer.close();
	}
	
	public static void findFolderContainingFolders(String path, String[] containedFolders, ArrayList paths) 
	{
		File file = new File(path);
		if ( file.isDirectory() ) {
			String[] containedFileNames = file.list();
			if ( StringUtil.doesArrayContainsArray(containedFileNames, containedFolders) )
				paths.add(path);
			else {
				for (int i = 0; i < containedFileNames.length; i++) {
					String fileName = containedFileNames[i];
					findFolderContainingFolders(path+"/"+fileName, containedFolders, paths);
				}
			}
		}
	}
	
	public static FileFilter getFileFilter(final String postfix) {
		return new FileFilter() {
			public boolean accept(File file) {
				return file.isFile() && file.getName().endsWith(postfix);
			}
		};
	}
	
	public static Collection findFilesInDirAndSubDirs(String dirPath, String suffix){
		return findFilesInDirAndSubDirs(new File(dirPath), suffix);
	}
	
	public static Collection findFilesInDirAndSubDirs(File dir, String suffix){
		ArrayList files = new ArrayList();
		 if ( dir.isDirectory() ) {
			 File[] filesAr = dir.listFiles( getFileFilter(suffix) );
			 files.addAll( Arrays.asList(filesAr) );
			 
			 File[] dirsAr = dir.listFiles( getDirectoryFilter() );
			 for (int i = 0; i < dirsAr.length; i++) {
				File subDir = dirsAr[i];
				files.addAll( findFilesInDirAndSubDirs(subDir, suffix) );
			}
		 }
		 return files;
	}
	public static FileFilter getDirectoryFilter() {
		return new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
	}

	public static FileFilter getDirectoryFilter(final String postfix) {
		return new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory() && file.getName().endsWith(postfix);
			}
		};
	}
	public static boolean isFileExisting(String jarPath) {
		return new File(jarPath).exists();
	}
}
