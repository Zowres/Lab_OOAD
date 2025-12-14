package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	private Connect connect = Connect.getInstance();

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
	
	public User(Integer userID, String userName, String userEmail, String userPassword, String userGender, Date userDOB,
			String userRole) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userRole = userRole;
	}
	
	private User createUserByRole(Integer id,String name,String email,String password,String gender,Date dob,String role) {
	    role = role.toLowerCase();

	    switch (role) {
	        case "admin":
	            return new Admin(id, name, email, password, gender, dob, role);

	        case "employee":
	            return new Employee(id, name, email, password, gender, dob, role);

	        case "receptionist":
	            return new Receptionist(id, name, email, password, gender, dob, role);

	        case "laundry_staff":
	        case "laundrystaff":
	            return new LaundryStaff(id, name, email, password, gender, dob, role);

	        case "customer":
	        default:
	            return new User(id, name, email, password, gender, dob, role);
	    }
	}


	public User login(String email, String password) {
	
		User user = null;

	    String query = "SELECT * FROM users WHERE userEmail = '" + email + "' AND userPassword = '" + password + "';";

	    connect.execQuery(query);

	    try {
	        if (connect.rs.next()) {

	            Integer id = connect.rs.getInt("userID");
	            String name = connect.rs.getString("userName");
	            String mail = connect.rs.getString("userEmail");
	            String pass = connect.rs.getString("userPassword");
	            String gender = connect.rs.getString("userGender");
	            Date dob = connect.rs.getDate("userDOB");
	            String role = connect.rs.getString("userRole");

	            user = createUserByRole(id, name, mail, pass, gender, dob, role);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return user;
	}
	
	public List<User> getUserByRole(String role){
		 List<User> list = new ArrayList<>();

		    String query = "SELECT * FROM users WHERE userRole LIKE 'Admin' " +
		                   "OR userRole LIKE 'Receptionist' " +
		                   "OR userRole LIKE 'LaundryStaff';";

		    connect.execQuery(query);

		    try {
		        while (connect.rs.next()) {

		            Integer id = connect.rs.getInt("userID");
		            String name = connect.rs.getString("userName");
		            String email = connect.rs.getString("userEmail");
		            String password = connect.rs.getString("userPassword");
		            String gender = connect.rs.getString("userGender");
		            Date dob = connect.rs.getDate("userDOB");
		            String roles = connect.rs.getString("userRole");

		            User emp = createUserByRole(id, name, email, password, gender, dob, roles);
		            list.add(emp);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return list;
	}
	
	
	public User getUserByEmail(String email) {
		User user = null;

	    String query = "SELECT * FROM users WHERE userEmail = '" + email + "';";
	    connect.execQuery(query);

	    try {
	        if (connect.rs.next()) {
	            Integer id = connect.rs.getInt("userID");
	            String name = connect.rs.getString("userName");
	            String mail = connect.rs.getString("userEmail");
	            String password = connect.rs.getString("userPassword");
	            String gender = connect.rs.getString("userGender");
	            Date dob = connect.rs.getDate("userDOB");
	            String role = connect.rs.getString("userRole");

	            user = new User(id, name, mail, password, gender, dob, role);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return user;
	}
	
	public User getUserByName(String name) {
		User user = null;

	    String query = "SELECT * FROM users WHERE userName = '" + name + "';";
	    connect.execQuery(query);

	    try {
	        if (connect.rs.next()) {
	            Integer id = connect.rs.getInt("userID");
	            String username = connect.rs.getString("userName");
	            String mail = connect.rs.getString("userEmail");
	            String password = connect.rs.getString("userPassword");
	            String gender = connect.rs.getString("userGender");
	            Date dob = connect.rs.getDate("userDOB");
	            String role = connect.rs.getString("userRole");

	            user = new User(id, username, mail, password, gender, dob, role);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return user;
	}
	
	public void addUser (String name, String email, String password, String gender, Date DOB, String role) {
		
		//insert db
		String query = "INSERT INTO users (userName, userEmail, userPassword, userGender, userDOB, userRole) VALUES (?, ?, ?, ?, ?, ?)";

		    try {
		        PreparedStatement ps = Connect.getInstance().preparedStatement(query);

		        ps.setString(1, name);
		        ps.setString(2, email);
		        ps.setString(3, password);
		        ps.setString(4, gender);
		        ps.setDate(5, new java.sql.Date(DOB.getTime()));  
		        ps.setString(6, role);

		        ps.executeUpdate();

		        System.out.println("User inserted successfully!");

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
		
	}
	
	

	
	
	
}
