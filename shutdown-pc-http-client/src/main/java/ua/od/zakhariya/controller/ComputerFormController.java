package ua.od.zakhariya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ua.od.zakhariya.entity.Computer;
import ua.od.zakhariya.model.Data;

public class ComputerFormController {

    @FXML
    private Label lblMessage;

    @FXML
    private TextField txtName, txtHost, txtPort, txtUrl, txtMac;

    public void save(ActionEvent event) {
        if (!validateFields()) return;

        Computer computer = new Computer(txtName.getText(), txtHost.getText(), Integer.valueOf(txtPort.getText()), txtUrl.getText());

        Data.getInstance().save(computer);

        ((Button)event.getSource()).getScene().getWindow().hide();
    }

    public void cancel(ActionEvent event) {
        ((Button)event.getSource()).getScene().getWindow().hide();
    }

    private boolean validateFields() {

        //TODO: add fields validation

        return isFieldFilled(txtName) && isFieldFilled(txtHost)
                && isFieldFilled(txtPort) && isFieldFilled(txtUrl);
    }

    private boolean isFieldFilled(TextField field) {
        return field.getText().length() > 0;
    }
}
