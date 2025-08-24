package ua.lpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        primaryStage.setTitle("FXML test");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }

}
