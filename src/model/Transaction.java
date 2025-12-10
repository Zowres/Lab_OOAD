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
	
	
	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public Integer getServiceID() {
		return serviceID;
	}

	public void setServiceID(Integer serviceID) {
		this.serviceID = serviceID;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	public Integer getReceptionistID() {
		return receptionistID;
	}

	public void setReceptionistID(Integer receptionistID) {
		this.receptionistID = receptionistID;
	}

	public Integer getLaundryStaffID() {
		return laundryStaffID;
	}

	public void setLaundryStaffID(Integer laundryStaffID) {
		this.laundryStaffID = laundryStaffID;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getTransactionNotes() {
		return transactionNotes;
	}

	public void setTransactionNotes(String transactionNotes) {
		this.transactionNotes = transactionNotes;
	}

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
