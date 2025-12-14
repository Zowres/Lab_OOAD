package controller;

import java.util.List;

import model.Service;

public class ServiceController {
	Service serviceModel;
	
	
	public ServiceController() {
		this.serviceModel = new Service();
	}
	
	public void addService(String name, String description, Double price, Integer duration) {
		serviceModel.addService(name, description, price, duration);
	}
	
	public void editService(Integer serviceID, String name, String description, Double price, Integer duration) {
		List<Service> listService = getAllServices();
		
		for (Service service : listService) {
			if(service.getServiceID() == serviceID) {
				serviceModel.editService(serviceID, name, description, price, duration);
			}
			else {
				System.out.println("Gagal insert ke db");
				return;
			}
		}
		
	}
	public void deleteService(Integer serviceID) {
		serviceModel.deleteService(serviceID);
	}
	
	public List<Service> getAllServices(){
		
		return serviceModel.getAllServices();
	}
	
	public String validateAddService(String name, String description, Double price, Integer duration ) {
		if(name.isEmpty()) {
			return "Name must be filled!";
		}
		
		if(name.length() > 50 ) {
			return "Name length must be less than or equal to 50";
		}
		
		
		if(description.isEmpty()) {
			return "Description must be filled!";
		}
		
		if(description.length() > 250) {
			return "description length must be less than or equal to 250";
		}
		
		
		if(price < 0) {
			return "Price must greater than 0";
		}
		
		
		int minDuration = 1 * 24 * 60 * 60;     // 86400
	    int maxDuration = 30 * 24 * 60 * 60;    // 2592000
		
		
	    if(duration <= minDuration || duration >= maxDuration) {
	    	return "Duration must between 1 and 30 days";
	    }
		
		
	    addService(name,description,price,duration);
	    
		
		return "Service successfully inserted";
	}
	
	public String validateEditService(Integer serviceID, String name, String description, Double price, Integer duration) {
		if(name.isEmpty()) {
			return "Name must be filled!";
		}
		
		if(name.length() > 50 ) {
			return "Name length must be less than or equal to 50";
		}
		
		
		if(description.isEmpty()) {
			return "Description must be filled!";
		}
		
		if(description.length() > 250) {
			return "description length must be less than or equal to 250";
		}
		
		
		if(price < 0) {
			return "Price must greater than 0";
		}
		
		
		int minDuration = 1 * 24 * 60 * 60;     // 86400
	    int maxDuration = 30 * 24 * 60 * 60;    // 2592000
		
		
	    if(duration <= minDuration || duration >= maxDuration) {
	    	return "Duration must between 1 and 30 days";
	    }
		
	    editService(serviceID, name, description, price, duration);
		
		return "Service successfully edited";
	}
}
