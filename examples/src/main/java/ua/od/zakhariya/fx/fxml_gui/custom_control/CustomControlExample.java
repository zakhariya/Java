package ua.od.zakhariya.fx.fxml_gui.custom_control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomControlExample extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CustomControl customControl = new CustomControl();
        customControl.setText("Hello!");
        
        stage.setScene(new Scene(customControl));
        stage.setTitle("Custom Control");
        stage.setWidth(300);
        stage.setHeight(200);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
