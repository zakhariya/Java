package ua.od.zakhariya.fx.fxml_gui.sub_views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/*
Modern JavaFX documentation generally recommends using the no-argument initialize() method
over implementing Initializable when you don't specifically need the location and resources
parameters. This simplifies the code and avoids unnecessary interface implementation.
*/
public class S2Controller extends SubControllerSuper implements Initializable {

    @FXML
    private Label lblSliderData;

    @FXML
    private Slider slider;

    @FXML
    private ProgressIndicator progIndicator;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button btnGo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lblSliderData.setText(String.valueOf(Math.round(slider.getValue())));
            }
        });
    }

    public void increaseProgress() {
        progressBar.setProgress(0);

        new Thread(() -> {
            double p = 0.0;

            BigDecimal progress = new BigDecimal(0.0);

            while (progress.doubleValue() < 1) {
                p += 0.1;

                //important to update link
                progress = progress.add(BigDecimal.valueOf(0.025));

                progressBar.setProgress(progress.doubleValue());
                progIndicator.setProgress(progress.doubleValue());

                System.out.println(p + " " + (double) Math.round(p * 10)/10 + " " + progress.doubleValue());

                try {
                    Thread.sleep(125);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
