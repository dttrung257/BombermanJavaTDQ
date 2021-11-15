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
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
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

                if (goUp) {
                    bomberman.update(pointX, pointY, Sprite.player_up.getFxImage());
                    if (pointY > Sprite.SCALED_SIZE) {
                        pointY -= step;
                        bomberman.update(pointX, pointY, Sprite.player_up.getFxImage());
                    }
                }
                if (goDown) {
                    bomberman.update(pointX, pointY, Sprite.player_down.getFxImage());
                    if (pointY < Sprite.SCALED_SIZE * (HEIGHT - 2)) {
                        pointY += step;
                        bomberman.update(pointX, pointY, Sprite.player_down.getFxImage());
                    }
                }
                if (goLeft) {
                    bomberman.update(pointX, pointY, Sprite.player_left.getFxImage());
                    if (pointX > Sprite.SCALED_SIZE) {
                        pointX -= step;
                        bomberman.update(pointX, pointY, Sprite.player_left.getFxImage());
                    }
                }
                if (goRight) {
                    bomberman.update(pointX, pointY, Sprite.player_right.getFxImage());
                    if (pointX < Sprite.SCALED_SIZE * (WIDTH - 1.625)) {
                        pointX += step;
                        bomberman.update(pointX, pointY, Sprite.player_right.getFxImage());
                    }
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
        try {
            FileInputStream fileInputStream = new FileInputStream("res\\levels\\Level1.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            int l = bufferedReader.read();

            String line = bufferedReader.readLine();
            for (int i = 0; i < HEIGHT; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < WIDTH; j++) {
                    Entity object;
                    Entity object1;
                    if (line.charAt(j) == '#') {
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                    } else if (line.charAt(j) == '*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                    } else if (line.charAt(j) == 'x') {
                        object1 = new Portal(j, i, Sprite.portal.getFxImage());
                        stillObjects.add(object1);
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                    } else if (line.charAt(j) == 'b') {
                        object1 = new BombItem(j, i, Sprite.bomb.getFxImage());
                        stillObjects.add(object1);
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                    } else if (line.charAt(j) == 'f') {
                        object1 = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        stillObjects.add(object1);
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                    } else if (line.charAt(j) == 's') {
                        object1 = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        stillObjects.add(object1);
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                    } else {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                    }
                    stillObjects.add(object);
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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