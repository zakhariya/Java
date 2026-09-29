package ua.od.zakhariya.fx.fxml_gui.variables_inject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VarInjectController {
    // 1. Define the property variable (DATA BINDING EXAMPLE)
    private final StringProperty labelText = new SimpleStringProperty("Text from controller's variable");
/*
    FXMLLoader loader = new FXMLLoader(getClass().getResource("YourFile.fxml"));

    // Inject key-value pairs directly into the FXML context
    loader.getNamespace().put("appVersion", "v2.1.0");

    Parent root = loader.load();

    //fxml file
    <Label text="${appVersion}" />
*/


    // 2. Provide the property getter (required for JavaFX binding)
    public StringProperty labelTextProperty() {
        return labelText;
    }

    public String getLabelText() {
        return labelText.get();
    }
}
