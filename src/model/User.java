package model;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

import database.Connect;

public class User{
	private Integer userID;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userGender;
	private Date userDOB;
	private String userRole;
	
	

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public Date getUserDOB() {
		return userDOB;
	}

	public void setUserDOB(Date userDOB) {
		this.userDOB = userDOB;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	
	public User() {
		
	}
	
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
		 String query = "INSERT INTO users (name, email, password, gender, dob, role) VALUES (?, ?, ?, ?, ?, ?)";

		    try {
		        PreparedStatement ps = Connect.getInstance().preparedStatement(query);

		        ps.setString(1, name);
		        ps.setString(2, email);
		        ps.setString(3, password);
		        ps.setString(4, gender);
		        ps.setDate(5, (java.sql.Date) DOB);  
		        ps.setString(6, role);

		        ps.executeUpdate();

		        System.out.println("User inserted successfully!");

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
		
	}
	
	
	
	
	
	
}
