package model;

import java.util.Date;

public class Receptionist extends Employee {

	public Receptionist(Integer userID, String userName, String userEmail, String userPassword,
		            	String userGender, Date userDOB, String userRole) {
		super(userID, userName, userEmail, userPassword, userGender, userDOB, userRole);
	}
	
}
