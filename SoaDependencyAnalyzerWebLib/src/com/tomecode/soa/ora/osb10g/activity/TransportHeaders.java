package com.tomecode.soa.ora.osb10g.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: transport-headers
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class TransportHeaders extends OsbActivity {

	//@PropertyViewData(title = "Comment")
	private String comment;

	//@PropertyViewData(title = "Copy All")
	private boolean copyAll;
	//@PropertyViewData(title = "Direction")
	private String direction;
	//@PropertyViewData(title = "Transport Headers")
	private final List<Header> headers = new ArrayList<TransportHeaders.Header>();

	/**
	 * Constructor
	 * 
	 * @param copyAll
	 * @param comment
	 * @param direction
	 */
	public TransportHeaders(boolean copyAll, String comment, String direction) {
		this.copyAll = copyAll;
		this.comment = comment;
		this.direction = direction;
	}

	public final String getDirection() {
		return direction;
	}

	public final List<Header> getHeaders() {
		return headers;
	}

	public final void addHeader(Header header) {
		this.headers.add(header);
	}

	/**
	 * if it is true, then all headers will be copied
	 * 
	 * @return
	 */
	public final boolean isCopyAll() {
		return copyAll;
	}

	public final String getToolTip() {
		StringBuilder sb = new StringBuilder(super.getToolTip());
		if (direction != null) {
			sb.append("\nDirection: 		").append(direction);
		}
		sb.append("\nPass All Headers: 		").append(copyAll);
		if (comment != null) {
			sb.append("\nComment: 			").append(comment);
		}

		if (!copyAll) {
			for (Header header : headers) {
				sb.append("\nHeader: ").append(header.getName()).append("	Value: ").append(header.getValue());
			}
		}

		return sb.toString();
	}

	@Override
	public final void findUsesForOsbVariables(OsbVariableList osbVariableList) {
		for (OsbVariable osbVariable : osbVariableList.getOsbVariables()) {
			for (Header header : headers) {
				if (header.contiansVariable("$" + osbVariable.getVariable())) {
					osbVariable.addUsageActivty(this);
				}
			}
		}
		super.findUsesForOsbVariables(osbVariableList);
	}

	@Override
	public final void findOsbHeaders(HeaderInActivity osbHeader) {
		osbHeader.addActivity(this);
		super.findOsbHeaders(osbHeader);
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_TRANSPORTHEADERS;
	}

	@Override
	public final String getType() {
		return "Transport Headers";
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * header
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	@PropertyGroupView(title = "Header")
	public static final class Header implements Serializable{

		//@PropertyViewData(title = "Name")
		private String name;

		//@PropertyViewData(title = "Value")
		private HeaderAction value;

		//@PropertyViewData(title = "Expression", showWhenIsEmpty = false)
		private String xqueryText;

		/**
		 * Constructor
		 * 
		 * @param name
		 * @param value
		 * @param xqueryText
		 */
		public Header(String name, String value, String xqueryText) {
			this.name = name;
			if ("copy".equals(value)) {
				this.value = HeaderAction.COPY;
			} else if ("delete".equals(value)) {
				this.value = HeaderAction.DELETE;
			} else if ("expression".equals(value)) {
				this.value = HeaderAction.EXPRESSION;
			}
			this.xqueryText = xqueryText;
		}

		public final boolean contiansVariable(String osbVariable) {
			if (xqueryText != null) {
				if (xqueryText.contains(osbVariable)) {
					return true;
				}
			}
			return false;
		}

		public final String getName() {
			return name;
		}

		public final HeaderAction getValue() {
			return value;
		}

		public final String getXqueryText() {
			return xqueryText;
		}

	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * header set action
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public enum HeaderAction {
		COPY, DELETE, EXPRESSION;
	}
}
