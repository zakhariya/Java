package ua.od.zakhariya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.od.zakhariya.model.Data;

import java.io.IOException;

public class MainController {

    @FXML
    private VBox vBox;

    @FXML
    public void initialize() {
        //TODO: make initialize objects and containers here???

        Data.getInstance().init(vBox);
    }

    public void showAddComputerForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EditComputerDialog.fxml"));
            loader.load();

            Scene primaryScene = ((Button) event.getSource()).getScene();

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryScene.getWindow());
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().addAll(primaryScene.getStylesheets());
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
