package com.tomecode.soa.beawli.parser;

import java.io.File;

import com.tomecode.soa.beawli.services.WliBaseProcess;
import com.tomecode.soa.parser.AbstractParser;

public final class WLIBaseProxyParser extends AbstractParser
{
  
	public WliBaseProcess parseJcs(File file){
		return new WliBaseProcess("");
	}
}