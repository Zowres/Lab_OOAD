package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.Connect;

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
	
	private Connect connect = Connect.getInstance();
	
	
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
	
	
	public Transaction(Integer transactionID, Integer serviceID, Integer customerID, Integer receptionistID,
			Integer laundryStaffID, Date transactionDate, String transactionStatus, Double totalWeight,
			String transactionNotes) {
		super();
		this.transactionID = transactionID;
		this.serviceID = serviceID;
		this.customerID = customerID;
		this.receptionistID = receptionistID;
		this.laundryStaffID = laundryStaffID;
		this.transactionDate = transactionDate;
		this.transactionStatus = transactionStatus;
		this.totalWeight = totalWeight;
		
		if(transactionNotes == null) {
			transactionNotes = "";
		}
		
		this.transactionNotes = transactionNotes;
	}
	
	
	public Transaction() {
		
	}
	
	
	public List<Transaction> getAllTransaction() {
	    List<Transaction> list = new ArrayList<>();

	    String query = "SELECT * FROM transaction;";
	    connect.execQuery(query);

	    try {
	        while (connect.rs.next()) {

	            Integer transactionID      = connect.rs.getInt("transactionID");
	            Integer serviceID          = connect.rs.getInt("serviceID");
	            Integer customerID         = connect.rs.getInt("customerID");
	            Integer receptionistID     = connect.rs.getInt("receptionistID");
	            Integer laundryStaffID     = connect.rs.getInt("laundryStaffID");
	            Date transactionDate       = connect.rs.getDate("transactionDate");
	            String transactionStatus   = connect.rs.getString("transactionStatus");
	            Double totalWeight         = connect.rs.getDouble("totalWeight");
	            String transactionNotes    = connect.rs.getString("notes");

	            // Create Transaction object
	            Transaction trx = new Transaction(transactionID,serviceID,customerID,receptionistID,laundryStaffID,transactionDate,transactionStatus,totalWeight,transactionNotes);

	            list.add(trx);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	
	public void updateTransactionStatus(Integer transactionID, String status) {
		String query = "UPDATE transaction SET transactionStatus = ? WHERE transactionID = ?";
		
		

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);
	        ps.setString(1, status);
	        ps.setInt(2, transactionID);

	        ps.executeUpdate();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Transaction> getAssignedOrderByLaundryStaffID(Integer LaundryStaffID) {
		List<Transaction> list = new ArrayList<>();
		if (LaundryStaffID == null) {
	        return list; 
	    }
	    String query = "SELECT * FROM transaction WHERE laundryStaffID = ?";

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);
	        ps.setInt(1, LaundryStaffID);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Integer transactionID      = rs.getInt("transactionID");
	            Integer serviceID          = rs.getInt("serviceID");
	            Integer customerID         = rs.getInt("customerID");
	            Integer receptionistID     = rs.getInt("receptionistID");
	            Integer staffID            = rs.getInt("laundryStaffID");
	            Date transactionDate       = rs.getDate("transactionDate");
	            String transactionStatus   = rs.getString("transactionStatus");
	            Double totalWeight         = rs.getDouble("totalWeight");
	            String transactionNotes    = rs.getString("notes");

	            Transaction trx = new Transaction(transactionID,serviceID,customerID,receptionistID,staffID,transactionDate,transactionStatus,totalWeight,transactionNotes
	            );

	            list.add(trx);
	        }

	        rs.close();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	public void assignOrderToLaundryStaff(Integer transactionID,Integer receptionistID, Integer laundryStaffID) {
		 String query = "UPDATE transaction "+ "SET receptionistID = ?, laundryStaffID = ?, transactionStatus = 'Assigned' "
                 + "WHERE transactionID = ?";

		    try {
		        PreparedStatement ps = connect.preparedStatement(query);
		        ps.setInt(1, receptionistID);
		        ps.setInt(2, laundryStaffID);
		        ps.setInt(3, transactionID);
		
		        ps.executeUpdate();
		        ps.close();
		
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	public void orderLaundryService(Integer serviceID,Integer customerID, Double totalWeight, String notes ) {
		String query = "INSERT INTO transaction (serviceID, customerID, receptionistID, laundryStaffID, "
	            + "transactionDate, transactionStatus, totalWeight, notes) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);
	        ps.setInt(1, serviceID);
	        ps.setInt(2, customerID);

	        ps.setNull(3, java.sql.Types.INTEGER); 
	        ps.setNull(4, java.sql.Types.INTEGER); 

	        ps.setDate(5, new java.sql.Date(System.currentTimeMillis())); 
	        ps.setString(6, "Pending");                                   

	        ps.setDouble(7, totalWeight);
	        ps.setString(8, notes);

	        ps.executeUpdate();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Transaction> getTransactionsByCustomerID(Integer customerID){
		
		
		List<Transaction> list = new ArrayList<>();

	    String query = "SELECT * FROM transaction WHERE customerID = ?";

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);
	        ps.setInt(1, customerID);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Integer transactionID      = rs.getInt("transactionID");
	            Integer serviceID          = rs.getInt("serviceID");
	            Integer receptionistID     = rs.getInt("receptionistID");
	            Integer staffID            = rs.getInt("laundryStaffID");
	            Date transactionDate       = rs.getDate("transactionDate");
	            String transactionStatus   = rs.getString("transactionStatus");
	            Double totalWeight         = rs.getDouble("totalWeight");
	            String transactionNotes    = rs.getString("notes");

	            Transaction trx = new Transaction(transactionID,serviceID,customerID,receptionistID,staffID,transactionDate,transactionStatus,totalWeight,transactionNotes);

	            list.add(trx);
	        }

	        rs.close();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	public List<Transaction> getTransactionsByStatus(String status){
		
		
		List<Transaction> list = new ArrayList<>();

	    String query = "SELECT * FROM transaction WHERE transactionStatus = ?";

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);
	        ps.setString(1, status);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Integer transactionID      = rs.getInt("transactionID");
	            Integer serviceID          = rs.getInt("serviceID");
	            Integer customerID		   = rs.getInt("customerID");
	            Integer receptionistID     = rs.getInt("receptionistID");
	            Integer staffID            = rs.getInt("laundryStaffID");
	            Date transactionDate       = rs.getDate("transactionDate");
	            String transactionStatus   = rs.getString("transactionStatus");
	            Double totalWeight         = rs.getDouble("totalWeight");
	            String transactionNotes    = rs.getString("notes");

	            Transaction trx = new Transaction(transactionID,serviceID,customerID,receptionistID,staffID,transactionDate,transactionStatus,totalWeight,transactionNotes);

	            list.add(trx);
	        }

	        rs.close();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	
}
