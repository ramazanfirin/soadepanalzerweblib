package avea.bpm.codeanalyzer.iwis;
import java.io.IOException;

import tr.com.avea.iwisConfig.CONFIGDocument;
import avea.bpm.codeanalyzer.util.FileUtil;

import com.bea.xml.XmlException;


public class IwisConfigReader {
	
	static CONFIGDocument readIwisConfig(String iwisConfigFilePath) throws IOException, XmlException {
		String fileStr = FileUtil.readFile(iwisConfigFilePath);
		
		fileStr = fileStr.trim();
		fileStr = fileStr.replaceAll("<CONFIG", "<tns:CONFIG xmlns:tns=\"http://www.avea.com.tr/IWIS_Config/\" ");
		fileStr = fileStr.replaceAll("</CONFIG>", "</tns:CONFIG>");

		CONFIGDocument document = CONFIGDocument.Factory.parse(fileStr);
		return document;
	}

}
