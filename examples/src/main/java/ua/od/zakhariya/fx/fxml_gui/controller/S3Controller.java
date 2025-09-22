package ua.od.zakhariya.fx.fxml_gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import ua.od.zakhariya.fx.fxml_gui.anim.Blink;
import ua.od.zakhariya.fx.fxml_gui.anim.Fade;
import ua.od.zakhariya.fx.fxml_gui.anim.Move;
import ua.od.zakhariya.fx.fxml_gui.anim.Scale;

public class S3Controller extends SubControllerSuper {

    @FXML
    private Sphere sphere;

    @FXML
    private Polygon triangle;

    @FXML
    private Rectangle rectangle1, rectangle2;

    @FXML
    private PieChart pieChart;

    @FXML
    private Slider slider;

    @FXML
    private ScrollBar scrollV;


    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChart.setData(pieChartData);

        pieChartData.addAll(
                new PieChart.Data("100", 100),
                new PieChart.Data("50", 50),
                new PieChart.Data("20", 20)
        );


        rectangle1.widthProperty().bind(scrollV.valueProperty().multiply(2));

        slider.valueProperty().addListener(observable -> {
            rectangle1.setHeight(slider.getValue() * 2);
        });

        Blink blink = new Blink(rectangle1);
        blink.playAnim();

        Fade fade = new Fade(rectangle2);
        fade.playAnim();

        Move move1 = new Move(rectangle2);
        move1.playPathAnim();
        move1.playRotateAnim(Rotate.X_AXIS);

        Move move2 = new Move(triangle);
        move2.playTranslateAnim();
        move2.playRotateAnim(Rotate.Z_AXIS);

        Scale scale = new Scale(triangle);
        scale.playAnim();
    }
}
