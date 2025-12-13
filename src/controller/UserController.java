package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import model.Employee;
import model.User;

public class UserController {
	
	User userModel;
	Employee employeeModel;

	
	//untuk ngebalikin error message harusnya pake string
	public String addUser(String name, String email,String password, String confirmPassword, String gender, Date dob, String role) {
		
		if(name.isEmpty()) {
			
			return "Please fill your name!";
		}
		
		if(getUserByName(name) != null) {
			return "Name already used!";
		}
		
		if(!email.endsWith("@email.com")) {
			
			return "Email must ends with @email.com ";
		}
		
		if(getUserByEmail(email) != null) {
			
			return "Email must be unique";
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
		
		LocalDate minBirthDate = LocalDate.now().minusYears(12);
		
		if (birthDate.isAfter(minBirthDate)) {
            return "Age must be at least 12 or older";
		}
		
		role = "Customer";
		
		
		userModel.addUser(name, email, confirmPassword, gender, dob, role);
		
		
		return "Success Registered";
	}
	
	public User login (String email, String password) {
		
		
		return userModel.login(email, password);
	}

	
	public void addEmployee(String name, String email, String password, String confirmPassword, Date dob, String role) {
		userModel.addUser(name, email, password, confirmPassword, dob, role);
	}
	
	public List<User> getAllEmployees(){
		return employeeModel.getAllEmployees();
	}
	
	
	public List<User> getUsersByRole(String role){
		
		return userModel.getUserByRole(role);
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
		User checkUser;
		
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
		
		
		return "Add Account Successfully";
	}
	
	public User getUserByName(String name) {
		
		return userModel.getUserByName(name);
	}
	
	public User getUserByEmail(String email) {
		
		
		return userModel.getUserByEmail(email);
	}
	
	
	
	
}	
