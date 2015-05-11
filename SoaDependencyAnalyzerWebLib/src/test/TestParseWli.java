package test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import avea.bpm.codeanalyzer.runner.CoreRunner;

public class TestParseWli {

	public static void main(String[] args) {
		
		CoreRunner runner = new CoreRunner();
		try {
			Map<String,List> result = runner.run("D:/mw/workspaces/osb1030/soa-analizer-test-ws", "D:/mw/workspaces/osb1030/soa-analizer-test-ws/BPMBase/IWIS_Config.xml");
			System.out.println("bitti");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
