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

public class AdminPage {

    private User user;
    private Scene scene;

    private BorderPane root;
    private TabPane tabPane;

    // Controllers
    private ServiceController serviceController;
    private UserController userController;
    private TransactionController transactionController;
    private NotificationController notificationController;

    // Tabs
    private Tab tabManageServices;
    private Tab tabManageEmployees;
    private Tab tabViewTransactions;

    // For service management
    private TableView<Service> serviceTable;
    private Button btnAddService;
    private Button btnEditService;
    private Button btnDeleteService;
    private Service selectedService;
    
    private Label messageLabel;

    public AdminPage(User user) {
        this.user = user;
        this.serviceController = new ServiceController();
        this.userController = new UserController();
        this.transactionController = new TransactionController();
        this.notificationController = new NotificationController();

        initializeUI();
        setupTabs();
        loadInitialData();
        

        scene = new Scene(root, 1000, 750);
    }

    private void initializeUI() {
        root = new BorderPane();

        Label welcomeLabel = new Label("Admin Dashboard - " + user.getUserName());
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
        tabManageServices = new Tab("Manage Services");
        tabManageServices.setClosable(false);
        tabManageServices.setContent(createManageServicesTab());

        tabManageEmployees = new Tab("Manage Employees");
        tabManageEmployees.setClosable(false);
        tabManageEmployees.setContent(createManageEmployeesTab());

        tabViewTransactions = new Tab("View Transactions");
        tabViewTransactions.setClosable(false);
        tabViewTransactions.setContent(createViewTransactionsTab());

        tabPane.getTabs().addAll(tabManageServices, tabManageEmployees, tabViewTransactions);
        loadServices();
    }

    private VBox createManageServicesTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("Service Management");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        serviceTable = new TableView<>();
        serviceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        serviceTable.setPlaceholder(new Label("No services available."));

