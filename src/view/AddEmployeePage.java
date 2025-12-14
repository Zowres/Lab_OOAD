package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;
import java.time.LocalDate;
import java.util.Date;

public class AddEmployeePage {

    private Scene scene;
    private User adminUser; 
    private UserController userController;

    public AddEmployeePage(User adminUser) {
        this.adminUser = adminUser;
        this.userController = new UserController();

        initializeUI();
    }

    private void initializeUI() {
        BorderPane root = new BorderPane();

        // Top: Back button + Title
        Button btnBack = new Button("← Back to Admin Dashboard");
        btnBack.setStyle("-fx-font-size: 14px; -fx-padding: 8 16;");
        btnBack.setOnAction(e -> 
            ViewManager.getInstance().switchScene(new AdminPage(adminUser).getScene())
        );

        Label title = new Label("Add New Employee");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox topBox = new HBox(20, btnBack, title);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(20));
        root.setTop(topBox);

        // Center: Form
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setPadding(new Insets(40));
        form.setAlignment(Pos.CENTER);

        // Form fields
        TextField tfName = new TextField();
        tfName.setPromptText("Full name");

        TextField tfEmail = new TextField();
        tfEmail.setPromptText("employee@govlash.com");

        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password (min 6 characters)");

        PasswordField pfConfirm = new PasswordField();
        pfConfirm.setPromptText("Confirm password");

        ToggleGroup tgGender = new ToggleGroup();
        RadioButton rbMale = new RadioButton("Male");
        rbMale.setToggleGroup(tgGender);
        RadioButton rbFemale = new RadioButton("Female");
        rbFemale.setToggleGroup(tgGender);

        DatePicker dpDOB = new DatePicker();
        dpDOB.setPromptText("Date of Birth");

        ComboBox<String> cbRole = new ComboBox<>();
        cbRole.getItems().addAll("Admin","Receptionist", "LaundryStaff");
        cbRole.setPromptText("Select Role");

        Button btnSubmit = new Button("Add Employee");
        btnSubmit.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 16px;");
        btnSubmit.setPrefWidth(200);

        Label msgLabel = new Label();
        msgLabel.setWrapText(true);
        msgLabel.setMaxWidth(400);
        msgLabel.setAlignment(Pos.CENTER);

        btnSubmit.setOnAction(e -> {
            String name = tfName.getText().trim();
            String email = tfEmail.getText().trim();
            String password = pfPassword.getText();
            String confirmPassword = pfConfirm.getText();
            String role = null;

            String gender = null;
            if(rbMale.isSelected()) gender = "Male";
            else if(rbFemale.isSelected()) gender = "Female";

            LocalDate localDate = dpDOB.getValue();
            java.sql.Date dob;
            if (localDate != null) {
            	dob = java.sql.Date.valueOf(localDate);
            }
            else {
            	dob = null;
            }
            
            Date utilDate;
            if (dob != null) {
            	utilDate = new Date(dob.getTime());
            }
            else {
            	utilDate = new Date();
            }
            

            role = cbRole.getValue();

            String result = userController.validateAddEmployee(
                    name,
                    email,
                    password,
                    confirmPassword,
                    gender,
                    utilDate,
                    role
            );

            msgLabel.setText(result);
            if (result != null && result.toLowerCase().contains("success")) {
                msgLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
                tfName.clear();
                tfEmail.clear();
                pfPassword.clear();
                pfConfirm.clear();
                dpDOB.setValue(null);
                cbRole.getSelectionModel().selectFirst();
            } else {
                msgLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            }
        });

        // Layout form
        form.add(new Label("Name:"), 0, 0);
        form.add(tfName, 1, 0);

        form.add(new Label("Email:"), 0, 1);
        form.add(tfEmail, 1, 1);

        form.add(new Label("Password:"), 0, 2);
        form.add(pfPassword, 1, 2);

        form.add(new Label("Confirm Password:"), 0, 3);
        form.add(pfConfirm, 1, 3);

        form.add(new Label("Gender:"), 0, 4);
        HBox genderBox = new HBox(20, rbMale, rbFemale);
        form.add(genderBox, 1, 4);

        form.add(new Label("Date of Birth:"), 0, 5);
        form.add(dpDOB, 1, 5);

        form.add(new Label("Role:"), 0, 6);
        form.add(cbRole, 1, 6);

        form.add(btnSubmit, 1, 7);
        form.add(msgLabel, 1, 8);

        root.setCenter(form);

        scene = new Scene(root, 700, 650);
    }

    public Scene getScene() {
        return scene;
    }
}