package avea.bpm.codeanalyzer.iwis;

import java.util.ArrayList;
import java.util.List;

import avea.bpm.codeanalyzer.iwis.model.Client;
import avea.bpm.codeanalyzer.iwis.model.Conversion;
import avea.bpm.codeanalyzer.iwis.model.Pool;
import avea.bpm.codeanalyzer.iwis.model.Service;
import avea.bpm.codeanalyzer.util.CollectionUtil;
import avea.bpm.codeanalyzer.util.ObjectFilter;
import avea.bpm.codeanalyzer.util.StringUtil;

public class IwisConfigModel 
{
	private List clients = new ArrayList();
	private List services = new ArrayList();
	private List conversions = new ArrayList();
	private List pools = new ArrayList();
	
	public IwisConfigModel(){
	}

	public boolean addService(Service service) {
		return services.add(service);
	}

	public boolean removeService(Service service) {
		return services.remove(service);
	}

	public boolean addClient(Client client) {
		return clients.add(client);
	}

	public boolean removeClient(Client client) {
		return clients.remove(client);
	}

	public boolean addConversion(Conversion conversion) {
		return conversions.add(conversion);
	}

	public boolean removeConversion(Conversion conversion) {
		return conversions.remove(conversion);
	}

	public boolean addPool(Pool pool) {
		return pools.add(pool);		
	}

	public boolean removePool(Pool pool) {
		return pools.remove(pool);
	}

	public List getClients() {
		return clients;
	}

	public List getConversions() {
		return conversions;
	}

	public List getPools() {
		return pools;
	}

	public List getServices() {
		return services;
	}

	public List queryServiceByName(final String start, final String in, final String end) 
	{
		List foundList = new ArrayList();
		
		if ( StringUtil.isNullOrEmpty(start)
			&& StringUtil.isNullOrEmpty(in)
			&& StringUtil.isNullOrEmpty(end) )
			return foundList;
		
//		for (Iterator iter = getServices().iterator(); iter.hasNext();) {
//			Service service = (Service) iter.next();
//			String name = service.getName();
//			if ( !StringUtil.isNullOrEmpty(name) ) {
//				if ( !StringUtil.isNullOrEmpty(start) ){
//					if ( name.startsWith(start) )
//						foundList.add(service);
//				}
//				else if ( !StringUtil.isNullOrEmpty(in) ) {
//					if ( name.contains(in) )
//						foundList.add(service);
//				}
//				else if ( !StringUtil.isNullOrEmpty(end) ) {
//					if ( name.endsWith(end) )
//						foundList.add(service);
//				}
//			}
//		}
		ObjectFilter filter = new ObjectFilter() {
			public boolean accept(Object object) {
				Service service = (Service)object;
				String name = service.getName();
				if ( !StringUtil.isNullOrEmpty(name) ) {
					if ( !StringUtil.isNullOrEmpty(start) && name.startsWith(start) )
						return true;
					else if ( !StringUtil.isNullOrEmpty(in) && name.indexOf(in) != -1 )
						return true;
					else if ( !StringUtil.isNullOrEmpty(end) && name.endsWith(end) )
						return true;
				}
				return false;
			}
		
		};
		
		foundList = CollectionUtil.filterCollection(getServices(), filter);
		return foundList;
	}
}