        TableColumn<Service, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));

        TableColumn<Service, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

        TableColumn<Service, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

        TableColumn<Service, Double> priceCol = new TableColumn<>("Price/kg");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

        TableColumn<Service, Integer> durationCol = new TableColumn<>("Duration (days)");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("serviceDuration"));

        serviceTable.getColumns().addAll(idCol, nameCol, descCol, priceCol, durationCol);

        // Selection listener
        serviceTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            selectedService = newVal;
            boolean selected = newVal != null;
            btnEditService.setDisable(!selected);
            btnDeleteService.setDisable(!selected);
        });

        btnAddService = new Button("Add New Service");
        btnAddService.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnAddService.setOnAction(e -> ViewManager.getInstance().switchScene(new AddEditServicePage(user, null).getScene()));

        btnEditService = new Button("Edit Selected");
        btnEditService.setDisable(true);
        btnEditService.setOnAction(e -> {
            if (selectedService != null) {
                ViewManager.getInstance().switchScene(new AddEditServicePage(user, selectedService).getScene());
            }
        });

        btnDeleteService = new Button("Delete Selected");
        btnDeleteService.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        btnDeleteService.setDisable(true);
        btnDeleteService.setOnAction(e -> {
            if (selectedService != null) {
                serviceController.deleteService(selectedService.getServiceID());
                loadServices(); // refresh
                showMessage(vbox, "Service deleted successfully.", "green");
            }
        });

        HBox buttonBox = new HBox(15, btnAddService, btnEditService, btnDeleteService);
        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(title, serviceTable, buttonBox);
        return vbox;
    }

    private VBox createManageEmployeesTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("Employee Management");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TableView<User> employeeTable = new TableView<>();
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        employeeTable.setPlaceholder(new Label("No employees found."));

        TableColumn<User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("userRole"));

        employeeTable.getColumns().addAll(idCol, nameCol, emailCol, roleCol);

        Button btnAddEmployee = new Button("Add New Employee");
        btnAddEmployee.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnAddEmployee.setOnAction(e -> ViewManager.getInstance().switchScene(new AddEmployeePage(user).getScene()));

        vbox.getChildren().addAll(title, employeeTable, btnAddEmployee);

        // Load employees when tab selected
        tabManageEmployees.setOnSelectionChanged(e -> {
        	List<User> employees = null;
            if (tabManageEmployees.isSelected()) {
                employees = userController.getAllEmployees(); 
                if (employees == null || employees.isEmpty()) {
                    employeeTable.setItems(FXCollections.emptyObservableList());
                } else {
                    employeeTable.setItems(FXCollections.observableArrayList(employees));
                }
            }
        });

        return vbox;
    }

    private VBox createViewTransactionsTab() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));

        Label title = new Label("All Transactions");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TableView<Transaction> allTable = new TableView<>();
        allTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        allTable.setPlaceholder(new Label("Loading transactions..."));

        setupTransactionColumns(allTable);

        Label finishedTitle = new Label("Finished Transactions");
        finishedTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 20 0 10 0;");

        TableView<Transaction> finishedTable = new TableView<>();
        finishedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        finishedTable.setPlaceholder(new Label("No finished transactions."));

        setupTransactionColumns(finishedTable);

        Button btnSendNotif = new Button("Send Notification to Customer");
        btnSendNotif.setStyle("-fx-background-color: #337ab7; -fx-text-fill: white; -fx-font-size: 16px;");
        btnSendNotif.setOnAction(e -> {
            Transaction selected = finishedTable.getSelectionModel().getSelectedItem();
            if (selected != null && selected.getCustomerID() != null) {
                notificationController.sendNotification(
                    selected.getCustomerID(),
                    "Your laundry order (ID: " + selected.getTransactionID() + ") is finished and ready for pickup!"
                );
                showMessage("Notification sent to customer!", "green");
            } else {
                showMessage("Please select a finished transaction.", "red");
            }
        });

        // Dedicated message label
        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px;");
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(Double.MAX_VALUE);

        vbox.getChildren().addAll(title, allTable, finishedTitle, finishedTable, btnSendNotif, messageLabel);

        // Load data when tab selected
        tabViewTransactions.setOnSelectionChanged(e -> {
            if (tabViewTransactions.isSelected()) {
                loadAllTransactions(allTable);
                loadFinishedTransactions(finishedTable);
            }
        });

        return vbox;
    }

    private void setupTransactionColumns(TableView<Transaction> table) {
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
    }

    private void loadInitialData() {
        tabManageServices.setOnSelectionChanged(e -> {
            if (tabManageServices.isSelected()) {
                loadServices();
            }
        });
    }

    private void loadServices() {
        VBox container = (VBox) tabManageServices.getContent();
        TableView<Service> table = (TableView<Service>) container.getChildren().get(1);

        List<Service> services = serviceController.getAllServices();
        if (services != null && !services.isEmpty()) {
            table.setItems(FXCollections.observableArrayList(services));
        } else {
            table.setItems(FXCollections.emptyObservableList());
        }
    }

    private void loadAllTransactions(TableView<Transaction> table) {
        List<Transaction> all = transactionController.getAllTransactions();
        if (all != null && !all.isEmpty()) {
            table.setItems(FXCollections.observableArrayList(all));
        } else {
            table.setItems(FXCollections.emptyObservableList());
        }
    }

    private void loadFinishedTransactions(TableView<Transaction> table) {
        List<Transaction> finished = transactionController.getTransactionByStatus("Finished");
        if (finished != null && !finished.isEmpty()) {
            table.setItems(FXCollections.observableArrayList(finished));
        } else {
            table.setItems(FXCollections.emptyObservableList());
        }
    }
    private void showMessage(String text, String color) {
        messageLabel.setText(text);
        messageLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-font-size: 14px;");

        // Auto clear after 3 seconds
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> messageLabel.setText(""));
            }
        }, 3000);
    }

    private void showMessage(VBox parent, String text, String color) {
        Label msg = new Label(text);
        msg.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 14px; -fx-font-weight: bold;");
        if (parent.getChildren().size() > 3) {
            parent.getChildren().remove(parent.getChildren().size() - 1);
        }
        parent.getChildren().add(msg);
    }

    public Scene getScene() {
        return scene;
    }
}