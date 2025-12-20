package ua.od.zakhariya.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ua.od.zakhariya.model.Computer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    public void initialize() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/computers.json");

        try {
            // Read JSON from file and convert to a User object (deserialization)
            List<Computer> computers = mapper.readValue(file, new TypeReference<List<Computer>>(){});

            System.out.println("Object successfully deserialized from JSON file:");
            System.out.println(computers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutDownPC(ActionEvent event) {
        System.out.println("fddgdfgfdgfd");
    }
}
