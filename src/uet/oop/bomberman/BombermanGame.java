package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.GameText;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;
import static uet.oop.bomberman.GamePlay.gameLevel;

public class BombermanGame extends Application {
    public static Stage stage = new Stage();
    public static final int WIDTH = 31;
    public static final int HEIGHT = 16;
    public static double musicD = 0.2;
    public static double soundD = 1;
    private GraphicsContext gc;
    private Canvas canvas;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        GameText texts = new GameText();
        // Tao root container
        Group root = new Group(texts.get(0), texts.get(1), texts.get(2),
                texts.get(3), texts.get(4), texts.get(5), texts.get(6));
        root.getChildren().add(canvas);
        Parent rot = FXMLLoader.load(getClass().getClassLoader().getResource("menuGame.fxml"));
        Scene menu = new Scene(rot);
        // Tao scene
        Scene scene = new Scene(root);
        scene.setFill(Color.LIGHTBLUE);
        // Them scene vao stage
        stage.getIcons().add(new Image("icon.png"));
        stage.setTitle("TDQ Bomberman");
        stage.setResizable(false);
        // Them scene vao stage
        stage.setScene(menu);
        stage.show();
        GamePlay newGame = new GamePlay(canvas, gc, scene, root);
        MediaPlayer music = new MediaPlayer(new Media(new File("res/audios/backgroundSound.mp3").toURI().toString()));

        AnimationTimer Timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (GamePlay.play) {
                    if(GamePlay.first) {
                        GamePlay.first = false;
                        stage.setScene(scene);
                        stage.show();
                        music.setCycleCount(-1);
                        music.setVolume(musicD);
                    }
                    if(!GamePlay.paused) {
                        music.play();
                    } else {
                        music.pause();
                    }
                    newGame.render();
                    newGame.update();
                    texts.update();
                }
            }
        };
        Timer.start();
        gameLevel = 7;
        newGame.createMap(gameLevel);
    }
}