package ua.od.zakhariya.fx.fxml_gui.sub_views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/*
Modern JavaFX documentation generally recommends using the no-argument initialize() method
over implementing Initializable when you don't specifically need the location and resources
parameters. This simplifies the code and avoids unnecessary interface implementation.
*/
public class S2Controller extends SubControllerSuper implements Initializable {

    @FXML
    private Label lblSliderData, lblSong;

    @FXML
    private Slider slider, sliderVolume;

    @FXML
    private ProgressIndicator progIndicator;

    @FXML
    private ProgressBar progressBar, pbSong;

    @FXML
    private Button btnPlay, btnReset, btnPrevious, btnNext, btnOpen;

    @FXML
    private ToggleButton btnPause;

    @FXML
    private ComboBox<String> cbSpeed;

    private Media media;
    private MediaPlayer mediaPlayer;

    private File dir;
    private File[] files;

    private ArrayList<File> songs;

    private int songNumber;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};

    private Timer timer;
    private TimerTask task;
    private boolean running;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lblSliderData.setText(String.valueOf(Math.round(slider.getValue())));
            }
        });

        songs = new ArrayList<>();

        disableMediaControls(true);

        for (int i = 0; i < speeds.length; i++) {
            cbSpeed.getItems().add((speeds[i]) + "%");
        }

//        cbSpeed.setOnAction(this::changeSpeed);

        sliderVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(sliderVolume.getValue() * 0.01);
            }
        });


    }

    private void disableMediaControls(boolean b) {
        btnPlay.setDisable(b);
        btnPause.setDisable(b);
        btnReset.setDisable(b);
        btnPrevious.setDisable(b);
        btnNext.setDisable(b);
        sliderVolume.setDisable(b);
        cbSpeed.setDisable(b);
    }

    public void playMedia() {
        if (mediaPlayer != null) mediaPlayer.stop();

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        lblSong.setText(songs.get(songNumber).getName());

        mediaPlayer.setVolume(sliderVolume.getValue() * 0.01);
        mediaPlayer.play();
        beginTimer();
        btnPause.setSelected(false);
        changeSpeed(null);
    }

    public void pauseMedia() {
        if (running) {
            mediaPlayer.pause();
            cancelTimer();
        } else {
            mediaPlayer.play();
            beginTimer();
        }
    }

    public void resetMedia() {
        pbSong.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void previousMedia() {
        if (songNumber > 0) {
            songNumber--;
        } else {
            songNumber = songs.size() - 1;
        }

        playMedia();
    }

    public void nextMedia() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
        } else {
            songNumber = 0;
        }

        playMedia();
    }

    public void changeSpeed(ActionEvent event) {
        int idx = cbSpeed.getSelectionModel().getSelectedIndex();

        if (idx >= 0)
            mediaPlayer.setRate(speeds[idx] * 0.01);

//        mediaPlayer.setRate(Integer.parseInt(cbSpeed.getValue()) * 0.01); // remove "%"
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();

                pbSong.setProgress(current/end);

                if (current/end == 1) {
                    cancelTimer();
                }
            }
        };

        running = true;
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void chooseFolder(ActionEvent event) {
        Window window = ((Button) event.getSource()).getScene().getWindow();
        dir = new DirectoryChooser().showDialog(window);

        if (dir == null) return;

        files = dir.listFiles();
        disableMediaControls(true);

        if (files != null) {
            songs.clear();

            for (File file : files) {
                songs.add(file);
            }

            disableMediaControls(false);
            playMedia();
        }
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
