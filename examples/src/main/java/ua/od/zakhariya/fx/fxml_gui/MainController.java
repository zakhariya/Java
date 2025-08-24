package ua.od.zakhariya.fx.fxml_gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.tk.FileChooserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Pagination;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Sphere;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ua.od.zakhariya.fx.fxml_gui.anim.Shake;

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

            showView("sub_views/s1.fxml");
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

    public void showView(String view) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(view));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}

