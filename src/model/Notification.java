package model;

import java.util.Date;
import java.util.List;

public class Notification {
	private Integer notificationID;
	private Integer recipientID;
	private String notificationMessage;
	private Date createdAt;
	private Boolean isRead;
	
	
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
