package ua.od.zakhariya.fx.fxml_gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;

public class S4Controller extends SubControllerSuper {
    @FXML
    private MediaView mediaView;

    @FXML
    private Button btnPlay, btnPause, btnReset, btnOpen;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;

    @FXML
    void initialize() {

    }

    public void playMedia(ActionEvent event) {
        mediaPlayer.play();
    }

    public void pauseMedia(ActionEvent event) {
        mediaPlayer.pause();
    }

    public void resetMedia(ActionEvent event) {
        if (mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
            mediaPlayer.seek(Duration.seconds(0.0));
        }
    }

    public void chooseFile(ActionEvent event) {
        Window window = ((Button) event.getSource()).getScene().getWindow();
        file = new FileChooser().showOpenDialog(window);
//        file = new File("D:\\12.Stand.Up.S08.7turza™.avi");
//        file = new File("D:\\hero_gift.mp4");
//        file = new File("src/main/resources/s1e09 Josh Johnson.mkv");
//        file = new File("src/main/resources/MOV000щ01.3gp");

        if (file != null) {
            System.out.println(file.toString());

            media= new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }
}
