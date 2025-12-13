package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.User;

public class CustomerPage {

	private User user;
	private Scene scene;
	
	private BorderPane borderPane;
	private VBox vBox;
	private Label titleLbl;

	public CustomerPage(User user) {
		this.user = user;
		init();
	}
	
	private void init() {
		borderPane = new BorderPane();
		vBox = new VBox(10);
		titleLbl = new Label("Customer Page");
		vBox.getChildren().add(titleLbl);
		borderPane.setTop(vBox);
		scene = new Scene(borderPane, 500, 500);
	}
	
	public Scene getScene() {
		return scene;
	}
}
