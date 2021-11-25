package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.graphics.Sprite.player_right;

public class GamePlay {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    private static Scene scene;
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Entity> MapInfor = new ArrayList<>();

    public GamePlay(Canvas canvas, GraphicsContext gc, Scene scene) {
        this.canvas = canvas;
        this.gc = gc;
        this.scene = scene;
    }

    public static void GameLoop() {
        Animation bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
        entities.add(bomberman);
        //Bắt sự kiện di chuyển bomber
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                scene.setOnKeyPressed(event -> {
                    bomberman.move_direction(event, MapInfor);
                    bomberman.moving();
                    scene.setOnKeyReleased(e -> {
                        bomberman.stand();
                    });
                });
            }
        };
        timer.start();
    }

    public static void createMap() {
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
                    MapInfor.add(object);
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

    public static void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}