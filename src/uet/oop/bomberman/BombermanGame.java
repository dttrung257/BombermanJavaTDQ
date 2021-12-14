package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
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

import static java.lang.Thread.sleep;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 16;
    private GraphicsContext gc;
    private Canvas canvas;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        GameText texts = new GameText();
        // Tao root container
        Group root = new Group(texts.get(0), texts.get(1), texts.get(2),
                texts.get(3), texts.get(4), texts.get(5), texts.get(6));
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        scene.setFill(Color.LIGHTBLUE);
        // Them scene vao stage
        stage.getIcons().add(new Image("icon.png"));
        stage.setTitle("TDQ Bomberman");
        stage.setResizable(false);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        GamePlay newGame = new GamePlay(canvas, gc, scene);
        MediaPlayer music = new MediaPlayer(new Media(new File("res/audios/backgroundSound.mp3").toURI().toString()));
        music.setCycleCount(-1);
        music.setVolume(0.2);
        AnimationTimer Timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(!GamePlay.paused) {
                    music.play();
                } else {
                    music.pause();
                }
                newGame.render();
                newGame.update();
                texts.update();
            }
        };
        Timer.start();
        newGame.createMap(1);
    }
}