package view;

import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Service;
import model.User;

public class OrderFormPage {

    private Scene scene;
    private User user;
    private Service service;
    private TransactionController transactionController;

    public OrderFormPage(User user, Service service) {
        this.user = user;
        this.service = service;
        this.transactionController = new TransactionController();

        initializeUI();
    }

    private void initializeUI() {
        BorderPane root = new BorderPane();

        // Top: Back + Title
        Button btnBack = new Button("← Back");
        btnBack.setOnAction(e -> ViewManager.getInstance().switchScene(new CustomerPage(user).getScene()));

        Label title = new Label("Order: " + service.getServiceName());
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        VBox topBox = new VBox(10, btnBack, title);
        topBox.setPadding(new Insets(20));
        root.setTop(topBox);

        // Center: Form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setPadding(new Insets(40));
        form.setAlignment(Pos.CENTER);

        Label descLabel = new Label("Description:");
        Label descValue = new Label(service.getServiceDescription());
        descValue.setWrapText(true);
        descValue.setMaxWidth(300);

        Label priceLabel = new Label("Price per kg:");
        Label priceValue = new Label("Rp " + service.getServicePrice());

        Label durationLabel = new Label("Estimated duration:");
        Label durationValue = new Label(service.getServiceDuration() + " days");

        TextField tfWeight = new TextField();
        tfWeight.setPromptText("e.g., 5.5");

        TextArea taNotes = new TextArea();
        taNotes.setPromptText("Any special instructions? (optional)");
        taNotes.setPrefRowCount(4);
        taNotes.setWrapText(true);

        Button btnSubmit = new Button("Confirm Order");
        btnSubmit.setStyle("-fx-background-color: #337ab7; -fx-text-fill: white; -fx-font-size: 16px;");
        btnSubmit.setPrefWidth(200);

        Label msgLabel = new Label();
        msgLabel.setMinHeight(30);

        btnSubmit.setOnAction(e -> {
            String weightText = tfWeight.getText().trim();
            if (weightText.isEmpty()) {
                msgLabel.setText("Please enter weight.");
                msgLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            double weight;
            try {
                weight = Double.parseDouble(weightText);
            } catch (NumberFormatException ex) {
                msgLabel.setText("Weight must be a number.");
                msgLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            String notes = taNotes.getText();

            transactionController.orderLaundryService(
                    service.getServiceID(),
                    user.getUserID(),
                    weight,
                    notes
            );

            msgLabel.setText("Order successfully placed!");
            msgLabel.setStyle("-fx-text-fill: green;");

            // Auto return after 2 seconds
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    javafx.application.Platform.runLater(() ->
                        ViewManager.getInstance().switchScene(new CustomerPage(user).getScene())
                    );
                }
            }, 2000);
        });

        form.add(new Label("Service:"), 0, 0);
        form.add(new Label(service.getServiceName()), 1, 0);
        form.add(descLabel, 0, 1);
        form.add(descValue, 1, 1);
        form.add(priceLabel, 0, 2);
        form.add(priceValue, 1, 2);
        form.add(durationLabel, 0, 3);
        form.add(durationValue, 1, 3);

        form.add(new Label("Total Weight (kg):"), 0, 4);
        form.add(tfWeight, 1, 4);
        form.add(new Label("Notes:"), 0, 5);
        form.add(taNotes, 1, 5);
        form.add(btnSubmit, 1, 6);
        form.add(msgLabel, 1, 7);

        root.setCenter(form);

        scene = new Scene(root, 700, 600);
    }

    public Scene getScene() {
        return scene;
    }
}