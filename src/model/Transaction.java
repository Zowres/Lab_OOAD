package model;

import java.util.Date;
import java.util.List;

public class Transaction {
	private Integer transactionID;
	private Integer serviceID;
	private Integer customerID;
	private Integer receptionistID;
	private Integer laundryStaffID;
	private Date transactionDate;
	private String transactionStatus;
	private Double totalWeight;
	private String transactionNotes;
	
	public List<Transaction> getAllTransaction(){
		
		//return list
		return null;
	}
	
	public List<Transaction> getTransactionByStatus (String status){
		
		
		//return list
		return null;
	}
	
	public void updateTransactionStatus(String transactionID, String status) {
		
	}
	
	public List<Transaction> getAssignedOrderByLaundryStaffID(Integer LaundryStaffID) {
		
		
		
		//return list
		return null;
	}
	
	public void assignOrderToLaundryStaff(Integer transactionID,Integer receptionistID, Integer laundryStaffID) {
		
	}
	
	public void orderLaundryService(Integer serviceID,Integer customerID, Double totalWeight, String notes ) {
		
	}
	
	public List<Transaction> getTransactionsByCustomerID(Integer customerID){
		
		
		//return list
		return null;
	}
	
	
	
}
