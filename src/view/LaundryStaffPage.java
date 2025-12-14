package view;

import controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Transaction;
import model.User;

import java.util.List;

public class LaundryStaffPage {

    private User laundryStaff;
    private Scene scene;

    private BorderPane root;
    private TabPane tabPane;

    private TransactionController transactionController;

    private TableView<Transaction> assignedTable;
    private Button btnFinish;
    private Transaction selectedTransaction;
    private Label messageLabel;  // For messages

    public LaundryStaffPage(User laundryStaff) {
        this.laundryStaff = laundryStaff;
        this.transactionController = new TransactionController();

        initializeUI();
        setupTabs();
        loadInitialData();

        scene = new Scene(root, 900, 650);
    }

    private void initializeUI() {
        root = new BorderPane();

        Label welcomeLabel = new Label("Laundry Staff Dashboard - " + (laundryStaff != null ? laundryStaff.getUserName() : "Unknown"));
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px;");
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
        Tab tabAssignedOrders = new Tab("My Assigned Orders");
        tabAssignedOrders.setClosable(false);
        tabAssignedOrders.setContent(createAssignedOrdersTab());

        tabPane.getTabs().add(tabAssignedOrders);
    }

    private VBox createAssignedOrdersTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("My Assigned Laundry Orders");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        assignedTable = new TableView<>();
        assignedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        assignedTable.setPlaceholder(new Label("No orders assigned to you yet."));

        TableColumn<Transaction, Integer> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Order Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        TableColumn<Transaction, Double> weightCol = new TableColumn<>("Weight (kg)");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));

        TableColumn<Transaction, String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("transactionNotes"));

        TableColumn<Transaction, String> statusCol = new TableColumn<>("Current Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));

        assignedTable.getColumns().addAll(idCol, dateCol, weightCol, notesCol, statusCol);

        assignedTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            selectedTransaction = newVal;
            btnFinish.setDisable(newVal == null);
        });

        btnFinish = new Button("Mark as Finished");
        btnFinish.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 16px;");
        btnFinish.setPrefWidth(250);
        btnFinish.setDisable(true);

        btnFinish.setOnAction(e -> {
            if (selectedTransaction != null) {
                transactionController.updateTransactionStatus(
                        selectedTransaction.getTransactionID(),
                        "Finished"
                );

                showMessage("Order #" + selectedTransaction.getTransactionID() + " marked as Finished!", "green");

                assignedTable.getSelectionModel().clearSelection();
            }
        });

        Button btnRefresh = new Button("Refresh");
        btnRefresh.setStyle("-fx-background-color: #337ab7; -fx-text-fill: white;");
        btnRefresh.setOnAction(e -> loadAssignedOrders());

        HBox buttonBox = new HBox(15, btnFinish, btnRefresh);
        buttonBox.setAlignment(Pos.CENTER);

        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px;");
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(Double.MAX_VALUE);

        vbox.getChildren().addAll(title, assignedTable, buttonBox, messageLabel);

        return vbox;
    }

    private void loadInitialData() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null && "My Assigned Orders".equals(newTab.getText())) {
                loadAssignedOrders();
            }
        });
    }

    private void loadAssignedOrders() {
        Integer staffID = laundryStaff.getUserID();
        if (staffID == null) {
            showMessage("Error: User ID is missing. Please log in again.", "red");
            assignedTable.setItems(FXCollections.emptyObservableList());
            return;
        }
        System.out.println(laundryStaff.getUserID());
        List<Transaction> assigned = transactionController.getAssignedOrdersByLaundryStaffID(staffID);
        if (assigned != null && !assigned.isEmpty()) {
            assignedTable.setItems(FXCollections.observableArrayList(assigned));
            showMessage("", "");  // Clear message
        } else {
            assignedTable.setItems(FXCollections.emptyObservableList());
            showMessage("No orders assigned to you yet.", "gray");
        }
    }

    private void showMessage(String text, String color) {
        messageLabel.setText(text);
        messageLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-font-size: 14px;");
    }

    public Scene getScene() {
        return scene;
    }
}