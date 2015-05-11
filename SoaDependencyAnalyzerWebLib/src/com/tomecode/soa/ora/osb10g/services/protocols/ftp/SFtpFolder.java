package com.tomecode.soa.ora.osb10g.services.protocols.ftp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.ToolTip;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * SFTP folder
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@PropertyGroupView(title = "SFTP Folder", parentMethod = "getParent")
public final class SFtpFolder implements ImageFace, Node<SFtpFolder>, ToolTip {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	/**
	 * path
	 */
	//@PropertyViewData(title = "Path:")
	private String name;

	/**
	 * parent server
	 */
	private SFtpServer ftpServer;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param ftpServer
	 */
	public SFtpFolder(String name, SFtpServer ftpServer) {
		this.name = name;
		this.ftpServer = ftpServer;
	}

	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	@Override
	public String getToolTip() {
		return "Type: " + getType() + "\nName: " + name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.FTP_FOLDER_SMALL;
		}
		return ImageFactory.FTP_FOLDER;
	}

	@Override
	public final SFtpServer getParent() {
		return ftpServer;
	}

	@Override
	public final List<Node<?>> getChilds() {
		return nodes;
	}

	@Override
	public final SFtpFolder getObj() {
		return this;
	}

	@Override
	public final String getType() {
		return "SFTP Folder";
	}

}
