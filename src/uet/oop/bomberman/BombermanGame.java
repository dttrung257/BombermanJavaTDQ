package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    private GraphicsContext gc;
    private Canvas canvas;

    public static boolean goUp, goDown, goRight, goLeft, createBomb = false;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        GamePlay newGame = new GamePlay(canvas, gc, scene);
        AnimationTimer Timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                newGame.render();
                newGame.update();
            }
        };
        Timer.start();
        newGame.createMap(7);
        AnimatedEntity bomberman = new Bomber(new Point(1, 1), Sprite.player_right.getFxImage());
        newGame.getEntities().add(bomberman);
        newGame.setBomberman((Bomber) bomberman);
        moveBomberman(scene);
        //GamePlay.moveEnemies();
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
                    createBomb = true;
                    break;
            };
        });
    }
}