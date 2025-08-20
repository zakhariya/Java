package ua.od.zakhariya.fx.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;

import java.io.FileNotFoundException;

public class FXComponentsExample {
    private static FXComponentsExample instance;

    private FXGraphicsExample graphicsExample;

    private CheckBox checkBox = new CheckBox("Check");
    private TextField textField = new TextField("Some text1");
    private TextField passwordField = new PasswordField();
    private Button button1 = new Button("Change radius");
    private Button button2 = new Button("Set default position");
    private Label label;

    public static synchronized FXComponentsExample getInstance() throws FileNotFoundException {
        if (instance == null) {
            instance = new FXComponentsExample();
        }

        return instance;
    }

    private FXComponentsExample() throws FileNotFoundException {

        graphicsExample = FXGraphicsExample.getInstance();

        label = new Label("THUTYY", graphicsExample.getCircle());
        label.setContentDisplay(ContentDisplay.BOTTOM);

        checkBox.setSelected(true);

        button1.setOnAction(new ButtonListener(graphicsExample.getCircle()));

        button2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                graphicsExample.getCircle().setCenterX(100);
                graphicsExample.getCircle().setCenterY(100);
            }
        });

        textField.setOnKeyTyped(event -> {
            textField.setText(textField.getText() + " bla bla");
        });



        RadioButton radioButton1 = new RadioButton("YES");
        RadioButton radioButton2 = new RadioButton("NO");

        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public TextField getTextField() {
        return textField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Button getButton1() {
        return button1;
    }

    public Button getButton2() {
        return button2;
    }

    public Label getLabel() {
        return label;
    }

    class ButtonListener implements EventHandler<ActionEvent> {
        private Circle circle;

        public ButtonListener(Circle circle) {
            this.circle = circle;
        }

        public void handle(ActionEvent event) {
            if (circle.getRadius() == 0) {
                circle.setRadius(100);
            } else {
                circle.setRadius(circle.getRadius() - 10);
            }
        }
    }
}
