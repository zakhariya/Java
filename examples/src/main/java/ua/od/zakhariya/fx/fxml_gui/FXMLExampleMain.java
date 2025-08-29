package ua.od.zakhariya.fx.fxml_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FXMLExampleMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("FXML example");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();// stop the event to do something before quitting
            exit(primaryStage);
        });
    }

    private void exit(Stage stage) {
//        (Stage) scene.getScene()getWindow().close();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit now?");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());

//        alert.showAndWait()
//                .filter(response -> response == ButtonType.YES)
//                .ifPresent(response -> { stage.close(); });

        if (alert.showAndWait().get() == ButtonType.YES) {
            stage.close();
        }
    }
}
