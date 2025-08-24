package ua.od.zakhariya.fx.fxml_gui.anim;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition transition;

    public Shake(Node node) {
        transition = new TranslateTransition(Duration.millis(100), node);
        transition.setFromX(0);
        transition.setByX(5);
        transition.setCycleCount(4);
        transition.setAutoReverse(true);
    }

    public void playAnim() {
        transition.playFromStart();
    }
}
