package com.tomecode.soa.ora.suite11g.project.sca.component.bpel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
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
@PropertyGroupView(title = "Oracle SOA Suite 11g - BPEL Process")
public final class Ora11gBpelProcess extends ScaComponent {
	/**
	 * version of BPEL process
	 */
	private String version;

	/**
	 * tree structure of BPEL process
	 */
	private final Ora11gBpelStructure bpelStructure;

	/**
	 * 
	 * all references or partnerlinks in BPEL process
	 */
	private final List<Ora11gBpelReference> bpelReferences;

	private final List<Ora11gBpelActivityDependency> activityDependencies;
	/**
	 * BPEL project WSDL
	 */
	private Wsdl wsdl;

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
	public Ora11gBpelProcess(String name, File file) {
		super(name, file);
		this.bpelStructure = new Ora11gBpelStructure(this);
		this.bpelReferences = new ArrayList<Ora11gBpelReference>();
		this.activityDependencies = new ArrayList<Ora11gBpelActivityDependency>();
	}

	/**
	 * @return the bpelStructure
	 */
	public final Ora11gBpelStructure getStructure() {
		return bpelStructure;
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
		return ImageFactory.ORACLE_10G_BPEL_PROCESS;
	}

	@Override
	public final String getToolTip() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: 		").append(getType());
		sb.append("\nName:		").append(getName());
		sb.append("\nPath: 		").append(file != null ? file : "");
		return sb.toString();
	}
	
	@Override
	public final String getType() {
		return "Oracle SOA Suite 11g - BPEL Process";
	}

	/**
	 * @return the bpelReferences
	 */
	public final List<Ora11gBpelReference> getBpelReferences() {
		return bpelReferences;
	}

	public final void addBpelReference(Ora11gBpelReference bpelReference) {
		bpelReference.setBpelProcess(this);
		bpelReferences.add(bpelReference);
	}

	public final List<Ora11gBpelActivityDependency> getActivityDependencies() {
		return activityDependencies;
	}

	public final void addActitivtyDependency(Ora11gBpelActivityDependency activityDependency) {
		this.activityDependencies.add(activityDependency);
	}

	/**
	 * @return the {@link Wsdl}
	 */
	public final Wsdl getWsdl() {
		return wsdl;
	}

	/**
	 * @param wsdl
	 *            the {@link Wsdl} to set
	 */
	public final void setWsdl(Wsdl wsdl) {
		this.wsdl = wsdl;
	}

}
