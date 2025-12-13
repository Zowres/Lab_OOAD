package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.User;

public class LoginPage {
	
	private Scene scene;
	private UserController userCon;
	
	
	private BorderPane borderPane;
	private GridPane gridPane;
	private VBox vBox;
	private VBox form;
	
	private Label titleLbl;
	private Label emailLbl;
	private Label passwordLbl;
	private Label errorLbl;
	
	private TextField emailTf;
	private PasswordField passwordPf;
	private Button loginBtn;
	private Hyperlink regisRedirect;
	
	public Scene getScene() {
		return scene;
	}

	public LoginPage() {
		userCon = new UserController();
		init();
		layout();
		action();
		
	}
	
	
	private void init() {
		borderPane = new BorderPane();
		vBox = new VBox(15);
		form = new VBox(10);
		
		titleLbl = new Label("Login");
		titleLbl.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
		
		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		errorLbl = new Label();
		
		emailTf = new TextField();
		passwordPf = new PasswordField();
		loginBtn = new Button("Login");
		regisRedirect = new Hyperlink("Don't have an account? Register");
	}
	
	private void layout() {

		form.setPadding(new Insets(20));
		form.getChildren().addAll(emailLbl, emailTf, passwordLbl, passwordPf, loginBtn);
		
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(titleLbl, form, errorLbl, regisRedirect);
		borderPane.setCenter(vBox);
		scene = new Scene(borderPane, 500, 500);
	
	}
	
	private void action() {
		loginBtn.setOnAction(e -> {
			
			errorLbl.setText("");
			
			String email = emailTf.getText();
			String password = passwordPf.getText();
			
			if(email.isEmpty() || password.isEmpty()) {
				errorLbl.setText("All fields must be filled");
				return;
			}
			
			User user = userCon.login(email, password);
			
			if(user != null) {
				String role = user.getUserRole();
				if(role.equalsIgnoreCase("admin")) {
					ViewManager.getInstance().switchScene(new AdminPage(user).getScene());
				}
				else if(role.equalsIgnoreCase("customer")) {
					ViewManager.getInstance().switchScene(new CustomerPage(user).getScene());
				}
				else {
					errorLbl.setText("Role undefined");
				}
			}
			else {
				errorLbl.setText("Credentials do not match!");
			}
		});
		
		regisRedirect.setOnAction(e -> {
			ViewManager.getInstance().switchScene(new RegisterPage().getScene());
		});
	}

}
