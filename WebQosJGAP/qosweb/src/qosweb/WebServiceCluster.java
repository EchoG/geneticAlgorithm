package qosweb;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WebServiceCluster {
	
	String name;
	List<WebService> services = new ArrayList<WebService>();
	
	public WebServiceCluster(String name) {
		super();
		this.name = name;
	}

	public List<WebService> getServices() {
		return services;
	}

	public void setServices(List<WebService> services) {
		this.services = services;
	}
	
	
}
