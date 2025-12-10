package model;

import java.util.List;

public class Service {
	private Integer serviceID;
	private String serviceName;
	private String serviceDescription;
	private Double servicePrice;
	private Integer serviceDuration;
	
	
	public Integer getServiceID() {
		return serviceID;
	}

	public void setServiceID(Integer serviceID) {
		this.serviceID = serviceID;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public Double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public Integer getServiceDuration() {
		return serviceDuration;
	}

	public void setServiceDuration(Integer serviceDuration) {
		this.serviceDuration = serviceDuration;
	}

	public void addService(String name, String description, Double price, Integer duration) {
		
	}
	
	public void editService(Integer serviceID, String name, String description, Double price, Integer duration) {
		
	}
	
	public void deleteService(Integer serviceID) {
		
	}
	
	public List<Service> getAllServices(){
		
		
		
		return null;
	}
	
	
}
