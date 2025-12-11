package model;

import java.util.Date;
import java.util.List;

public class Employee extends User{
	
	
	
	public Employee(Integer userID, String userName, String userEmail, String userPassword,
            		String userGender, Date userDOB, String userRole) {
		super(userID, userName, userEmail, userPassword, userGender, userDOB, userRole);
	}


	public List<User> getAllEmployees(){
		
		return null;
	}
}
