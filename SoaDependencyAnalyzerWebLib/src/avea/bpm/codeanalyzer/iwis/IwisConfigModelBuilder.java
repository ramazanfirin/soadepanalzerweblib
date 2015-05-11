package avea.bpm.codeanalyzer.iwis;

import java.io.IOException;

import tr.com.avea.iwisConfig.CONFIGDocument;
import tr.com.avea.iwisConfig.ProdTest;
import tr.com.avea.iwisConfig.CONFIGDocument.CONFIG;
import tr.com.avea.iwisConfig.CONFIGDocument.CONFIG.CONVERSIONS.CONVERSION;
import tr.com.avea.iwisConfig.CONFIGDocument.CONFIG.CONVERSIONS.CONVERSION.ATTRIBUTE;
import tr.com.avea.iwisConfig.CONFIGDocument.CONFIG.IWISCLIENTS.IWISCLIENT;
import tr.com.avea.iwisConfig.CONFIGDocument.CONFIG.IWISSERVICES.IWISSERVICE;
import tr.com.avea.iwisConfig.CONFIGDocument.CONFIG.SERVICEPOOLS.SERVICEPOOL;
import avea.bpm.codeanalyzer.iwis.model.Attribute;
import avea.bpm.codeanalyzer.iwis.model.Client;
import avea.bpm.codeanalyzer.iwis.model.Conversion;
import avea.bpm.codeanalyzer.iwis.model.Pool;
import avea.bpm.codeanalyzer.iwis.model.Service;
import avea.bpm.codeanalyzer.iwis.model.factory.ClientFactory;
import avea.bpm.codeanalyzer.iwis.model.factory.ConversionFactory;
import avea.bpm.codeanalyzer.iwis.model.factory.PoolFactory;
import avea.bpm.codeanalyzer.iwis.model.factory.ServiceFactory;
import avea.bpm.codeanalyzer.util.CollectionUtil;
import avea.bpm.codeanalyzer.util.ObjectFilter;

import com.bea.xml.XmlException;

public class IwisConfigModelBuilder 
{
	private ClientFactory clientFactory;
	private ServiceFactory serviceFactory;
	private ConversionFactory conversionFactory;
	private PoolFactory poolFactory;
	
	public IwisConfigModelBuilder() {
		this.clientFactory = new ClientFactory();
		this.serviceFactory = new ServiceFactory();
		this.conversionFactory = new ConversionFactory();
		this.poolFactory = new PoolFactory();
	}
	
	public IwisConfigModel build(String fileName) throws IOException, XmlException 
	{
		IwisConfigModel configModel = new IwisConfigModel();
		
		CONFIGDocument document = IwisConfigReader.readIwisConfig(fileName);
		CONFIG config = document.getCONFIG();
		
		generateClients(configModel, config);
		generateConversions(configModel, config);
		generatePools(configModel, config);
		generateServices(configModel, config);
		
		//TODO do build here
		
		return configModel;
	}

	private void generatePools(IwisConfigModel configModel, CONFIG config) {
		SERVICEPOOL[] servicepools = config.getSERVICEPOOLS().getSERVICEPOOLArray();
		for (int i = 0; i < servicepools.length; i++) {
			SERVICEPOOL servicepool = servicepools[i];
			
			Pool pool = (Pool) this.poolFactory.instanciate(servicepool.getName(), servicepool.getDescription());
			
			configModel.addPool(pool);
		}
	}

	private void generateConversions(IwisConfigModel configModel, CONFIG config) {
		CONVERSION[] conversions = config.getCONVERSIONS().getCONVERSIONArray();
		for (int i = 0; i < conversions.length; i++) {
			CONVERSION iwisconversion = conversions[i];
			
			Conversion conversion = (Conversion) this.conversionFactory.instanciate(iwisconversion.getName(), iwisconversion.getDescription());
			for (int j = 0; j < iwisconversion.getATTRIBUTEArray().length; j++) {
				ATTRIBUTE convAttribute = iwisconversion.getATTRIBUTEArray()[j];
			
				Attribute attribute = new Attribute();
				attribute.setName(convAttribute.getName());
				attribute.setType(convAttribute.getType());
				attribute.setStringValue(convAttribute.getStringValue());
				
				conversion.addAttribute(attribute);
			}
			
			configModel.addConversion(conversion);
		}
	}

	private void generateServices(IwisConfigModel configModel, CONFIG config) {
		IWISSERVICE[] iwisservices = config.getIWISSERVICES().getIWISSERVICEArray();
		for (int i = 0; i < iwisservices.length; i++) {
			final IWISSERVICE iwisservice = iwisservices[i];
			
			if ( ProdTest.BOTH.equals( iwisservice.getProdtest() ) 
					|| ProdTest.TEST.equals( iwisservice.getProdtest() )) {
				Service service = (Service) this.serviceFactory.instanciate(iwisservice.getName(), iwisservice.getStringValue());
				service.setVersion(iwisservice.getDescription());
				service.setDetailedService(iwisservice.getDservice());
	
				ObjectFilter poolFilter = new ObjectFilter() {
					public boolean accept(Object object) {
						Pool pool = (Pool)object;
						String name = pool.getName();
						String servicePoolName = iwisservice.getPool();
						if ( name != null && servicePoolName!= null && name.equals(servicePoolName) )
							return true;
						return false;
					}
				
				};
				Pool pool = (Pool) CollectionUtil.findByFilter(configModel.getPools(), poolFilter);
				service.setPool(pool);
				
				ObjectFilter inFilter = new ObjectFilter() {
					public boolean accept(Object object) {
						Conversion conversion = (Conversion)object;
						String name = conversion.getName();
						String in = iwisservice.getIn();
						if ( name != null && in != null && name.equals(in) )
							return true;
						return false;
					}
				};
				Conversion in = (Conversion) CollectionUtil.findByFilter(configModel.getConversions(), inFilter);
				service.setIn(in);
				in.addService(service);
				
				ObjectFilter outFilter = new ObjectFilter() {
					public boolean accept(Object object) {
						Conversion conversion = (Conversion)object;
						String name = conversion.getName();
						String out = iwisservice.getOut();
						if ( name != null && out != null && name.equals(out) )
							return true;
						return false;
					}
				};
				Conversion out = (Conversion) CollectionUtil.findByFilter(configModel.getConversions(), outFilter);
				service.setOut(out);
				out.addService(service);
				
				configModel.addService(service);
			}
		}
	}

	private void generateClients(IwisConfigModel configModel, CONFIG config) {
		IWISCLIENT[] iwisclients = config.getIWISCLIENTS().getIWISCLIENTArray();
		for (int i = 0; i < iwisclients.length; i++) {
			IWISCLIENT iwisclient = iwisclients[i];
			
			Client client = (Client) this.clientFactory.instanciate(iwisclient.getName(), iwisclient.getDescription());
			
			configModel.addClient(client);
		}
	}
	
	public static void main(String[] args) throws IOException, XmlException {
		IwisConfigModelBuilder builder = new IwisConfigModelBuilder();
		IwisConfigModel model = builder.build("schemas/IWIS_config.xml");
		System.out.println("services: "+model.queryServiceByName("BPM_", null, null).size());
	}
}
