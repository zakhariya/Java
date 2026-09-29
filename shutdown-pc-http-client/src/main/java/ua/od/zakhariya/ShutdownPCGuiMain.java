package ua.od.zakhariya;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.od.zakhariya.controller.MainController;

import java.io.InputStream;
import java.net.URL;

public class ShutdownPCGuiMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        //Use (URL) for IDE
        loader.setLocation(getClass().getResource("view/MainView.fxml"));
        Parent root = loader.load();

//        URL url = ShutdownPCGuiMain.class.getResource("view/MainView.fxml");
//        loader.setLocation(url);

//        //Use (Stream) for Jar archive
//        InputStream in = getClass().getResourceAsStream("view/MainView.fxml");
//        Parent root = loader.load(in);

        MainController controller = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("view/assets/style.css").toExternalForm());

//        primaryStage.getIcons().addAll(new Image("logo_up_32x32.png"), new Image("logo_up_16x16.png"));
//        primaryStage.getIcons().addAll(new Image(new FileInputStream("src/main/resources/logo.ico")));
        primaryStage.setTitle("Shutdown PC GUI");
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("Main thread: " + Thread.currentThread().getId());
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        primaryStage.setOnCloseRequest(event -> {
            event.consume();// stop the event to do something before quitting
//            controller.exit(primaryStage);
            //TODO: make close dialog
            primaryStage.close();

            Platform.exit();
            System.exit(0);
        });
    }
}
