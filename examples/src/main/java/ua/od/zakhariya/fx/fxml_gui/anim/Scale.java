package ua.od.zakhariya.fx.fxml_gui.anim;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Scale {

    private ScaleTransition transition;

    public Scale(Node node) {
        transition = new ScaleTransition();
        transition.setNode(node);
        transition.setDuration(Duration.millis(1000));
        transition.setCycleCount(ScaleTransition.INDEFINITE);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setByX(2.0);
        transition.setByY(2.0);
        transition.setAutoReverse(true);
    }

    public void playAnim() {
        transition.play();
    }
}
