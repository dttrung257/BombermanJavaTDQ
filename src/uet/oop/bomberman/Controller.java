package uet.oop.bomberman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Media click = new Media(new File("res/audios/click1.mp3").toURI().toString());
    private MediaPlayer pusic = new MediaPlayer(new Media(new File("res/audios/menu.mp3").toURI().toString()));
    private double rate = 2.0;

    @FXML
    private AnchorPane To;

    @FXML
    private Pane Menu, Settings, Guide;

    @FXML
    private Slider music, sound;

    @FXML
    private Label musicN, soundN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pusic.setVolume(0.05);
        pusic.setCycleCount(-1);
        pusic.play();
    }

    @FXML
    void PlayGame(MouseEvent e) {
        MediaPlayer music = new MediaPlayer(new Media(new File("res/medias/retro.mp4").toURI().toString()));
        MediaView viewer = new MediaView(music);
        viewer.setPreserveRatio(true);
        To.getChildren().add(viewer);
        pusic.stop();
        music.play();
        viewer.setOnMouseClicked(event -> {
            music.setRate(rate++);
        });
        music.setOnEndOfMedia(() -> {
            viewer.setVisible(false);
            MediaPlayer level1 = new MediaPlayer(new Media(new File("res/medias/level1.mp4").toURI().toString()));
            MediaView level = new MediaView(level1);
            level.setPreserveRatio(true);
            To.getChildren().add(level);
            level1.play();
            rate = 2.0;
            level.setOnMouseClicked(event -> {
                level1.setRate(rate++);
            });
            level1.setOnEndOfMedia(() -> {
                GamePlay.play = true;
                GamePlay.first = true;
                level.setVisible(false);
            });
        });
    }

    @FXML
    void Guide(MouseEvent e) {
        (new MediaPlayer(click)).play();
        Menu.setVisible(false);
        Guide.setVisible(true);
    }

    @FXML
    void Settings(MouseEvent e) {
        (new MediaPlayer(click)).play();
        Menu.setVisible(false);
        Settings.setVisible(true);
    }

    @FXML
    void adjustMusic(MouseEvent e) {
        musicN.setText((int) music.getValue() + "");
    }

    @FXML
    void adjustSound(MouseEvent e) {
        soundN.setText((int) sound.getValue() + "");
    }

    @FXML
    void Ok(MouseEvent e) {
        (new MediaPlayer(click)).play();
        Menu.setVisible(true);
        Settings.setVisible(false);
        GamePlay.setVolume(music.getValue(), sound.getValue());
        pusic.setVolume(BombermanGame.musicD);
    }

    @FXML
    void Back(MouseEvent e) {
        (new MediaPlayer(click)).play();
        Menu.setVisible(true);
        Guide.setVisible(false);
    }

    @FXML
    void Quit(MouseEvent e) {
        (new MediaPlayer(click)).play();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Do you want to quit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
