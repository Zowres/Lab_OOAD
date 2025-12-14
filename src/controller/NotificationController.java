package controller;

import java.util.List;

import model.Notification;


public class NotificationController {
	
	Notification notifModel;
	
	public NotificationController() {
		this.notifModel = new Notification();
	}
	
	public void sendNotification(Integer recipientID, String message) {
		if (recipientID == null) {
	        System.out.println("Recipient ID cannot be empty");
	        return;
	    }

	    if (message == null || message.trim().isEmpty()) {
	        System.out.println("Notification message cannot be empty");
	        return;
	    }
	    

	    notifModel.sendNotification(recipientID, message);

	    System.out.println("Notification sent successfully");
	    
	}
	
	public List<Notification> getNotificationByRecipientID(Integer recipientID){
		
		
		return notifModel.getNotificationsByRecipientID(recipientID);
	}
	
	public Notification getNotificationByID(Integer notificationID) {
		
		return notifModel.getNotificationByID(notificationID);
	}
	
	public void deleteNotification(Integer notificationID) {
		
		notifModel.deleteNotification(notificationID);
	}
	
	public void markAsRead(Integer notificationID) {
		notifModel.markAsRead(notificationID);
	}
	
	public Boolean validateIsRead(String notification) {
		
		
		return null;
	}
}
