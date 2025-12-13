package view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import controller.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RegisterPage {

    private Scene scene;
    private UserController userCon;

    private BorderPane borderPane;
    private GridPane formGrid;
    private VBox vBox;
    private VBox form;

    private Label titleLbl;
    private Label nameLbl;
    private Label emailLbl;
    private Label passwordLbl;
    private Label genderLbl;
    private Label dobLbl;
    private Label errorLbl;
    private Label confirmPasswordLbl;

    private TextField nameTf;
    private TextField emailTf;
    private PasswordField passwordPf;
    private PasswordField confirmPasswordPf;
    private ComboBox<String> genderCb;
    private DatePicker dobPicker;

    private Button registerBtn;
    private Hyperlink loginRedirect;

    public Scene getScene() {
        return scene;
    }

    public RegisterPage() {
        userCon = new UserController();
        init();
        layout();
        action();
    }

    private void init() {
    	borderPane = new BorderPane();
        vBox = new VBox(25); 
        
        formGrid = new GridPane(); 
        formGrid.setHgap(15); 
        formGrid.setVgap(15); 

        titleLbl = new Label("Register");
        titleLbl.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        nameLbl = new Label("Name");
        emailLbl = new Label("Email");
        passwordLbl = new Label("Password");
        confirmPasswordLbl = new Label("Confirm Password");
        genderLbl = new Label("Gender");
        dobLbl = new Label("Date of Birth");

        errorLbl = new Label();
        errorLbl.setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); 

        nameTf = new TextField();
        emailTf = new TextField();
        passwordPf = new PasswordField();
        confirmPasswordPf = new PasswordField();

        genderCb = new ComboBox<>();
        genderCb.getItems().addAll("Male", "Female");
        genderCb.setPromptText("Select Gender"); 
        
        dobPicker = new DatePicker();
        dobPicker.setPromptText("DD/MM/YYYY");

        registerBtn = new Button("Register");
        registerBtn.setMinWidth(150); 
        
        loginRedirect = new Hyperlink("Already have an account? Login");
    }

    private void layout() {
    	int row = 0;
        
        formGrid.addRow(row++, nameLbl, nameTf);
        formGrid.addRow(row++, emailLbl, emailTf);
        formGrid.addRow(row++, passwordLbl, passwordPf);
        formGrid.addRow(row++, confirmPasswordLbl, confirmPasswordPf);
        formGrid.addRow(row++, genderLbl, genderCb);
        formGrid.addRow(row++, dobLbl, dobPicker);
        
        nameTf.setMaxWidth(Double.MAX_VALUE);
        emailTf.setMaxWidth(Double.MAX_VALUE);
        passwordPf.setMaxWidth(Double.MAX_VALUE);
        confirmPasswordPf.setMaxWidth(Double.MAX_VALUE);
        genderCb.setMaxWidth(Double.MAX_VALUE);
        dobPicker.setMaxWidth(Double.MAX_VALUE);
        
        ColumnConstraints labelCol = new ColumnConstraints();
        labelCol.setHalignment(HPos.LEFT);
        ColumnConstraints inputCol = new ColumnConstraints();
        inputCol.setHgrow(Priority.ALWAYS);
        formGrid.getColumnConstraints().addAll(labelCol, inputCol);
        
        HBox buttonWrapper = new HBox(registerBtn);
        buttonWrapper.setAlignment(Pos.CENTER);
        
        GridPane.setColumnSpan(buttonWrapper, 2); 
        GridPane.setHalignment(buttonWrapper, HPos.CENTER);
        formGrid.add(buttonWrapper, 0, row++);
        
        vBox.setAlignment(Pos.CENTER);

        formGrid.setMaxWidth(400); 
        vBox.setPadding(new Insets(20)); 

        vBox.getChildren().addAll(titleLbl, formGrid, errorLbl, loginRedirect);

        borderPane.setCenter(vBox);
        scene = new Scene(borderPane, 500, 600);
    }

    private void action() {
        registerBtn.setOnAction(e -> {
            errorLbl.setText("");

            String name = nameTf.getText();
            String email = emailTf.getText();
            String password = passwordPf.getText();
            String confirmPassword = confirmPasswordPf.getText();
            String gender = genderCb.getValue();
            
            LocalDate dobLocal = dobPicker.getValue(); 
            
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()
                    || confirmPassword.isEmpty() || gender == null || dobLocal == null) {
                errorLbl.setText("All fields must be filled");
                return;
            }


            Date dob = java.sql.Date.valueOf(dobLocal);
            
            String role = "Customer"; 
            
            String success = userCon.addUser(name, email, password, confirmPassword, gender, dob, role);
            
            System.out.println("Berhasil");

            if (success.isEmpty()) {
                ViewManager.getInstance().switchScene(new LoginPage().getScene());
            } else {
                errorLbl.setText(success);
            }
        });

        loginRedirect.setOnAction(e -> {
            ViewManager.getInstance().switchScene(new LoginPage().getScene());
        });
    }
}
