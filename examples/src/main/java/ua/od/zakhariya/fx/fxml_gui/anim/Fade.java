package ua.od.zakhariya.fx.fxml_gui.anim;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Fade {

    private FadeTransition transition;

    public Fade(Node node) {
        transition = new FadeTransition(Duration.millis(3000), node);
        transition.setFromValue(1.0);
        transition.setToValue(0.1);
        transition.setAutoReverse(true);
        transition.setCycleCount(FadeTransition.INDEFINITE);
    }

    public void playAnim() {
        transition.play();
    }
}
