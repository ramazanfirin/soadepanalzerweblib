package avea.bpm.codeanalyzer.util;

import java.util.ArrayList;

import avea.bpm.codeanalyzer.util.StringUtil.StringIndex;


public class JavaCodeUtil {

	public static String findPackage(String classStr)
	{
		int indexOfPackage = classStr.indexOf("package");
		int indexOfNextSemicolon = classStr.indexOf(";", indexOfPackage);
		
		return classStr.substring(indexOfPackage+7, indexOfNextSemicolon).trim();
	}

	public static String findControlId(String content) {
		String controlId = null;
		
		int indexOfControlIdMark = content.indexOf("Control ID =");
		if ( indexOfControlIdMark != -1) 
		{
			int indexOfNextNewline = content.indexOf("\n", indexOfControlIdMark);
			controlId = content.substring(indexOfControlIdMark+12, indexOfNextNewline).trim();
		}
		return controlId;
	}

	public static StringIndex findMethodContainigIndex(String content, int index) {
		int indexOfCommonOp = content.lastIndexOf("@common:operation", index);
		int indexOfFirstParanAfterCommonOp = content.indexOf("(", indexOfCommonOp);
		int indexOfMethodStart = content.lastIndexOf(" ", indexOfFirstParanAfterCommonOp);
		String methodName = content.substring(indexOfMethodStart, indexOfFirstParanAfterCommonOp).trim();
		while (methodName.length() == 0) {
			indexOfMethodStart = content.lastIndexOf(" ", indexOfMethodStart-1);
			methodName = content.substring(indexOfMethodStart, indexOfFirstParanAfterCommonOp).trim();
		}
		return new StringIndex(methodName, indexOfMethodStart);

	}

	public static StringIndex[] findMethodInvocations(String content, String controlVariableName) 
	{
		ArrayList methodInvocations = new ArrayList();
		StringIndex[] stringIndices = StringUtil.findStringIndicessBetween(content, controlVariableName+".", "(", true);
		for (int i = 0; i < stringIndices.length; i++) {
			StringIndex index = stringIndices[i];
			char ch = content.charAt(index.getIndex()-controlVariableName.length()-2);
			// Fix for control var names containing another var  name "smsCtrl" <> "bsmsCtrl"
			if ( !Character.isJavaIdentifierPart(ch) )
				methodInvocations.add(index);
		}
		StringIndex[] foundAr = new StringIndex[methodInvocations.size()];
		methodInvocations.toArray(foundAr);
		return foundAr;
	}

	public static String removeDoubleSlashedComments(String content) {
		// remove // commented lines
		return content.replaceAll("//[^\\n]{0,}\\n", "\n");
	}
}
