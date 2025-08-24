package ua.od.zakhariya.fx.java_code_gui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
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
    private RadioButton radioButton1 = new RadioButton("YES");
    private RadioButton radioButton2 = new RadioButton("NO");
    private TextArea textArea = new TextArea();
    private ComboBox comboBox = new ComboBox();
    private ListView listView = new ListView();
    private ListCell listCell = new ListCell();
    private ScrollBar scrollBar = new ScrollBar();
    private Slider slider = new Slider();

    public static synchronized FXComponentsExample getInstance() throws FileNotFoundException {
        if (instance == null) {
            instance = new FXComponentsExample();
        }

        return instance;
    }

    private FXComponentsExample() throws FileNotFoundException {

        graphicsExample = FXGraphicsExample.getInstance();

        label = new Label("THUTYY", new Circle(20, new Color(0.2, 0.7, 1.0, 1)));
        label.setContentDisplay(ContentDisplay.BOTTOM);

        checkBox.setSelected(true);

        button1.setOnAction(new ButtonListener(graphicsExample.getCircle()));

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphicsExample.getCircle().setCenterX(100);
                graphicsExample.getCircle().setCenterY(100);
            }
        });

        textField.setOnKeyTyped(event -> {
            textField.setText(textField.getText() + " bla bla");
        });

        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);

        radioButton1.setSelected(true);

        textArea.setMaxWidth(150.0);

        listView.setMaxWidth(100);

        scrollBar.setValue(50.0);
        graphicsExample.getRectangle().widthProperty().bind(scrollBar.valueProperty().multiply(2));


        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMax(120.0);
        slider.setValue(35);

        slider.valueProperty().addListener(observable -> {
            graphicsExample.getRectangle().setHeight(slider.getValue() * 2);
        });
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

    public RadioButton getRadioButton1() {
        return radioButton1;
    }

    public RadioButton getRadioButton2() {
        return radioButton2;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

    public ListView getListView() {
        return listView;
    }

    public ListCell getListCell() {
        return listCell;
    }

    public ScrollBar getScrollBar() {
        return scrollBar;
    }

    public Slider getSlider() {
        return slider;
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
