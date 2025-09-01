package ua.od.zakhariya.fx.fxml_gui.sub_views;

import javafx.scene.control.Button;

public abstract class SubControllerSuper {

    protected Button parentButton;

    public void setParentButton(Button parentButton) {
        this.parentButton = parentButton;
    }
}
