package controller;

import java.util.List;

import model.Transaction;

public class TransactionController {
	public List<Transaction> getAllTransactions() {
		
		
		return null;
	}
	
	
	public List<Transaction> getTransactionByStatus(String status){
		
		
		return null;
	}
	
	public void updateTransactionStatus(Integer transactionID, String status) {
		
	}
	
	public List<Transaction> getAssignedOrdersByLaundryStaffID(Integer laundryStaffID) {
		
		
		return null;
	}
	
	public void assignOrderToLaundryStaff(Integer transactionID, Integer receptionistID, Integer laundryStaffID) {
		
	}
	
	public void orderLaundryService(Integer serviceID, Integer customerID, Double totalWeight, String notes) {
		
	}
	
	public List<Transaction> getTransactionsByCustomerID(Integer customerID){
		
		return null;
	}
	
	public String validateOrder(Double totalWeight, String notes) {
		
		
		
		return null;
	}
	
	
	
	
}
