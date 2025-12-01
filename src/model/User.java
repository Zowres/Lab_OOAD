package model;

import java.util.Date;
import java.util.List;

public class User{
	private Integer userID;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userGender;
	private Date userDOB;
	private String userRole;

	
	
	public User login(String email, String password) {
	
		// ntar balikin user ye
		return null;
	}
	
	public List<User> getUserByRole(String role){
		
		
		//ntar balikin list user
		return null;
	}
	
	public User getUserByEmail(String email) {
		
		
		//balikin user
		return null;
	}
	
	public User getUserByName(String name) {
		
		
		//balikin user
		return null;
	}
	
	public void addUser (String name, String email, String password, String gender, Date DOB, String role) {
		
		
		
		//insert db
	}
	
	
	
	
	
	
}
