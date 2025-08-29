package ua.od.zakhariya.fx.java_code_gui.components;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FXGraphicsExample {

    private static FXGraphicsExample instance;

    private Circle circle = new Circle(50);
    private Rectangle rectangle = new Rectangle(200, 50);
    private Polygon triangle = new Polygon(110, 115, 125, 125, 115, 110);
    private Line line = new Line(20, 45, 30, 45);
    private Text text1 = new Text("ABC");
    private Text text2 = new Text("XYZBGH");
    private ImageView imageView;

    public static synchronized FXGraphicsExample getInstance() throws FileNotFoundException {
        if (instance == null) {
            instance = new FXGraphicsExample();
        }

        return instance;
    }

    private FXGraphicsExample() throws FileNotFoundException {
        triangle.getPoints().setAll(
                200.0, 200.0,
                300d, 300d,
                200., 300.
        );
        triangle.setFill(Color.AQUAMARINE);

        line.setStrokeWidth(5);
        line.setRotate(45);

        circle.setCenterX(100);
        circle.setCenterY(100);
        circle.setFill(Color.BLUE);
        circle.setStroke(Color.RED);
        circle.setStrokeWidth(10);
        circle.setCursor(Cursor.CLOSED_HAND);

        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
            }
        });

        Image image = new Image(new FileInputStream("src/main/resources/add.png"));
        imageView = new ImageView(image);

        Line line = new Line(100, 200, 300, 200);

        PathTransition pathTransition = new PathTransition(Duration.millis(10000), line, imageView);
        pathTransition.setCycleCount(2);
        pathTransition.setAutoReverse(true);
        pathTransition.play();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), imageView);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(30);
        fadeTransition.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            boolean b = true;

            @Override
            public void handle(ActionEvent event) {
                if (b) {
                    circle.setFill(Color.BLUE);
                    circle.setStroke(Color.RED);
                } else {
                    circle.setFill(Color.RED);
                    circle.setStroke(Color.BLUE);
                }
                b= !b;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        text1.setX(200);
        text1.setY(200);

        Font font = Font.font("Arial", FontWeight.LIGHT, FontPosture.ITALIC, 24);
        text1.setFont(font);

        text1.setStyle("-fx-fill: green;");
        text1.setStyle("-fx-font-weight: bold; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);");

        text2.setFill(new Color(1.0, 0.5, 0, 0.6));
        text2.setStyle("-fx-font-weight: bold;");
        text2.setX(200);
        text2.setY(300);
    }

    public Circle getCircle() {
        return circle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Polygon getTriangle() {
        return triangle;
    }

    public Text getText1() {
        return text1;
    }

    public Text getText2() {
        return text2;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Line getLine() {
        return line;
    }
}
