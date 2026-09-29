package ua.od.zakhariya.fx.fxml_gui.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class S1Controller extends SubControllerSuper {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane paneUploadFiles;

    @FXML
    private Button btnSelectFiles, btnUploadFiles, btnOk;

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

        paneUploadFiles.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
                System.out.println("event.getDragboard().hasFiles()");
            }
            event.consume();
            System.out.println("event.consume()");
        });

        paneUploadFiles.setOnDragDropped(event -> {
            List<File> files = event.getDragboard().getFiles();
            // Process dropped files
            event.setDropCompleted(true);
            event.consume();

            System.out.println(files);
        });
    }

    public void selectFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Upload");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Optional: Filter for specific file types
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files", "*.txt"),
                new FileChooser.ExtensionFilter("Java archives", "*.jar"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

//        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            System.out.println("Selected: " + selectedFiles.get(0).getAbsolutePath());
            System.out.println(selectedFiles);
            // Trigger your upload logic here
        }
    }

    public void uploadFiles(ActionEvent event) {

    }
}
