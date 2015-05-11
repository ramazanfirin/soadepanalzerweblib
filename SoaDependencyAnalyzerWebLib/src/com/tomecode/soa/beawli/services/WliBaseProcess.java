package com.tomecode.soa.beawli.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
//import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tools.EndpointList;
import com.tomecode.soa.dependency.analyzer.tools.ReferencedBy;
import com.tomecode.soa.dependency.analyzer.tools.ToolEndpointList;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.suite11g.project.sca.ScaComponent;
import com.tomecode.soa.wsdl.Wsdl;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * BPEL process in SOA Suite 11g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "Wli Process...", type = "Wli Process...", parentMethod = "getWorkpsace")
public final class WliBaseProcess extends ReferencedBy implements ImageFace, ToolTip, ToolEndpointList {
	/**
	 * version of Wli process
	 */
	private String version;
	
	//@PropertyViewData(title = "Name")
	private String name;
	
	
	//@PropertyViewData(title = "returnCodes")
	private String returnCodes;
	
	//@PropertyViewData(title = "iwisConfigVariables")
	private List<String> iwisConfigVariables;
	
	
	//@PropertyViewData(title = "Input")
	private String input;
	

	//@PropertyViewData(title = "Output")
	private String output;

	/**
	 * 
	 * all service called in process
	 */
	private final List<WliBaseService> wliServices;
	
	private final List<WliBaseProcess> referenceProcesses;
	
	/**
	 * Constructor
	 * 
	 * @param name
	 *            BPEL process name
	 * @param version
	 *            process version
	 * @param file
	 *            process file
	 */
	public WliBaseProcess(String name) {	
		this.name = name;
		this.wliServices = new ArrayList<WliBaseService>();
		this.referenceProcesses = new ArrayList<WliBaseProcess>();
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public final void setVersion(String version) {
		this.version = version;
	}

	public final String getVersion() {
		return version;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_11G_EVENT;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: 		").append(getType());
		return sb.toString();
	}
	
	@Override
	public final String getType() {
		return "Oracle Wli 8.1g -BPM Process";
	}

	public final void addWliProcessReference(WliBaseProcess wliProcess) {
		referenceProcesses.add(wliProcess);
		wliProcess.addReferencedByService(this);
	}
	
	public final void addService(WliBaseService service) {
		wliServices.add(service);
		service.addReferencedByService(this);
	}

	public List<WliBaseService> getWliServices() {
		return wliServices;
	}

	public List<WliBaseProcess> getReferenceProcesses() {
		return referenceProcesses;
	}

	@Override
	public void calculateEndpointList(EndpointList endpointList) {
		// TODO Auto-generated method stub
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}

	public String getReturnCodes() {
		return returnCodes;
	}

	public void setReturnCodes(String returnCodes) {
		this.returnCodes = returnCodes;
	}

	public List<String> getIwisConfigVariables() {
		return iwisConfigVariables;
	}

	public void setIwisConfigVariables(List<String> iwisConfigVariables) {
		this.iwisConfigVariables = iwisConfigVariables;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	
	
	
	

}
