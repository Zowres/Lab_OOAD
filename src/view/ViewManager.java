package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {
    private static ViewManager instance;
    private Stage stage;

    private ViewManager() {}

    public static ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void switchScene(Scene scene) {

    	stage.setScene(scene);
    	stage.centerOnScreen();
    	stage.show();
    }

}