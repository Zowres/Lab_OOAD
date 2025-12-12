package model;

import java.util.Date;
import java.util.List;

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
	
	
	public Notification(Integer notificationID, Integer recipientID, String notificationMessage, Date createdAt,
			Boolean isRead) {
		super();
		this.notificationID = notificationID;
		this.recipientID = recipientID;
		this.notificationMessage = notificationMessage;
		this.createdAt = createdAt;
		this.isRead = isRead;
	}
	
	

	public void sendNotification(Integer recipientID, String message) {
		
		// send message
	}
	
	public List<Notification> getNotificationsByRecipientID(Integer recipientID){
		
		//return list notif
		return null;
	}
	
	public Notification getNotificationByID(Integer notificationID) {
		
		//return notif by id
		return null;
	}
	
	public void deleteNotification (Integer notificationID) {
		
		
	}
	
	public void markAsRead(Integer notificationID) {
		
		
	}
	
	
	
}
