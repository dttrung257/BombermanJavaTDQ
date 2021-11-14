package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.event.Event;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    boolean goUp, goDown, goRight, goLeft;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


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

        AnimationTimer Timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                //update();
            }
        };
        Timer.start();
        createMap();
        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        moveBomberman(scene);
        AnimationTimer timer = new AnimationTimer() {
            int step = 4;
            @Override
            public void handle(long l) {
                int pointX = bomberman.getX();
                int pointY = bomberman.getY();

                if (goUp && pointY > 32) {
                    pointY -= step;
                    bomberman.update(pointX, pointY, Sprite.player_up.getFxImage());
                }
                if (goDown && pointY < 420) {
                    pointY += step;
                    bomberman.update(pointX, pointY, Sprite.player_down.getFxImage());
                }
                if (goLeft && pointX > 32) {
                    pointX -= step;
                    bomberman.update(pointX, pointY, Sprite.player_left.getFxImage());
                }
                if (goRight && pointX < 588) {
                    pointX += step;
                    bomberman.update(pointX, pointY, Sprite.player_right.getFxImage());
                }
            }
        };
        timer.start();
    }

    public void moveBomberman(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
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
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
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
                };
            }
        });
    }


    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    /*public void update() {
        entities.forEach(Entity::update);
    }*/

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
