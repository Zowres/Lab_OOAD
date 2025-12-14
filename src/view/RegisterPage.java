package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.sql.Date;
import java.time.LocalDate;

public class RegisterPage {
    private Scene scene;
    private GridPane gridPane;
    private VBox mainLayout;

    private Label lblTitle, lblName, lblEmail, lblPass, lblConfPass, lblGender, lblDOB, lblMsg;
    private TextField tfName, tfEmail;
    private PasswordField pfPass, pfConfPass;
    private RadioButton rbMale, rbFemale;
    private ToggleGroup tgGender;
    private DatePicker dpDOB;
    private Button btnRegister;
    private Hyperlink loginRedirect;
    private UserController userController;

    public RegisterPage() {
        userController = new UserController();
        init();
        layout();
        action();
    }

    private void init() {
        gridPane = new GridPane();
        mainLayout = new VBox(20);
        loginRedirect = new Hyperlink("Already have an account? Login");

        lblTitle = new Label("Register Customer");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        lblName = new Label("Name:");
        lblEmail = new Label("Email:");
        lblPass = new Label("Password:");
        lblConfPass = new Label("Confirm Password:");
        lblGender = new Label("Gender:");
        lblDOB = new Label("Date of Birth:");
        lblMsg = new Label();

        tfName = new TextField();
        tfEmail = new TextField();
        pfPass = new PasswordField();
        pfConfPass = new PasswordField();

        rbMale = new RadioButton("Male");
        rbFemale = new RadioButton("Female");
        tgGender = new ToggleGroup();
        rbMale.setToggleGroup(tgGender);
        rbFemale.setToggleGroup(tgGender);

        dpDOB = new DatePicker();

        btnRegister = new Button("Register");
        
    }

    private void layout() {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        gridPane.add(lblName, 0, 0); 
        gridPane.add(tfName, 1, 0);
        gridPane.add(lblEmail, 0, 1); 
        gridPane.add(tfEmail, 1, 1);
        gridPane.add(lblPass, 0, 2); 
        gridPane.add(pfPass, 1, 2);
        gridPane.add(lblConfPass, 0, 3); 
        gridPane.add(pfConfPass, 1, 3);
        
        gridPane.add(lblGender, 0, 4);
        VBox genderBox = new VBox(5, rbMale, rbFemale);
        gridPane.add(genderBox, 1, 4);

        gridPane.add(lblDOB, 0, 5); gridPane.add(dpDOB, 1, 5);
        gridPane.add(btnRegister, 1, 6);

        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(lblTitle, gridPane, lblMsg, loginRedirect);

        scene = new Scene(mainLayout, 500, 550);
    }

    private void action() {

        btnRegister.setOnAction(e -> {
            String name = tfName.getText();
            String email = tfEmail.getText();
            String pass = pfPass.getText();
            String confPass = pfConfPass.getText();
            
            String gender = "";
            if(rbMale.isSelected()) gender = "Male";
            else if(rbFemale.isSelected()) gender = "Female";

            LocalDate localDate = dpDOB.getValue();
            Date dob;
            if (localDate != null) {
            	dob = Date.valueOf(localDate);
            }
            else {
            	dob = null;
            }
            
           
            java.util.Date utilDate;
            if (dob != null) {
            	utilDate = new java.util.Date(dob.getTime());
            }
            else {
            	utilDate = new java.util.Date();
            }

            String result = userController.validateAddCustomer(name, email, pass, confPass, gender, utilDate);
            
            lblMsg.setText(result);
            
            
            if(result.equals("Success Registered")) {
                lblMsg.setStyle("-fx-text-fill: green;");
            } 
            else {
                lblMsg.setStyle("-fx-text-fill: red;");
            }
        });
        
        loginRedirect.setOnAction(e -> {
        	ViewManager.getInstance().switchScene(new LoginPage().getScene());
        });
    }

    public Scene getScene() { 
    	return scene; 
    }
}