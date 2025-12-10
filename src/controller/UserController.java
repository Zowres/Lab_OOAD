package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import model.User;

public class UserController {
	
	User userModel;
	
	public void addUser(String name, String email, String confirmPassword, String gender, Date dob, String role) {
		
	}
	
	public User login (String email, String password) {
		
		
		return null;
	}

	
	public void addEmployee(String name, String email, String password, String confirmPassword, Date dob, String role) {
		userModel.addUser(name, email, password, confirmPassword, dob, role);
	}
	
	public List<User> getAllEmployees(){
		
		
		return null;
	}
	
	
	public List<User> getUsersByRole(){
		
		return null;
	}
	
	public String validateAddEmployee(String name, String email,String password, String confirmPassword, String gender, Date dob, String role) {
		List<User> employeeList = getAllEmployees();
		User checkUser;
		//cek nama ada ga
		if(name.isBlank()) return "Name musn't be empty";
		
		//cek nama nya udh unik belom
		checkUser = userModel.getUserByName(name);
		if(checkUser != null) {
			return "Name already be used!";
		}
		
		//check email
		if(!email.endsWith("@govlash.com")) {
			return "Email must end with @govlash.com";
		}
		
		checkUser = userModel.getUserByEmail(email);
		if(checkUser != null) {
			return "Email already be used!";
		}
		
		if(password.length() <= 6) {
			return "Password length must more than 6";
		}
		
		if(!password.equals(confirmPassword)) {
			
			return "Password must be same!";
		}
		
		if(! (gender.equals("Male") || gender.equals("Female"))) {
			return "gender must Male or Female";
		}
		
		LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		LocalDate minBirthDate = LocalDate.now().minusYears(17);
		
		if (birthDate.isAfter(minBirthDate)) {
            return "Age must be more than 17 or older";
		}
		
		if(! ( role.equals("Admin") || role.equals("Laundry Staff") || role.equals("Receptionist") )) {
			return "Role must be between Admin , Laundry Staff , or Receptionist";
		}
		
		
		//berhasil 
		addEmployee(name,email,password,confirmPassword,dob,role);
		
		
		return "Add Employee Success";
	}
	
	public String validateAddCustomer(String name, String email, String password, String confirmPassword, String gender, Date dob) {
		
		
		return null;
	}
	
	public User getUserByEmail(String email) {
		
		
		return null;
	}
	
	
	
	
}	
