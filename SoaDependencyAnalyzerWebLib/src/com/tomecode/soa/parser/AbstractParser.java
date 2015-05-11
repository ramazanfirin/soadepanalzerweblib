package com.tomecode.soa.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Basic parser, for all parsers
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class AbstractParser {

	/**
	 * application file .jws
	 */
	public static final String ORACLE_SOA_SUITE_APPLIATION_FILE_EXTENSTION = ".jws";

	/**
	 * composite.xml in Oracle SOA Suite 11g
	 */
	public static final String ORACLE_SOA_SUITE_11G_COMPOSITE_XML = "composite.xml";

	/**
	 * parse file to XML
	 * 
	 * @param file
	 * @return
	 * @throws ServiceParserException
	 */
	protected final Element parseXml(File file) throws ServiceParserException {
		try {
			return parseXml(new FileInputStream(file));
		} catch (IOException e) {
			throw new ServiceParserException(e);
		}
	}

	/**
	 * parse file to XML
	 * 
	 * @param inputStream
	 * @return
	 * @throws ServiceParserException
	 */
	protected final Element parseXml(InputStream inputStream) throws ServiceParserException {
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(inputStream);
		} catch (DocumentException e) {
			throw new ServiceParserException(e);
		} catch (Exception e) {
			throw new ServiceParserException(e);
		}
		return document.getRootElement();
	}

	/**
	 * return file name without file extension
	 * 
	 * @param name
	 * @return
	 */
	public final String getNameWithouExtension(String name) {
		int index = name.lastIndexOf(".");
		if (index != -1) {
			return name.substring(0, index);
		}
		return name;
	}

	/**
	 * find file in folder/sub-folders with extension
	 * 
	 * @param folder
	 * @param extension
	 * @return
	 */
	protected final File findFileWithExcetension(File folder, String extension) {
		if (folder.isFile() && folder.getName().toLowerCase().endsWith(extension)) {
			return folder;
		}

		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					File extFile = findFileWithExcetension(file, extension);
					if (extFile != null) {
						return extFile;
					}
				}
				if (file.isFile() && file.getName().toLowerCase().endsWith(extension)) {
					return file;
				}
			}
		}
		return null;
	}

	/**
	 * find file by name
	 * 
	 * @param folder
	 * @param fileName
	 * @return
	 */
	protected final File findFile(File folder, String fileName) {
		if (fileName != null && fileName.trim().length() != 0) {
			fileName = fileName.toLowerCase();
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						File extFile = findFile(file, fileName);
						if (extFile != null) {
							return extFile;
						}
					}
					if (file.isFile() && file.getName().toLowerCase().equals(fileName)) {
						return file;
					}
				}
			}
		}
		return null;
	}

}
