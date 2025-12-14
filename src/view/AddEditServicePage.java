package view;

import controller.ServiceController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Service;
import model.User;

public class AddEditServicePage {

    private Scene scene;
    private User adminUser;
    private Service service; 
    private ServiceController serviceController;

    public AddEditServicePage(User adminUser, Service service) {
        this.adminUser = adminUser;
        this.service = service;
        this.serviceController = new ServiceController();

        initializeUI();
    }

    private void initializeUI() {
        BorderPane root = new BorderPane();

        // Top: Back button + Title
        Button btnBack = new Button("← Back to Service Management");
        btnBack.setStyle("-fx-font-size: 14px; -fx-padding: 8 16;");
        btnBack.setOnAction(e ->
            ViewManager.getInstance().switchScene(new AdminPage(adminUser).getScene())
        );

        boolean isEdit = service != null;
        Label title = new Label(isEdit ? "Edit Service" : "Add New Service");
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

        TextField tfName = new TextField();
        tfName.setPromptText("e.g., Premium Wash");

        TextArea taDescription = new TextArea();
        taDescription.setPromptText("Describe the service...");
        taDescription.setPrefRowCount(5);
        taDescription.setWrapText(true);

        TextField tfPrice = new TextField();
        tfPrice.setPromptText("Price per kg (e.g., 15000)");

        TextField tfDuration = new TextField();
        tfDuration.setPromptText("Duration in days (1-30)");

        Button btnSubmit = new Button(isEdit ? "Update Service" : "Add Service");
        btnSubmit.setStyle("-fx-background-color: #337ab7; -fx-text-fill: white; -fx-font-size: 16px;");
        btnSubmit.setPrefWidth(200);

        Label msgLabel = new Label();
        msgLabel.setWrapText(true);
        msgLabel.setMaxWidth(400);
        msgLabel.setAlignment(Pos.CENTER);

        // Pre-fill fields if editing
        if (isEdit) {
            tfName.setText(service.getServiceName());
            taDescription.setText(service.getServiceDescription());
            tfPrice.setText(String.valueOf(service.getServicePrice()));
            tfDuration.setText(String.valueOf(service.getServiceDuration()));
        }

        btnSubmit.setOnAction(e -> {
            String name = tfName.getText().trim();
            String description = taDescription.getText().trim();
            String priceText = tfPrice.getText().trim();
            String durationText = tfDuration.getText().trim();

            Double price = null;
            Integer duration = null;


            if (!priceText.isEmpty()) {
                try {
                    price = Double.parseDouble(priceText);
                    if (price < 0) {
                        msgLabel.setText("Price cannot be negative.");
                        msgLabel.setStyle("-fx-text-fill: red;");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    msgLabel.setText("Price must be a valid number.");
                    msgLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            }

            if (!durationText.isEmpty()) {
                try {
                    duration = Integer.parseInt(durationText);
                    if (duration <= 0) {
                        msgLabel.setText("Duration must be positive.");
                        msgLabel.setStyle("-fx-text-fill: red;");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    msgLabel.setText("Duration must be a valid whole number.");
                    msgLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            }
            
            Integer durationSeconds = (duration != null) ? duration * 24 * 60 * 60 : null;

            String result;

            if (isEdit) {
                // Call edit validation
                result = serviceController.validateEditService(
                        service.getServiceID(),
                        name,
                        description,
                        price,
                        durationSeconds
                );
            } else {
                // Call add validation
                result = serviceController.validateAddService(
                        name,
                        description,
                        price,
                        durationSeconds
                );
            }

            msgLabel.setText(result);
            if (result != null && result.toLowerCase().contains("success")) {
                msgLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
                new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            javafx.application.Platform.runLater(() ->
                                ViewManager.getInstance().switchScene(new AdminPage(adminUser).getScene())
                            );
                        }
                    },
                    1500
                );
            } else {
                msgLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            }
        });

        form.add(new Label("Service Name:"), 0, 0);
        form.add(tfName, 1, 0);

        form.add(new Label("Description:"), 0, 1);
        form.add(taDescription, 1, 1);

        form.add(new Label("Price per kg:"), 0, 2);
        form.add(tfPrice, 1, 2);

        form.add(new Label("Duration (days):"), 0, 3);
        form.add(tfDuration, 1, 3);

        form.add(btnSubmit, 1, 4);
        form.add(msgLabel, 1, 5);

        root.setCenter(form);

        scene = new Scene(root, 700, 650);
    }

    public Scene getScene() {
        return scene;
    }
}