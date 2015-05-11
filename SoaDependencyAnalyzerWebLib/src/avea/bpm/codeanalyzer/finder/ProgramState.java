package avea.bpm.codeanalyzer.finder;

import java.util.Collection;
import java.util.TreeSet;

public interface ProgramState 
{

	public TreeSet getProcessSet();

	public Collection getCctrlMethods();

	public Collection getBctrlMethods();

	public Collection getBusinessControls();

	public Collection getCoreControls();
}
