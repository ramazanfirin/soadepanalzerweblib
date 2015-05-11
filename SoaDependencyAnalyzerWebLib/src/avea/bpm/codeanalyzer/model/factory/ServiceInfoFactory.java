package avea.bpm.codeanalyzer.model.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import avea.bpm.codeanalyzer.jtag.JDoc;
import avea.bpm.codeanalyzer.jtag.JTag;
import avea.bpm.codeanalyzer.model.ServiceInfo;

public class ServiceInfoFactory {

	public static ServiceInfo generateInstance(JDoc doc) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ServiceInfo serviceInfo = new ServiceInfo();
		
		for (Iterator iter = doc.getTags().values().iterator(); iter.hasNext();) {
			JTag tag = (JTag) iter.next();
			String tagName = tag.getName(); 
			if ( tagName.startsWith( "@asd-") ) {
				String tAttributeStr = tagName.substring(5);
				StringTokenizer tokenizer = new StringTokenizer(tAttributeStr, "-");
				StringBuffer buffer = new StringBuffer();
				if ( tokenizer.hasMoreTokens() ) {
					buffer.append(tokenizer.nextToken());
					while ( tokenizer.hasMoreTokens() ) {
						String token = tokenizer.nextToken(); 
						buffer.append( token.substring(0, 1).toUpperCase(Locale.ENGLISH)+token.substring(1) );
					}
					PropertyUtils.setProperty(serviceInfo, buffer.toString(), tag.getFragmentsStr());
				}
			}
		}
		
		return serviceInfo;
	}

	public static ServiceInfo generateInstance(JDoc doc, CompilationUnit compilationUnit, MethodDeclaration methodDeclaration) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, InstantiationException {
		ServiceInfo serviceInfo = generateInstance(doc);
		
		List parameters = methodDeclaration.parameters();
		if ( parameters.size() > 0 ) {
			StringBuffer inputArgsBuffer = new StringBuffer();
			for (Iterator iter = parameters.iterator(); iter.hasNext();) {
				SingleVariableDeclaration declaration = (SingleVariableDeclaration)iter.next();
				Type type = declaration.getType();

				inputArgsBuffer.append(type+" "+declaration.getName());
				inputArgsBuffer.append(", ");
			}
		}
		return serviceInfo;
	}
}
