package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.LoginPage;
import view.ViewManager;

public class Main extends Application{
	BorderPane borderPane;
	Scene scene;
	@Override
	public void start(Stage primaryStage) throws Exception {

		ViewManager.getInstance().setStage(primaryStage);
		
		LoginPage login = new LoginPage();
		primaryStage.setScene(login.getScene());
		primaryStage.setTitle("GoVlash Laundry");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
