package ua.od.zakhariya.fx.fxml_gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Sphere;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.od.zakhariya.fx.fxml_gui.anim.Shake;
import ua.od.zakhariya.fx.fxml_gui.sub_views.S1Controller;


import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private Button btnAlright, btnOK, btnDir, btnFile, btnFiles;

    @FXML
    private CheckBox checkBox;

    @FXML
    private ChoiceBox<?> choiseBox;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private CubicCurve curve;

    @FXML
    private DatePicker datePicker;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private Label label;

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
    private Sphere sphere;

    @FXML
    private Spinner<?> spinner;

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
    private PieChart pieChart;

    @FXML
    private ImageView imageView;


    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChart.setData(pieChartData);

        pieChartData.addAll(
                new PieChart.Data("100", 100),
                new PieChart.Data("50", 50),
                new PieChart.Data("20", 20)
        );

        btnAlright.setOnAction(event -> {
//            btnAlright.getScene().getWindow().hide();
            btnAlright.setDisable(true);

            showView(event, "sub_views/s1.fxml");
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
            File file = new DirectoryChooser().showDialog(null);
            System.out.println(file);
        });

        btnFile.setOnAction(event -> {
            File file = new FileChooser().showOpenDialog(null); //FileChooserType.OPEN_MULTIPLE
            System.out.println(file);
        });

        btnFiles.setOnAction(event -> {
            List<File> files = new FileChooser().showOpenMultipleDialog(null);
            System.out.println(files);
        });

    }

    public void showView(ActionEvent event, String view) {
        try {
            Button parentButton = (Button) event.getSource();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
            //loader.setLocation(getClass().getResource(view));

            loader.load();

            //TODO: make abstract. create parent class for controllers for common parent and linked resources
            S1Controller s1Controller = loader.getController();
            System.out.println(s1Controller);
            s1Controller.setParentButton(parentButton);

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
                    System.out.println(stage.isShowing());
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

}

