package ua.od.zakhariya.fx.fxml_gui.anim;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Move {

    private PathTransition path;
    private TranslateTransition translate;
    private RotateTransition rotate;

    private Node node;

    public Move(Node node) {
        this.node = node;
    }

    public void playPathAnim() {
        Line line = new Line(100, 0, 300, 0);

        path = new PathTransition(Duration.millis(10000), line, node);
        path.setCycleCount(PathTransition.INDEFINITE);
        path.setAutoReverse(true);

        path.playFromStart();
    }

    public void playTranslateAnim() {
        translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(250);
        translate.setAutoReverse(true);

        translate.play();
    }

    public void playRotateAnim(Point3D axis) {
        rotate = new RotateTransition();
        rotate.setNode(node);
        rotate.setDuration(Duration.millis(1000));
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(axis);
//        rotate.setAxis(Rotate.Y_AXIS);
//        rotate.setAxis(Rotate.Z_AXIS);

        rotate.play();
    }
}
