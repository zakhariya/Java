package ua.od.zakhariya.fx.fxml_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.od.zakhariya.fx.fxml_gui.controller.MainController;

public class FXMLExampleMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainView.fxml"));

        Parent root = loader.load();
        MainController controller = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("assets/style.css").toExternalForm());

        primaryStage.getIcons().addAll(new Image("logo_up_32x32.png"), new Image("logo_up_16x16.png"));
//        primaryStage.getIcons().addAll(new Image(new FileInputStream("src/main/resources/logo.ico")));
        primaryStage.setTitle("FXML example");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();// stop the event to do something before quitting
            controller.exit(primaryStage);
        });

        System.out.println("Java version: " + System.getProperty("java.version") + " JavaFX version " + System.getProperty("javafx.version"));
    }
}
