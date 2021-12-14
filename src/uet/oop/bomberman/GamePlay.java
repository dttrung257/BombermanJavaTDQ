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
import uet.oop.bomberman.entities.item.HiddenItem;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.entities.staticEntity.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePlay {
    public static int WIDTH;
    public static int HEIGHT;
    private static Scene scene;
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static Bomber bomberman;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Flame> flames = new ArrayList<>();
    private static List<Entity> bombs = new ArrayList<>();
    private static List<Entity> walls = new ArrayList<>();
    private static List<Entity> portals = new ArrayList<>();
    private static List<Entity> bricks = new ArrayList<>();
    private static List<Entity> items = new ArrayList<>();
    private static List<Entity> enemies = new ArrayList<>();
    private static List<Entity> grasses = new ArrayList<>();
    public static int gameLevel;
    public static boolean paused = false;
    public static int score = 0;

    public GamePlay(Canvas canvas, GraphicsContext gc, Scene scene) {
        this.canvas = canvas;
        this.gc = gc;
        this.scene = scene;
    }

    public static Entity getEntityAtPosition(int pointX, int pointY) {
        Entity entity = null;
        entity = checkByType(walls, pointX, pointY);
        if (entity != null) {
            return entity;
        }
        entity = checkByType(bricks, pointX, pointY);
        if (entity != null) {
            return entity;
        }

        entity = checkByType(bombs, pointX, pointY);
        if (entity != null) {
            return entity;
        }
        entity = checkByType(portals, pointX, pointY);
        if (entity != null) {
            return entity;
        }
        entity = checkByType(items, pointX, pointY);
        if (entity != null) {
            return entity;
        }
        entity = checkByType(enemies, pointX, pointY);
        if (entity != null) {
            return entity;
        }
        if (bomberman != null
                && bomberman.getCoordinate().getX() == pointX
                && bomberman.getCoordinate().getY() == pointY) {
            entity = bomberman;
        }
        return entity;
    }

    public static Entity checkByType(List<Entity> entities, int pointX, int pointY) {
        Iterator<Entity> itr = entities.iterator();
        Entity cur;
        Entity entity = null;
        while (itr.hasNext()) {
            cur = itr.next();
            if (cur.getCoordinate().getY() == pointY && cur.getCoordinate().getX() == pointX) {
                entity = cur;
            }
        }
        return entity;
    }

    public static void resetData() {
        entities = new ArrayList<>();
        flames = new ArrayList<>();
        bombs = new ArrayList<>();
        walls = new ArrayList<>();
        portals = new ArrayList<>();
        bricks = new ArrayList<>();
        items = new ArrayList<>();
        enemies = new ArrayList<>();
        grasses = new ArrayList<>();
    }

    public static void createMap(int level) {
        //bomberman = new Bomber(new Point(1, 1), Sprite.player_right.getFxImage());
        //entities.add(bomberman);
        resetData();
        try {
            FileInputStream fileInputStream = new FileInputStream("res\\levels\\Level" + level + ".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            int l = bufferedReader.read();
            String line = bufferedReader.readLine();
            String[] str = line.split(" ");
            HEIGHT = Integer.parseInt(str[1]);
            WIDTH = Integer.parseInt(str[2]);
            for (int i = 0; i < HEIGHT; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < WIDTH; j++) {
                    grasses.add(new Grass(new Point(j, i), Sprite.grass.getFxImage()));
                    entities.add(new Grass(new Point(j, i), Sprite.grass.getFxImage()));
                    char ch = line.charAt(j);
                    switch (ch) {
                        case 'p':
                            bomberman = new Bomber(new Point(j, i), Sprite.player_right.getFxImage());
                            entities.add(bomberman);
                            break;
                        case '#':
                            Entity wall = new Wall(new Point(j, i), Sprite.wall.getFxImage());
                            walls.add(wall);
                            entities.add(wall);
                            break;
                        case '1':
                            Entity balloom = new Balloom(new Point(j, i), Sprite.balloom_right1.getFxImage());
                            enemies.add(balloom);
                            entities.add(balloom);
                            break;
                        case '2':
                            Entity oneal =  new Oneal(new Point(j, i), Sprite.oneal_right1.getFxImage());
                            enemies.add(oneal);
                            entities.add(oneal);
                            break;
                        case '*':
                            Entity brick = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            bricks.add(brick);
                            entities.add(brick);
                            break;
                        case 'x':
                            Entity portal = new Portal(new Point(j, i), Sprite.portal.getFxImage());
                            Entity br = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            portals.add(portal);
                            bricks.add(br);
                            entities.add(portal);
                            entities.add(br);
                            break;
                        case 'b':
                            Entity bombItem = new BombItem(new Point(j, i), Sprite.powerup_bombs.getFxImage());
                            Entity brick1 = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            items.add(bombItem);
                            bricks.add(brick1);
                            entities.add(bombItem);
                            entities.add(brick1);
                            break;
                        case 'f':
                            Entity flameItem = new FlameItem(new Point(j, i), Sprite.powerup_flames.getFxImage());
                            Entity brick2 = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            items.add(flameItem);
                            bricks.add(brick2);
                            entities.add(flameItem);
                            entities.add(brick2);
                            break;
                        case 's':
                            Entity speedItem = new SpeedItem(new Point(j, i), Sprite.powerup_speed.getFxImage());
                            Entity brick3 = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            items.add(speedItem);
                            bricks.add(brick3);
                            entities.add(speedItem);
                            entities.add(brick3);
                            break;
                        default:
                    }
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            gameLevel = level;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update() {
        try {
            if (!paused) {
                if (bomberman != null) {
                    bomberman.update();
                }
                bricks.forEach(Entity::update);
                enemies.forEach(Entity::update);
                bombs.forEach(Entity::update);
                flames.forEach(Flame::update);
            }
        } catch (Exception e) {

        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(grass -> grass.render(gc));
        walls.forEach(wall -> wall.render(gc));
        portals.forEach(portal -> portal.render(gc));
        items.forEach(item -> item.render(gc));
        for (Flame flame : flames) {
            flame.get_flameSegments().forEach(flameSegment -> flameSegment.render(gc));
        }
        bricks.forEach(brick -> brick.render(gc));
        enemies.forEach(enemy -> enemy.render(gc));
        bombs.forEach(g -> g.render(gc));
        if (bomberman != null) {
            bomberman.render(gc);
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

    public static void setFlame(Flame flame) {
        flames.add(flame);
    }

    public static void removeFlame() {
        flames.remove(0);
    }

    public static void destroyBrick(Brick brick) {
        bricks.remove(brick);
    }

    public static void setBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public static Bomber getBomber() {
        return bomberman;
    }

    public static void removeBomber() {
        bomberman = null;
    }

    public static void setBomber(Bomber bomber) {
        GamePlay.bomberman = bomber;
    }

    public static void removeBomb() {
        bombs.remove(0);
        if (bomberman != null) {
            Bomber.addBomb();
        }
    }

    public static void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public static void removeItem(HiddenItem item) {
        items.remove(item);
    }

    public static List<Entity> getEnemies() {
        return enemies;
    }
}
