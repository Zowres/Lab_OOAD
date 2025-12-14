package controller;

import java.util.List;

import model.Transaction;

public class TransactionController {
	Transaction transactionModel;
	
	public TransactionController() {
		this.transactionModel = new Transaction();
	}
	
	
	public List<Transaction> getAllTransactions() {
		return transactionModel.getAllTransaction();
	}
	
	
	public List<Transaction> getTransactionByStatus(String status){
		return transactionModel.getTransactionsByStatus(status);
	}
	
	
	public void updateTransactionStatus(Integer transactionID, String status) {
		transactionModel.updateTransactionStatus(transactionID, status);
	}
	
	public List<Transaction> getAssignedOrdersByLaundryStaffID(Integer laundryStaffID) {
		return transactionModel.getAssignedOrderByLaundryStaffID(laundryStaffID);
	}
	
	public void assignOrderToLaundryStaff(Integer transactionID, Integer receptionistID, Integer laundryStaffID) {
		
		transactionModel.assignOrderToLaundryStaff(transactionID, receptionistID, laundryStaffID);
	}
	
	public void orderLaundryService(Integer serviceID, Integer customerID, Double totalWeight, String notes) {
		
		
		String validate = validateOrder(totalWeight, notes);
		
		if(validate == null) {
//			transactionModel.orderLaundryService(null, null, totalWeight, notes);
		}
		else {
			//disini set aja error message
		}
	}
	
	public List<Transaction> getTransactionsByCustomerID(Integer customerID){
		return transactionModel.getTransactionsByCustomerID(customerID);
	}
	
	
	public String validateOrder(Double totalWeight, String notes) {
		
		if(totalWeight < 1 || totalWeight > 51) {
			return "Total weight must between 2 kg and 50 kg.";
		}
		
		if(notes.length() > 251) {
			return "Notes must be less than or equal 250 characters";
		}
		
		return null;
	}
	
	
	
	
}
