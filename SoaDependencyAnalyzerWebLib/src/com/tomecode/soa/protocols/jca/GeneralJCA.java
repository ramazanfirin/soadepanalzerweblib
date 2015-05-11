package com.tomecode.soa.protocols.jca;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.ora.osb10g.services.OSBService;
import com.tomecode.soa.protocols.Node;
import com.tomecode.soa.services.jca.JCABase;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * General - wrapper class for {@link JCABase} - it's helper class, for
 * visualizing dependencies
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
@PropertyGroupView(title = "JCA Dadapter", parentMethod = "getParent")
public final class GeneralJCA implements ImageFace, Node<GeneralJCA>, ToolTip {

	/**
	 * list of {@link JCAResourceJndi}
	 */
	protected final List<JCAResourceJndi> jcaResourceJndis = new ArrayList<JCAResourceJndi>();

	//@PropertyViewData(title = "JCA Adapter Type: ")
	private JCAAdapterType jcaAdapterType;

	//@PropertyViewData(title = "Name: ")
	private String name;

	// TODO: zobrazit detailne informacie o datach
	private JCABase jcaBase;

	/**
	 * parent service
	 */
	private Object parentService;

	public GeneralJCA() {
		this.jcaAdapterType = JCAAdapterType.UNKNOWN;
	}

	public GeneralJCA(String name) {
		this();
		this.name = name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (jcaBase == null) {
			if (small) {
				return ImageFactory.UNKNOWN_SERVICE_SMALL;
			}
			return ImageFactory.UNKNOWN_SERVICE;
		}
		return jcaBase.getImage(small);
	}

	@Override
	public final String getToolTip() {
		if (jcaBase == null) {
			return "Type: " + getType() + "\nName: " + name;
		}
		return jcaBase.getToolTip();
	}

	public final JCABase getJcaBase() {
		return jcaBase;
	}

	public final void setJcaBase(JCABase jcaBase) {
		this.jcaAdapterType = jcaBase.getAdapterType();
		this.jcaBase = jcaBase;
	}

	public final List<JCAResourceJndi> getChilds() {
		return jcaResourceJndis;
	}

	public final Object getParent() {
		return parentService;
	}

	public final void setParentService(OSBService parentService) {
		this.parentService = parentService;
	}

	@Override
	public final GeneralJCA getObj() {
		return this;
	}

	public final String toString() {
		if (jcaBase == null) {
			return name;
		}
		return jcaBase.toString();
	}

	public final void addJCAResourceJndi(String uri) {
		uri = uri.replace("jca://", "");
		if (!existsJndi(uri)) {
			jcaResourceJndis.add(new JCAResourceJndi(uri, this));
		}
	}

	private final boolean existsJndi(String jndi) {
		for (JCAResourceJndi jcaResourceJndi : jcaResourceJndis) {
			if (jcaResourceJndi.getName().equals(jndi)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final String getType() {
		if (jcaBase != null) {
			return jcaBase.getAdapterType().getName();
		}
		return jcaAdapterType.getName();
	}
}
