package uet.oop.bomberman;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.enemies.Balloom;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.entities.staticEntity.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GamePlay {
    public static int WIDTH;
    public static int HEIGHT;
    private static Scene scene;
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static Bomber bomberman;
    private static List<Entity> entities = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();
    public static List<Flame> flames = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Enemy> enemies = new ArrayList<>();

    public GamePlay(Canvas canvas, GraphicsContext gc, Scene scene) {
        this.canvas = canvas;
        this.gc = gc;
        this.scene = scene;
    }

    public static Entity getEntityAtPosition(int pointX, int pointY) {
        Entity entity = null;
        for (Entity e : stillObjects) {;
            if (e.getCoordinate().getX() == pointX && e.getCoordinate().getY() == pointY) {
                entity = e;
                break;
            }
        }
        return entity;
    }

    public static void createMap(int level) {
        //bomberman = new Bomber(new Point(1, 1), Sprite.player_right.getFxImage());
        //entities.add(bomberman);
        try {
            FileInputStream fileInputStream = new FileInputStream("res\\levels\\Level" + level + ".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            int l = bufferedReader.read();
            String line = bufferedReader.readLine();
            String str[] = line.split(" ");
            HEIGHT = Integer.parseInt(str[1]);
            WIDTH = Integer.parseInt(str[2]);
            for (int i = 0; i < HEIGHT; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < WIDTH; j++) {
                    Entity object = null;
                    Entity object1 = null;
                    Entity object2 = null;
                    char ch = line.charAt(j);
                    switch (ch) {
                        case '#':
                            object = new Wall(new Point(j, i), Sprite.wall.getFxImage());
                            break;
                        case '1':
                            Balloom b = new Balloom(new Point(j, i), Sprite.balloom_right1.getFxImage());
                            enemies.add(b);
                            entities.add(b);
                            object2 = new Grass(new Point(j, i), Sprite.grass.getFxImage());
                            break;
                        case '2':
                            Oneal o = new Oneal(new Point(j, i), Sprite.oneal_right1.getFxImage());
                            enemies.add(o);
                            entities.add(o);
                            object2 = new Grass(new Point(j, i), Sprite.grass.getFxImage());
                            break;
                        case '*':
                            object = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            break;
                        case 'x':
                            object1 = new Portal(new Point(j, i), Sprite.portal.getFxImage());
                            object = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            break;
                        case 'b':
                            object1 = new BombItem(new Point(j, i), Sprite.bomb.getFxImage());
                            object = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            break;
                        case 'f':
                            object1 = new FlameItem(new Point(j, i), Sprite.powerup_flames.getFxImage());
                            object = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            break;
                        case 's':
                            object1 = new SpeedItem(new Point(j, i), Sprite.powerup_speed.getFxImage());
                            object = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            //object2 = new Grass(j, i, Sprite.grass.getFxImage());
                            break;
                        default:
                            object2 = new Grass(new Point(j, i), Sprite.grass.getFxImage());
                            break;
                    }
                    if (object1 != null) {
                        entities.add(object1);
                    }
                    if (object2 != null) {
                        stillObjects.add(object2);
                        entities.add(object2);
                    }
                    if (object != null) {
                        stillObjects.add(object);
                        entities.add(object);
                    }
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        enemies.forEach(Enemy::update);
        bombs.forEach(Bomb::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (bomberman.isAlive()) {
            stillObjects.forEach(g -> g.render(gc));
            entities.forEach(g -> g.render(gc));
            bombs.forEach(g -> g.render(gc));
            flames.forEach(g -> g.render(gc));
            enemies.forEach(g -> g.render(gc));
        }
    }

    public static List<Entity> getEntities() {
        return entities;
    }

    public static Bomber getBomberman() {
        return bomberman;
    }

    public static void setBomberman(Bomber bomberman) {
        GamePlay.bomberman = bomberman;
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }
}
