package view;

import controller.TransactionController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Transaction;
import model.User;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReceptionistPage {

    private User receptionist;
    private Scene scene;

    private BorderPane root;
    private TabPane tabPane;

    private TransactionController transactionController;
    private UserController userController;

    private TableView<Transaction> pendingTable;
    private TableView<User> staffTable;
    private Button btnAssign;
    private Transaction selectedTransaction;
    private User selectedStaff;
    private Label messageLabel;  // For success/error messages

    public ReceptionistPage(User receptionist) {
        this.receptionist = receptionist;
        this.transactionController = new TransactionController();
        this.userController = new UserController();

        initializeUI();
        setupTabs();
        loadInitialData();

        scene = new Scene(root, 1000, 700);
    }

    private void initializeUI() {
        root = new BorderPane();

        // Top bar
        Label welcomeLabel = new Label("Receptionist Dashboard - " + receptionist.getUserName());
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
        Tab tabAssignOrders = new Tab("Assign Orders to Laundry Staff");
        tabAssignOrders.setClosable(false);
        tabAssignOrders.setContent(createAssignOrdersTab());

        tabPane.getTabs().add(tabAssignOrders);
    }

    private VBox createAssignOrdersTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("Assign Pending Orders");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Pending Transactions Table
        Label pendingLabel = new Label("Pending Transactions");
        pendingLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        pendingTable = new TableView<>();
        pendingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pendingTable.setPlaceholder(new Label("No pending transactions."));

        TableColumn<Transaction, Integer> pendIdCol = new TableColumn<>("ID");
        pendIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, Date> pendDateCol = new TableColumn<>("Date");
        pendDateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        TableColumn<Transaction, Double> pendWeightCol = new TableColumn<>("Weight (kg)");
        pendWeightCol.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));

        TableColumn<Transaction, String> pendNotesCol = new TableColumn<>("Notes");
        pendNotesCol.setCellValueFactory(new PropertyValueFactory<>("transactionNotes"));

        pendingTable.getColumns().addAll(pendIdCol, pendDateCol, pendWeightCol, pendNotesCol);

        pendingTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            selectedTransaction = newVal;
            updateAssignButton();
        });

        // Laundry Staff Table
        Label staffLabel = new Label("Available Laundry Staff");
        staffLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 20 0 10 0;");

        staffTable = new TableView<>();
        staffTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        staffTable.setPlaceholder(new Label("No laundry staff available."));

        TableColumn<User, Integer> staffIdCol = new TableColumn<>("ID");
        staffIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        TableColumn<User, String> staffNameCol = new TableColumn<>("Name");
        staffNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<User, String> staffEmailCol = new TableColumn<>("Email");
        staffEmailCol.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

        staffTable.getColumns().addAll(staffIdCol, staffNameCol, staffEmailCol);

        staffTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            selectedStaff = newVal;
            updateAssignButton();
        });

        // Assign Button
        btnAssign = new Button("Assign Selected Order to Selected Staff");
        btnAssign.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 16px;");
        btnAssign.setPrefWidth(400);
        btnAssign.setDisable(true);

        btnAssign.setOnAction(e -> {
            if (selectedTransaction != null && selectedStaff != null) {
                transactionController.assignOrderToLaundryStaff(
                        selectedTransaction.getTransactionID(),
                        receptionist.getUserID(),
                        selectedStaff.getUserID()
                );

                showMessage("Order #" + selectedTransaction.getTransactionID() + 
                           " assigned successfully to " + selectedStaff.getUserName() + "!", "green");

                pendingTable.getSelectionModel().clearSelection();
                staffTable.getSelectionModel().clearSelection();
            }
        });

        // Refresh Button
        Button btnRefresh = new Button("Refresh Data");
        btnRefresh.setStyle("-fx-background-color: #337ab7; -fx-text-fill: white;");
        btnRefresh.setOnAction(e -> {
            loadPendingTransactions();
            loadLaundryStaff();
        });

        HBox buttonBox = new HBox(15, btnAssign, btnRefresh);
        buttonBox.setAlignment(Pos.CENTER);

        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px;");
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(Double.MAX_VALUE);

        vbox.getChildren().addAll(title, pendingLabel, pendingTable, staffLabel, staffTable, buttonBox, messageLabel);

        return vbox;
    }

    private void updateAssignButton() {
        btnAssign.setDisable(selectedTransaction == null || selectedStaff == null);
    }

    private void loadInitialData() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null && "Assign Orders to Laundry Staff".equals(newTab.getText())) {
                loadPendingTransactions();
                loadLaundryStaff();
            }
        });
    }

    private void loadPendingTransactions() {
        List<Transaction> pending = transactionController.getTransactionByStatus("Pending");  // ← Correct status!
        if (pending != null && !pending.isEmpty()) {
            pendingTable.setItems(FXCollections.observableArrayList(pending));
            clearMessage();
        } else {
            pendingTable.setItems(FXCollections.emptyObservableList());
            showMessage("No pending transactions to assign.", "gray");
        }
    }

    private void loadLaundryStaff() {
        List<User> allEmployees = userController.getAllEmployees();
        List<User> laundryStaff = allEmployees.stream()
                .filter(u -> u != null && "LaundryStaff".equals(u.getUserRole()))
                .collect(Collectors.toList());

        if (!laundryStaff.isEmpty()) {
            staffTable.setItems(FXCollections.observableArrayList(laundryStaff));
            clearMessage();
        } else {
            staffTable.setItems(FXCollections.emptyObservableList());
            showMessage("No laundry staff available.", "gray");
        }
    }

    private void showMessage(String text, String color) {
        messageLabel.setText(text);
        messageLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-font-size: 14px;");
    }

    private void clearMessage() {
        messageLabel.setText("");
    }

    public Scene getScene() {
        return scene;
    }
}