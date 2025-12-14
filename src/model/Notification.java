package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.Connect;

public class Notification {
	private Integer notificationID;
	private Integer recipientID;
	private String notificationMessage;
	private Date createdAt;
	private Boolean isRead;
	
	public Integer getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(Integer notificationID) {
		this.notificationID = notificationID;
	}

	public Integer getRecipientID() {
		return recipientID;
	}

	public void setRecipientID(Integer recipientID) {
		this.recipientID = recipientID;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
	Connect connect = Connect.getInstance();
	
	
	public Notification(Integer notificationID, Integer recipientID, String notificationMessage, Date createdAt,
			Boolean isRead) {
		super();
		this.notificationID = notificationID;
		this.recipientID = recipientID;
		this.notificationMessage = notificationMessage;
		this.createdAt = createdAt;
		this.isRead = isRead;
	}
	
	public Notification() {
		
	}
	
	

	public void sendNotification(Integer recipientID, String message) {
		String query = "INSERT INTO notification (recipientID, notificationMessage, createdAt, isRead) "
                + "VALUES (?, ?, ?, ?)";

		try {
	       PreparedStatement ps = connect.preparedStatement(query);
	
	       ps.setInt(1, recipientID);
	       ps.setString(2, message);
	       ps.setDate(3, new java.sql.Date(System.currentTimeMillis())); // now
	       ps.setBoolean(4, false); // unread by default
	
	       ps.executeUpdate();
	       ps.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Notification> getNotificationsByRecipientID(Integer recipientID){
		List<Notification> list = new ArrayList<>();

	    String query = "SELECT * FROM notification WHERE recipientID = ? ORDER BY createdAt DESC";

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);
	        ps.setInt(1, recipientID);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Integer notificationID = rs.getInt("notificationID");
	            Integer recID          = rs.getInt("recipientID");
	            String message         = rs.getString("notificationMessage");
	            Date createdAt         = rs.getDate("createdAt");
	            Boolean isRead         = rs.getBoolean("isRead");

	            Notification notif = new Notification(
	                notificationID,
	                recID,
	                message,
	                createdAt,
	                isRead
	            );

	            list.add(notif);
	        }

	        rs.close();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return list;
	}
	
	public Notification getNotificationByID(Integer notificationID) {
		Notification notif = null;

	    String query = "SELECT * FROM notification WHERE notificationID = ?";

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);
	        ps.setInt(1, notificationID);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	        	Integer notificationID2 = rs.getInt("notificationID");
	            Integer recID          = rs.getInt("recipientID");
	            String message         = rs.getString("notificationMessage");
	            Date createdAt         = rs.getDate("createdAt");
	            Boolean isRead         = rs.getBoolean("isRead");

	            notif = new Notification(
	                notificationID2,
	                recID,
	                message,
	                createdAt,
	                isRead
	            );
	        }

	        rs.close();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return notif;
	}
	
	public void deleteNotification (Integer notificationID) {
		String query = "DELETE FROM notification WHERE notificationID = ?";

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);
	        ps.setInt(1, notificationID);

	        ps.executeUpdate();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void markAsRead(Integer notificationID) {
		String query = "UPDATE notification SET isRead = ? WHERE notificationID = ?";

	    try {
	        PreparedStatement ps = connect.preparedStatement(query);

	        ps.setBoolean(1, true);
	        ps.setInt(2, notificationID);

	        ps.executeUpdate();
	        ps.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}
	
	
	
}
