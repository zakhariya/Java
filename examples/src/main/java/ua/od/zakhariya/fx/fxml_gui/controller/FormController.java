package ua.od.zakhariya.fx.fxml_gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FormController {

    @FXML
    private TextField txtId, txtName;

    @FXML
    private ComboBox<String> comSex;

    @FXML
    private Button btnSave, btnDiscard;

    @FXML
    void initialize() {
        System.out.println("Form initialized");
    }

    public void save(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String gender = comSex.getSelectionModel().getSelectedItem();

        System.out.println("ID: " + id + ", name: " + name + ", gender: " + gender);
    }

    public void discard(ActionEvent event) {
        System.out.println("Discard changes");
    }
}
