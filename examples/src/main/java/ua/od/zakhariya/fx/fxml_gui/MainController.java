package ua.od.zakhariya.fx.fxml_gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import javafx.stage.*;
import ua.od.zakhariya.fx.fxml_gui.anim.Shake;
import ua.od.zakhariya.fx.fxml_gui.sub_views.SubControllerSuper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Accordion accordion;

    @FXML
    private Button btnS1, btnS2, btnS3, btnS4, btnS5, btnOK, btnDir, btnFile, btnFiles;

    @FXML
    private CheckBox checkBox;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private CubicCurve curve;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private ListView<String> listView;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private Label lblChoice, lblControls;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Pagination pagination;

    @FXML
    private Polygon polygonTriangle;

    @FXML
    private ToggleGroup radioGroup1;

    @FXML
    private RadioButton rbtnNo, rbtnYes;

    @FXML
    private Tab tab1;

    @FXML
    private Tab tab2;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextArea textArea;

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextField tfName;

    @FXML
    private PasswordField tfPass;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private ToolBar toolBar;

    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);

        valueFactory.setValue(1);
        spinner.setValueFactory(valueFactory);
        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                lblControls.setText(spinner.getValue().toString());
            }
        });

        btnS1.setOnAction(event -> {
//            btnS1.getScene().getWindow().hide();
            btnS1.setDisable(true);

            showView(event, "sub_views/s1.fxml");
        });

        btnS2.setOnAction(event -> {
            btnS2.setDisable(true);

            showView(event, "sub_views/s2.fxml");
        });

        btnS3.setOnAction(event -> {
            btnS3.setDisable(true);

            showView(event, "sub_views/s3.fxml");
        });

        btnS4.setOnAction(event -> {
            btnS4.setDisable(true);

            showView(event, "sub_views/s4.fxml");
        });

        btnS5.setOnAction(event -> {
            btnS5.setDisable(true);

            showView(event, "sub_views/s5.fxml");
        });

        btnOK.setOnAction(event -> {
            System.out.println("OK button pressed");

            if (tfName.getText().trim().length() < 3) {
                Shake shake = new Shake(tfName);
                shake.playAnim();

                RadioButton selectedRadio = (RadioButton) radioGroup1.getSelectedToggle();
                System.out.println(selectedRadio.getText() + " selected");
            }
        });

        btnDir.setOnAction(event -> {
            Window window = ((Button) event.getSource()).getScene().getWindow();
            File file =
                    new DirectoryChooser().showDialog(window);

            System.out.println(file);
        });

        btnFile.setOnAction(event -> {
            Window window = ((Button) event.getSource()).getScene().getWindow();
            //FileChooserType.OPEN_MULTIPLE
            File file =
                    new FileChooser().showOpenDialog(window);

            System.out.println(file);
        });

        btnFiles.setOnAction(event -> {
            Window window = ((Button) event.getSource()).getScene().getWindow();
            List<File> files =
                    new FileChooser().showOpenMultipleDialog(window);

            System.out.println(files);
        });

        String[] cdData = {"One", "Two", "Three"};

        listView.getItems().addAll(cdData);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblControls.setText(listView.getSelectionModel().getSelectedItem());
            }
        });

        choiceBox.getItems().addAll(cdData);
        choiceBox.setOnAction(this::updateChoiceValue);

        comboBox.getItems().addAll(cdData);
        comboBox.setOnAction(this::updateChoiceValue);


        TreeItem<String> rootItem =
                new TreeItem<>(
                        "Files",
                        new ImageView(
                                new Image("folder.png", 20.0, 15.0, true, true)
                        )
                );

        TreeItem<String> branchItem1 = new TreeItem<>("Video");
        TreeItem<String> branchItem2 = new TreeItem<>("Games");
        TreeItem<String> branchItem3 = new TreeItem<>("Soft");

        TreeItem<String> leafItem1 = new TreeItem<>("F1");
        TreeItem<String> leafItem2 = new TreeItem<>("F2");
        TreeItem<String> leafItem3 = new TreeItem<>("F3");
        TreeItem<String> leafItem4 = new TreeItem<>("F4");
        TreeItem<String> leafItem5 = new TreeItem<>("F5");
        TreeItem<String> leafItem6 = new TreeItem<>("F6");

        branchItem1.getChildren().addAll(leafItem1, leafItem2);
        branchItem2.getChildren().addAll(leafItem3, leafItem4);
        branchItem3.getChildren().addAll(leafItem5, leafItem6);
        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3);

//        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);
    }

    public void updateChoiceValue(ActionEvent event) {
        // can be Object
        Control cbType = (Control) event.getSource();
        String value;

        if (cbType instanceof ChoiceBox) {
            value = ((ChoiceBox) cbType).getValue().toString();
        } else {
            value = ((ComboBox) cbType).getValue().toString();
        }

        lblChoice.setText(value);
    }

    public void showView(ActionEvent event, String view) {
        try {
            Button parentButton = (Button) event.getSource();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
            //loader.setLocation(getClass().getResource(view));

            loader.load();

            SubControllerSuper controller = loader.getController();
            controller.setParentButton(parentButton);

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());

//            When using showAndWait(), the setOnCloseRequest() handler should be set before calling showAndWait()
            stage.setOnCloseRequest(event1 -> {
//                event1.consume();// stop the event to do something before quitting
                if (parentButton != null) {
                    parentButton.setDisable(false);
                }
            });

            stage.showAndWait();
//            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeImage(ActionEvent event) {
        if (checkBox.isSelected()) {
            imageView.setImage(new Image("l.png"));
        } else {
            imageView.setImage(new Image("r.png"));
        }
    }

    public void getDate(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        String formatedDate = date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        lblControls.setText(formatedDate);
    }

    public void changeColor(ActionEvent event) {
        Color color = colorPicker.getValue();

        lblControls.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void selectItem() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null) {
            lblChoice.setText(item.getValue());
        }
    }

    public void exit(ActionEvent event) {
        //TODO: need to cast for MenuItem somehow
        Node component = (Node) event.getSource();
        Stage stage = (Stage) component.getScene().getWindow();

        exit(stage);
    }

    public void exit(Stage stage) {
//        (Stage) scene.getScene()getWindow().close();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit now?");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());

//        alert.showAndWait()
//                .filter(response -> response == ButtonType.YES)
//                .ifPresent(response -> { stage.close(); });

        if (alert.showAndWait().get() == ButtonType.YES) {
            stage.close();
        }
    }
}

