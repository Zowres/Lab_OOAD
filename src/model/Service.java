package model;

import java.sql.PreparedStatement;
import java.util.List;

import database.Connect;

public class Service {
	private Integer serviceID;
	private String serviceName;
	private String serviceDescription;
	private Double servicePrice;
	private Integer serviceDuration;
	
	public Service() {
		
	}
	
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
		 String query = "INSERT INTO service (name, description, price, duration_seconds) VALUES (?, ?, ?, ?)";

		    try {
		        PreparedStatement ps = Connect.getInstance().preparedStatement(query);

		        ps.setString(1, name);
		        ps.setString(2, description);
		        ps.setDouble(3, price);
		        ps.setInt(4, duration);

		        ps.executeUpdate();

		        System.out.println("Service inserted successfully!");

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
	
	public void editService(Integer serviceID, String name, String description, Double price, Integer duration) {
		
	}
	
	public void deleteService(Integer serviceID) {
		
	}
	
	public List<Service> getAllServices(){
		
		
		
		return null;
	}
	
	
}
