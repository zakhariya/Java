package ua.od.zakhariya.fx.fxml_gui.sub_views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class S1Controller extends SubControllerSuper{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Button btnOk;

    @FXML
    void initialize() {
        btnOk.setOnAction(event -> {
            btnOk.getScene().getWindow().hide();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            if (parentButton != null) {
                parentButton.setDisable(false);
                System.out.println(stage.isShowing());
            }

            System.out.println(btnOk.getId() + " clicked");
        });
    }
}
