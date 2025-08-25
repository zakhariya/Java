package ua.od.zakhariya.files.disk_observer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class Starter extends Application {
    private Stage primaryStage;
    private Map<String, Long> sizes;
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private PieChart pieChart;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Disk analyzer");

        Button btnChooseDir = new Button("Choose directory");

        StackPane pane = new StackPane();
        pane.getChildren().add(btnChooseDir);
        primaryStage.setScene(new Scene(pane, 300, 250));
        primaryStage.show();

        btnChooseDir.setOnAction(event -> {
            File file = new DirectoryChooser().showDialog(primaryStage);
            //String
            Path path = Paths.get(file.getAbsolutePath());

            sizes = new Analyzer().calculateDirectorySize(path);

            buildChart(path);
        });
    }

    //String
    private void buildChart(Path path) {
        pieChart = new PieChart(pieChartData);

        refillChart(path);

        Button btnBack = new Button(path.toString());
        btnBack.setOnAction(event -> refillChart(path));

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(btnBack);
        borderPane.setCenter(pieChart);

        primaryStage.setScene(new Scene(borderPane, 800, 600));
        primaryStage.show();
    }

    private void refillChart(Path path) {
        pieChartData.clear();

        pieChartData.addAll(
                sizes
                .entrySet()
                .parallelStream()
                .filter(entry -> {
                    Path parent = Paths.get(entry.getKey()).getParent();
                    return parent != null && parent.toString().equals(path.toString());
                })
                .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList())
        );

        pieChart.getData().forEach(data -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                refillChart(Paths.get(data.getName()));
            });
        });
    }
}
