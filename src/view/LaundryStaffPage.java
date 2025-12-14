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
import java.util.stream.Collectors;

public class LaundryStaffPage {

    private User laundryStaff;
    private Scene scene;

    private BorderPane root;
    private TabPane tabPane;

    private TransactionController transactionController;

    private TableView<Transaction> assignedTable;
    private TableView<Transaction> finishedTable;
    private Button btnFinish;
    private Transaction selectedTransaction;
    private Label messageLabel;

    public LaundryStaffPage(User laundryStaff) {
        this.laundryStaff = laundryStaff;
        this.transactionController = new TransactionController();

        initializeUI();
        setupTabs();
        loadInitialData();

        scene = new Scene(root, 1000, 700);
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
        Tab tabAssigned = new Tab("Assigned Orders");
        tabAssigned.setClosable(false);
        tabAssigned.setContent(createAssignedOrdersTab());

        Tab tabFinished = new Tab("Finished Orders");
        tabFinished.setClosable(false);
        tabFinished.setContent(createFinishedOrdersTab());

        tabPane.getTabs().addAll(tabAssigned, tabFinished);
    }

    private VBox createAssignedOrdersTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("Currently Assigned Orders");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        assignedTable = new TableView<>();
        assignedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        assignedTable.setPlaceholder(new Label("No orders currently assigned to you."));

        setupTransactionColumns(assignedTable);

        // Selection for "Mark as Finished"
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
        btnRefresh.setOnAction(e -> {
            loadAssignedOrders();
            loadFinishedOrders();
        });

        HBox buttonBox = new HBox(15, btnFinish, btnRefresh);
        buttonBox.setAlignment(Pos.CENTER);

        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px;");
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(Double.MAX_VALUE);

        vbox.getChildren().addAll(title, assignedTable, buttonBox, messageLabel);

        return vbox;
    }

    private VBox createFinishedOrdersTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("Completed Orders");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        finishedTable = new TableView<>();
        finishedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        finishedTable.setPlaceholder(new Label("No finished orders yet."));

        setupTransactionColumns(finishedTable);

        vbox.getChildren().addAll(title, finishedTable);

        return vbox;
    }

    private void setupTransactionColumns(TableView<Transaction> table) {
        TableColumn<Transaction, Integer> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Order Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        TableColumn<Transaction, Double> weightCol = new TableColumn<>("Weight (kg)");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));

        TableColumn<Transaction, String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("transactionNotes"));

        TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));

        table.getColumns().addAll(idCol, dateCol, weightCol, notesCol, statusCol);
    }

    private void loadInitialData() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null) {
                if ("Assigned Orders".equals(newTab.getText())) {
                    loadAssignedOrders();
                } else if ("Finished Orders".equals(newTab.getText())) {
                    loadFinishedOrders();
                }
            }
        });
    }

    private void loadAssignedOrders() {
        Integer staffID = laundryStaff.getUserID();
        if (staffID == null) {
            showMessage("Error: User session invalid.", "red");
            return;
        }

        List<Transaction> allAssigned = transactionController.getAssignedOrdersByLaundryStaffID(staffID);
        List<Transaction> assigned = allAssigned.stream()
                .filter(t -> "Assigned".equals(t.getTransactionStatus()))
                .collect(Collectors.toList());

        if (!assigned.isEmpty()) {
            assignedTable.setItems(FXCollections.observableArrayList(assigned));
            showMessage("", "");
        } else {
            assignedTable.setItems(FXCollections.emptyObservableList());
            showMessage("No active orders assigned to you.", "gray");
        }
    }

    private void loadFinishedOrders() {
        Integer staffID = laundryStaff.getUserID();
        if (staffID == null) return;

        List<Transaction> allAssigned = transactionController.getAssignedOrdersByLaundryStaffID(staffID);
        List<Transaction> finished = allAssigned.stream()
                .filter(t -> "Finished".equals(t.getTransactionStatus()))
                .collect(Collectors.toList());

        if (!finished.isEmpty()) {
            finishedTable.setItems(FXCollections.observableArrayList(finished));
        } else {
            finishedTable.setItems(FXCollections.emptyObservableList());
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