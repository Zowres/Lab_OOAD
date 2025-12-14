package view;

import controller.*;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.*;
import java.util.List;

public class CustomerPage {

    private User user;
    private Scene scene;

    private BorderPane root;
    private TabPane tabPane;

    // Controllers
    private TransactionController transactionController;
    private NotificationController notificationController;
    private ServiceController serviceController;

    // Tabs
    private Tab tabOrderService;
    private Tab tabTransactionHistory;
    private Tab tabNotifications;

    // For service selection
    private TableView<Service> serviceTable;
    private Button btnPlaceOrder;
    private Service selectedService; 

    public CustomerPage(User user) {
        this.user = user;
        this.transactionController = new TransactionController();
        this.notificationController = new NotificationController();
        this.serviceController = new ServiceController();

        initializeUI();
        setupTabs();
        loadInitialData();

        scene = new Scene(root, 900, 700);
    }

    private void initializeUI() {
        root = new BorderPane();

        Label welcomeLabel = new Label("Welcome, " + user.getUserName() + "!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16;");
        logoutBtn.setOnAction(e -> ViewManager.getInstance().switchScene(new LoginPage().getScene()));

        HBox topBox = new HBox(20, welcomeLabel, logoutBtn);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20));
        HBox.setHgrow(welcomeLabel, Priority.ALWAYS);

        root.setTop(topBox);

        tabPane = new TabPane();
        root.setCenter(tabPane);
    }

    private void setupTabs() {
        tabOrderService = new Tab("Order Laundry Service");
        tabOrderService.setClosable(false);
        tabOrderService.setContent(createOrderServiceTab());

        tabTransactionHistory = new Tab("Transaction History");
        tabTransactionHistory.setClosable(false);
        tabTransactionHistory.setContent(createTransactionHistoryTab());

        tabNotifications = new Tab("Notifications");
        tabNotifications.setClosable(false);
        tabNotifications.setContent(createNotificationsTab());

        tabPane.getTabs().addAll(tabOrderService, tabTransactionHistory, tabNotifications);
    }

    private VBox createOrderServiceTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Choose a Laundry Service");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label instruction = new Label("Select a service from the list below, then click 'Place Order'");
        instruction.setStyle("-fx-text-fill: #555; -fx-font-size: 14px;");

        serviceTable = new TableView<>();
        serviceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        serviceTable.setPlaceholder(new Label("No services available."));

        TableColumn<Service, String> nameCol = new TableColumn<>("Service Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

        TableColumn<Service, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

        TableColumn<Service, Double> priceCol = new TableColumn<>("Price (per kg)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

        TableColumn<Service, Integer> durationCol = new TableColumn<>("Duration (days)");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("serviceDuration"));

        serviceTable.getColumns().addAll(nameCol, descCol, priceCol, durationCol);

        serviceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedService = newVal;
            btnPlaceOrder.setDisable(newVal == null);
        });

        btnPlaceOrder = new Button("Place Order");
        btnPlaceOrder.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 16px;");
        btnPlaceOrder.setPrefWidth(200);
        btnPlaceOrder.setDisable(true); 

        btnPlaceOrder.setOnAction(e -> {
            if (selectedService != null) {
                ViewManager.getInstance().switchScene(
                    new OrderFormPage(user, selectedService).getScene()
                );
            }
        });

        Label statusLabel = new Label("Please select a service to continue.");
        statusLabel.setStyle("-fx-text-fill: #888;");

        vbox.getChildren().addAll(
                title,
                instruction,
                serviceTable,
                new Separator(),
                btnPlaceOrder,
                statusLabel
        );

        // Load services
        List<Service> services = serviceController.getAllServices();
        if (services != null && !services.isEmpty()) {
            serviceTable.setItems(FXCollections.observableArrayList(services));
        }

        return vbox;
    }

    private VBox createTransactionHistoryTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("My Transaction History");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TableView<Transaction> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No transactions yet."));

        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        TableColumn<Transaction, Double> weightCol = new TableColumn<>("Weight (kg)");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));

        TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));

        TableColumn<Transaction, String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("transactionNotes"));

        table.getColumns().addAll(idCol, dateCol, weightCol, statusCol, notesCol);

        vbox.getChildren().addAll(title, table);

        return vbox;
    }

    private VBox createNotificationsTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("Notifications");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ListView<Notification> notifListView = new ListView<>();
        notifListView.setPrefHeight(350);
        notifListView.setPlaceholder(new Label("No notifications."));

        notifListView.setCellFactory(lv -> new ListCell<Notification>() {
            @Override
            protected void updateItem(Notification n, boolean empty) {
                super.updateItem(n, empty);
                if (empty || n == null) {
                    setText(null);
                    setStyle("");
                } else {
                    String prefix = n.getIsRead() ? "✓ " : "● New: ";
                    setText(prefix + n.getNotificationMessage());
                    setStyle(n.getIsRead() ? "-fx-text-fill: gray;" : "-fx-font-weight: bold;");
                }
            }
        });

        Button btnView = new Button("View Detail");
        Button btnDelete = new Button("Delete");
        Button btnRefresh = new Button("Refresh");

        HBox buttons = new HBox(15, btnView, btnDelete, btnRefresh);
        buttons.setAlignment(Pos.CENTER);

        Label msgLabel = new Label();

        btnRefresh.setOnAction(e -> loadNotifications(notifListView));

        btnView.setOnAction(e -> {
            Notification selected = notifListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                ViewManager.getInstance().switchScene(new NotificationDetailPage(user, selected).getScene());
            } else {
                msgLabel.setText("Please select a notification.");
                msgLabel.setStyle("-fx-text-fill: red;");
            }
        });

        btnDelete.setOnAction(e -> {
            Notification selected = notifListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                notificationController.deleteNotification(selected.getNotificationID());
                loadNotifications(notifListView);
                msgLabel.setText("Notification deleted.");
                msgLabel.setStyle("-fx-text-fill: green;");
            } else {
                msgLabel.setText("Please select a notification to delete.");
                msgLabel.setStyle("-fx-text-fill: red;");
            }
        });

        vbox.getChildren().addAll(title, notifListView, buttons, msgLabel);

        return vbox;
    }

    private void loadInitialData() {
        tabTransactionHistory.setOnSelectionChanged(e -> {
            if (tabTransactionHistory.isSelected()) {
                loadTransactionHistory();
            }
        });

        tabNotifications.setOnSelectionChanged(e -> {
            if (tabNotifications.isSelected()) {
                ListView<Notification> listView = (ListView<Notification>) ((VBox) tabNotifications.getContent()).getChildren().get(1);
                loadNotifications(listView);
            }
        });
    }

    private void loadTransactionHistory() {
        VBox container = (VBox) tabTransactionHistory.getContent();
        TableView<Transaction> table = (TableView<Transaction>) container.getChildren().get(1);

        List<Transaction> transactions = transactionController.getTransactionsByCustomerID(user.getUserID());
        if (transactions != null && !transactions.isEmpty()) {
            table.setItems(FXCollections.observableArrayList(transactions));
        } else {
            table.setItems(FXCollections.emptyObservableList());
        }
    }

    private void loadNotifications(ListView<Notification> listView) {
        List<Notification> notifications = notificationController.getNotificationByRecipientID(user.getUserID());
        if (notifications != null && !notifications.isEmpty()) {
            listView.setItems(FXCollections.observableArrayList(notifications));
        } else {
            listView.setItems(FXCollections.emptyObservableList());
        }
    }

    public Scene getScene() {
        return scene;
    }
}