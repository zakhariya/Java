package ua.od.zakhariya.fx.fxml_gui.variables_inject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VarInjectApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VarInjectView.fxml"));

        System.out.println(getClass().getResource("VarInjectView.fxml"));

        // 1. Detect if running inside an IDE
        boolean isIDE = System.getProperty("java.class.path").contains("idea_rt.jar") ||
                System.getProperty("java.class.path").contains("eclipse");

        // 2. Set different paths based on the launch environment
        String baseIconPath;
        if (isIDE) {
            baseIconPath = "/images/ide-icons/"; // Path when coding inside your IDE
        } else {
            baseIconPath = "/images/console-icons/"; // Path when running packaged code
        }

        // 3. Inject the variable directly into the FXML context BEFORE loading
//        loader.getNamespace().put("iconPath", baseIconPath);

        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

