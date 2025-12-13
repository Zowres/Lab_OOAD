package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
		String query = "UPDATE service SET serviceName = ?, serviceDescription = ?, servicePrice = ?, serviceDuration = ? WHERE serviceID = ?";

	    try {
	        PreparedStatement ps = Connect.getInstance().preparedStatement(query);

	        ps.setString(1, name);
	        ps.setString(2, description);
	        ps.setDouble(3, price);
	        ps.setInt(4, duration);
	        ps.setInt(5, serviceID);

	        int affectedRows = ps.executeUpdate();

	        if (affectedRows > 0) {
	            System.out.println("Service updated successfully!");
	        } else {
	            System.out.println("No service found with ID: " + serviceID);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteService(Integer serviceID) {
		String query = "DELETE FROM services WHERE serviceID = ?";
		try {
	        PreparedStatement ps = Connect.getInstance().preparedStatement(query);
	        ps.setInt(1, serviceID);

	        int affectedRows = ps.executeUpdate();

	        if (affectedRows > 0) {
	            System.out.println("Service deleted successfully!");
	        } else {
	            System.out.println("No service found with ID: " + serviceID);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Service> getAllServices(){
		List<Service> list = new ArrayList<Service>();

	    String query = "SELECT * FROM services";

	    try {
	        PreparedStatement ps = Connect.getInstance().preparedStatement(query);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Service s = new Service();

	            s.setServiceID(rs.getInt("serviceID"));
	            s.setServiceName(rs.getString("name"));
	            s.setServiceDescription(rs.getString("description"));
	            s.setServicePrice(rs.getDouble("price"));

	            int seconds = rs.getInt("duration_seconds");
	            int days = seconds / (24 * 60 * 60);
	            s.setServiceDuration(days);

	            list.add(s);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	
}
