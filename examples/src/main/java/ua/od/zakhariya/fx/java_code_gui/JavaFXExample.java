package ua.od.zakhariya.fx.java_code_gui;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ua.od.zakhariya.fx.java_code_gui.components.FXComponentsExample;
import ua.od.zakhariya.fx.java_code_gui.components.FXGraphicsExample;

public class JavaFXExample extends Application {
    private FXComponentsExample componentsExample;
    private FXGraphicsExample graphicsExample;
/*
    public static void main(String[] args) {
        launch(args);
    }
*/

    public void start(Stage primaryStage) throws Exception {
        componentsExample = FXComponentsExample.getInstance();
        graphicsExample = FXGraphicsExample.getInstance();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(new Rectangle(150, 150, new Color(0.5, 0.5, 0.5, 1)));
        stackPane.getChildren().add(componentsExample.getButton1());

        Pane pane = new Pane();
        pane.getChildren().add(graphicsExample.getCircle());
        pane.getChildren().add(graphicsExample.getImageView());

        HBox hBoxTop = new HBox();
        hBoxTop.getChildren().add(componentsExample.getCheckBox());
        hBoxTop.getChildren().add(componentsExample.getComboBox());
        hBoxTop.getChildren().add(componentsExample.getRadioButton1());
        hBoxTop.getChildren().add(componentsExample.getRadioButton2());
        hBoxTop.getChildren().add(componentsExample.getTextField());
        hBoxTop.getChildren().add(componentsExample.getPasswordField());

        graphicsExample.getText2().xProperty().bind(hBoxTop.widthProperty().divide(2));

        HBox hBoxBottom = new HBox();
        hBoxBottom.setAlignment(Pos.BASELINE_CENTER);
        hBoxBottom.getChildren().add(componentsExample.getButton1());
        hBoxBottom.getChildren().add(componentsExample.getButton2());

        GridPane gridPane = new GridPane();
        gridPane.add(new Circle(25), 0, 0);
        gridPane.add(new Text("Blablabla"), 1, 0);

        VBox vBox = new VBox();
        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(componentsExample.getLabel());
        vBox.getChildren().add(new Rectangle(50, 50, new Color(0.5, 0.5, 0.5, 1)));
        vBox.getChildren().add(new Rectangle(50, 50, new Color(0, 0.5, 1, 1)));
        vBox.getChildren().add(graphicsExample.getText1());
        vBox.getChildren().add(graphicsExample.getText2());
        vBox.getChildren().add(graphicsExample.getRectangle());

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.getChildren().setAll(componentsExample.getTextArea(), componentsExample.getListView(),
                componentsExample.getScrollBar(), componentsExample.getSlider());
//        flowPane.getChildren().add(componentsExample.getListCell());

        ScrollPane scrollPane = new ScrollPane(pane);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBoxTop);
        borderPane.setCenter(scrollPane);
        borderPane.setLeft(vBox);
        borderPane.setBottom(hBoxBottom);
        borderPane.setRight(flowPane);

        Scene scene = new Scene(borderPane, 650, 800);

        primaryStage.setScene(scene);
        primaryStage.show();

        scrollPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DOWN) {
                graphicsExample.getCircle().setCenterY(graphicsExample.getCircle().getCenterY() + 10);
            } else if(event.getCode() == KeyCode.UP) {
                graphicsExample.getCircle().setCenterY(graphicsExample.getCircle().getCenterY() - 10);
            } else if(event.getCode() == KeyCode.LEFT) {
                graphicsExample.getCircle().setCenterX(graphicsExample.getCircle().getCenterX() - 10);
            } else if(event.getCode() == KeyCode.RIGHT) {
                graphicsExample.getCircle().setCenterX(graphicsExample.getCircle().getCenterX() + 10);
            }
        });

        scrollPane.requestFocus();


        IntegerProperty a = new SimpleIntegerProperty();
        a.setValue(100);

        IntegerProperty b = new SimpleIntegerProperty();
        b.setValue(200);

        System.out.println(a + " " + b);

        a.bindBidirectional(b);

        a.setValue(300);

        System.out.println(a + " " + b);
    }
}
