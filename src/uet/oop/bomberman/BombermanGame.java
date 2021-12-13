package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.GameText;
import uet.oop.bomberman.graphics.Sprite;

import static java.lang.Thread.sleep;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 16;
    private GraphicsContext gc;
    private Canvas canvas;

    public static boolean goUp, goDown, goRight, goLeft, createBomb;

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
        stage.setScene(scene);
        stage.setTitle("Bomberman Game");
        stage.show();
        GamePlay newGame = new GamePlay(canvas, gc, scene);
        AnimationTimer Timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                newGame.render();
                newGame.update();
                texts.update();
            }
        };
        Timer.start();
        newGame.createMap(1);
        moveBomberman(scene);
    }

    public void moveBomberman(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    goUp = true;
                    break;
                case DOWN:
                    goDown = true;
                    break;
                case RIGHT:
                    goRight = true;
                    break;
                case LEFT:
                    goLeft = true;
                    break;
                case SPACE:
                    createBomb = true;
                    break;
                case P:
                    GamePlay.paused = !GamePlay.paused;
                    break;
            };
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    goUp = false;
                    break;
                case DOWN:
                    goDown = false;
                    break;
                case RIGHT:
                    goRight = false;
                    break;
                case LEFT:
                    goLeft = false;
                    break;
                case SPACE:
                    createBomb = false;
                    break;
            };
        });
    }
}