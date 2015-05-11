package com.tomecode.soa.ora.suite11g.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.tools.ReferencedBy;
import com.tomecode.soa.ora.suite11g.project.sca.CompositeService;
import com.tomecode.soa.ora.suite11g.project.sca.ScaComponent;
import com.tomecode.soa.ora.suite11g.project.sca.ScaService;
import com.tomecode.soa.ora.suite11g.project.sca.Wire;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.services.jca.JCABase;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * Oracle 11g Composite process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Ora11gComposite extends ReferencedBy {

	private Project project;
	/**
	 * composite name
	 */
	private final String name;
	/**
	 * composite file
	 */
	private final File file;

	/**
	 * list of composite services in project
	 */
	private final List<CompositeService> compositeServices;

	private final List<Wire> wires;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            composite name
	 * @param file
	 *            composite xml file
	 */
	public Ora11gComposite(String name, File file) {
		this.wires = new ArrayList<Wire>();
		this.compositeServices = new ArrayList<CompositeService>();
		this.name = name;
		this.file = file;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the file
	 */
	public final File getFile() {
		return file;
	}

	public final void addService(ScaService service) {
		service.setComposite(this);
		this.compositeServices.add(service);
		service.addReferencedByService(this);
	}

	public final List<CompositeService> getCompositeServices() {
		return compositeServices;
	}

	/**
	 * add new {@link ScaComponent} and set parent
	 * 
	 * @param component
	 */
	public final void addComponent(ScaComponent component) {
		if (component != null) {
			component.setComposite(this);
			this.compositeServices.add(component);
			component.addReferencedByService(this);
		}
	}

	/**
	 * @return the wires
	 */
	public final List<Wire> getWires() {
		return wires;
	}

	/**
	 * add new {@link Wire} connection
	 * 
	 * @param wire
	 */
	public final void addWire(Wire wire) {
		if (wire != null) {
			wires.add(wire);
		}
	}

	public final String toString() {
		return name;
	}

	public final boolean hasServices() {
		return !compositeServices.isEmpty();
	}

	public final void setProject(Project project) {
		this.project = project;
	}

	public final Project getProject() {
		return project;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
	 * 
	 * base interface for {@link BindingJCA} or {@link BindingWS}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public interface Binding {
	}

	/**
	 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
	 * 
	 * interface for set/get WS
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public interface BindingWS extends Binding {
	}

	/**
	 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
	 * 
	 * interface for get/set {@link JCABase} for jca binding
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public interface BindingJCA extends Binding {
		/**
		 * concrete JCA adapter
		 * 
		 * @return
		 */
		JCABase getJcaBase();

		/**
		 * seter for JCA adapter
		 * 
		 * @param jcaBase
		 */
		void setJcaBase(JCABase jcaBase);
	}

	@Override
	public String getObjectData() {
		// TODO Auto-generated method stub
		return name;
	}

	
	
	
}
