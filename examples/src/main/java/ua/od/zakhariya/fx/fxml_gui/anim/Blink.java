package ua.od.zakhariya.fx.fxml_gui.anim;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class Blink {
    private Timeline timeline;

    public Blink(Node node) {
        timeline =
                new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
                    boolean b = true;

                    @Override
                    public void handle(ActionEvent event) {
                        if (b) {
                            node.setStyle("" +
                                    "-fx-border-color: blue; " +
                                    "-fx-background-color: red;" +
                                    "-fx-fill: red;" +
                                    "-fx-stroke: blue;");
                        } else {
                            node.setStyle("" +
                                    "-fx-border-color: red; " +
                                    "-fx-background-color: blue;" +
                                    "-fx-fill: blue;" +
                                    "-fx-stroke: red;");
                        }
                        b = !b;
                    }
                }));

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void playAnim() {
        timeline.play();
    }
}
