package view;

import controller.NotificationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Notification;
import model.User;

public class NotificationDetailPage {

    private Scene scene;
    private Notification notification;
    private User user;
    private NotificationController notificationController;

    public NotificationDetailPage(User user, Notification notification) {
        this.user = user;
        this.notification = notification;
        this.notificationController = new NotificationController();

        initializeUI();
    }

    private void initializeUI() {
        BorderPane root = new BorderPane();

        // Top: Back button and title
        Button btnBack = new Button("← Back to Notifications");
        btnBack.setStyle("-fx-font-size: 14px; -fx-padding: 8 16;");
        btnBack.setOnAction(e -> 
            ViewManager.getInstance().switchScene(new CustomerPage(user).getScene())
        );

        Label titleLabel = new Label("Notification Detail");
        titleLabel.setFont(Font.font(24));
        titleLabel.setStyle("-fx-font-weight: bold;");

        HBox topBox = new HBox(20, btnBack, titleLabel);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(20));
        root.setTop(topBox);

        // Center: Notification content
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(40));
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setMaxWidth(700);
        contentBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ddd; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label messageLabel = new Label(notification.getNotificationMessage());
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-font-size: 18px; -fx-line-spacing: 4px;");
        messageLabel.setMaxWidth(600);

        Label dateLabel = new Label("Received on: " + notification.getCreatedAt().toString());
        dateLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        Label statusLabel = new Label(notification.getIsRead() ? "Status: Read" : "Status: Unread");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        statusLabel.setStyle(statusLabel.getStyle() + (notification.getIsRead() ? "-fx-text-fill: #888;" : "-fx-text-fill: #d9534f;"));

        contentBox.getChildren().addAll(messageLabel, dateLabel, statusLabel);

        // Bottom: Action buttons
        Button btnMarkAsRead = new Button("Mark as Read");
        btnMarkAsRead.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 14px;");
        btnMarkAsRead.setPrefWidth(150);

        Button btnDelete = new Button("Delete Notification");
        btnDelete.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px;");
        btnDelete.setPrefWidth(180);

        Label actionMsg = new Label();
        actionMsg.setStyle("-fx-font-size: 14px;");
        actionMsg.setWrapText(true);

        // Action: Mark as Read
        btnMarkAsRead.setOnAction(e -> {
            notificationController.markAsRead(notification.getNotificationID());
            actionMsg.setText("Notification marked as read.");
            actionMsg.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Status: Read");
            statusLabel.setStyle("-fx-text-fill: #888; -fx-font-weight: bold;");
        });

        // Action: Delete
        btnDelete.setOnAction(e -> {
            notificationController.deleteNotification(notification.getNotificationID());
            actionMsg.setText("Notification deleted successfully.");
            actionMsg.setStyle("-fx-text-fill: green;");

            // Auto return to customer page after delete
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        javafx.application.Platform.runLater(() ->
                            ViewManager.getInstance().switchScene(new CustomerPage(user).getScene())
                        );
                    }
                },
                1500
            );
        });

        HBox buttonBox = new HBox(20, btnMarkAsRead, btnDelete);
        buttonBox.setAlignment(Pos.CENTER);

        VBox bottomBox = new VBox(15, buttonBox, actionMsg);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        contentBox.getChildren().add(bottomBox);

        root.setCenter(contentBox);

        root.setStyle("-fx-background-color: #ffffff;");

        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }
}