package ua.od.zakhariya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import ua.od.zakhariya.entity.Computer;
import ua.od.zakhariya.model.Data;

import java.net.URL;
import java.util.ResourceBundle;

public class ComputerController implements Initializable {

    //TODO: make class initializable??
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Circle statusIndicator;

    @FXML
    private Label lblName, lblStatus;

    @FXML
    private Button btnTurnOn, btnTurnOff, btnDelete;


    public void turnOnPC(ActionEvent event) {

    }

    public void turnOffPC(ActionEvent event) {
        Button button = (Button) event.getSource();
        Computer computer = (Computer) button.getUserData();
        String message = computer.shutdown();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(message);
//        alert.setContentText(message);

        alert.showAndWait();
    }

    public void showInfo(ActionEvent event) {
        System.out.println("info button");
    }

    public void save(ActionEvent event) {
        Button button = (Button) event.getSource();
        Computer computer = (Computer) button.getUserData();
        Data.getInstance().save(computer);
    }

    public void deleteComputer(ActionEvent event) {
        Button button = (Button) event.getSource();
        Computer computer = (Computer) button.getUserData();
        Data.getInstance().deleteComputer(computer);
    }

    public Label getLblName() {
        return lblName;
    }

    public Label getLblStatus() {
        return lblStatus;
    }

    public Button getBtnTurnOn() {
        return btnTurnOn;
    }

    public Button getBtnTurnOff() {
        return btnTurnOff;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }
}
