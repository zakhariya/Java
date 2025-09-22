package ua.od.zakhariya.fx.fxml_gui.view.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;

public class ShinyLabel extends Label {
    private BooleanProperty shiny;

    public ShinyLabel() {
        getStyleClass().add("shiny-label");

        shiny = new SimpleBooleanProperty(false);
        shiny.addListener(e -> {
            pseudoClassStateChanged(PseudoClass.getPseudoClass("shiny"), shiny.get());
        });
    }

    public boolean isShiny() {
        return shiny.get();
    }

    public void setShiny(boolean shiny) {
        this.shiny.set(shiny);
    }
}
