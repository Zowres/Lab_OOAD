package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.Connect;

public class Employee extends User{
	
	Connect connect = Connect.getInstance();
	
	public Employee(Integer userID, String userName, String userEmail, String userPassword,
            		String userGender, Date userDOB, String userRole) {
		super(userID, userName, userEmail, userPassword, userGender, userDOB, userRole);
	}
	
	public Employee(){
		
	}
	
	public List<User> getAllEmployees(){
		 List<User> list = new ArrayList<>();

		    String query = "SELECT * FROM users WHERE userRole LIKE 'Employee' " +
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

		            User emp = new Employee(id, name, email, password, gender, dob, roles);
		            list.add(emp);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return list;
	}
}
