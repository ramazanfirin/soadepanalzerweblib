package com.tomecode.soa.protocols.ejb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * EJB provider
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(title = "EJB Provider")
public final class EjbProvider implements ImageFace, Node<EjbProvider>, ToolTip {

	/**
	 * ejb provider name
	 */
	//@PropertyViewData(title = "Name: ")
	private String name;

	/**
	 * list of {@link EjbHome}
	 */
	private final List<EjbHome> ejbHomes = new ArrayList<EjbHome>();

	/**
	 * parent service
	 */
	private Object parentService;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            provider name
	 */
	public EjbProvider(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.EJB_PROVIDER_SMALL;
		}
		return ImageFactory.EJB_PROVIDER;
	}

	@Override
	public String getToolTip() {
		return "Type: "+getType()+"\nName: " + (name == null ? "" : name);
	}

	public final void addEjbHome(EjbHome ejbHome) {
		ejbHomes.add(ejbHome);
		ejbHome.setEjbProvider(this);
	}

	public final String toString() {
		return name;
	}

	public final void setParentService(OSBService parentService) {
		this.parentService = parentService;
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<EjbHome> getChilds() {
		return ejbHomes;
	}

	@Override
	public final EjbProvider getObj() {
		return this;
	}

	@Override
	public final String getType() {
		return "EJB Provider";
	}
}
