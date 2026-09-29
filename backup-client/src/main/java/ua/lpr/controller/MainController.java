package ua.lpr.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainController {

    private final String OS = System.getProperty("os.name");

    @FXML
    private Button btnSelectFiles, btnUploadFiles;

    @FXML
    private AnchorPane paneUploadFiles;

    @FXML
    public void initialize() {
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
