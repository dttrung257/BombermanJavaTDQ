package uet.oop.bomberman;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.enemies.*;
import uet.oop.bomberman.entities.item.*;
import uet.oop.bomberman.entities.staticEntity.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class GamePlay {
    public static int WIDTH;
    public static int HEIGHT;
    public static Group root;
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
    public static boolean goUp, goDown, goRight, goLeft, createBomb, FirstStep;
    public static int gameLevel = 1;
    public static boolean paused = false;
    public static boolean play = false, first = false, end = false;
    public static int score = 0;
    private static double rate = 2.0;
    public static boolean autoPlay = false;
    private int autoStatus = 0;

    public GamePlay(Canvas canvas, GraphicsContext gc, Scene scene, Group root) {
        this.root = root;
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

    public boolean isSafe(int pointX, int pointY) {
        if (isFlameSegment(pointX, pointY)) {
            return false;
        }
        if (nearBomb(pointX, pointY)) {
            return false;
        }
        return true;
    }

    public boolean nearBomb(int pointX, int pointY) {
        Iterator<Entity> itr = bombs.iterator();
        Entity cur;
        while (itr.hasNext()) {
            cur = itr.next();
            if (Math.abs(cur.getCoordinate().getY() - pointY) + Math.abs(cur.getCoordinate().getX() - pointX) < 2) {
                return true;
            }
        }
        return false;
    }

    public boolean nearEnemy(int pointX, int pointY) {
        Iterator<Entity> itr = enemies.iterator();
        Entity cur;
        while (itr.hasNext()) {
            cur = itr.next();
            if (Math.abs(cur.getCoordinate().getY() - pointY) + Math.abs(cur.getCoordinate().getX() - pointX) < 2) {
                return true;
            }
        }
        return false;
    }

    public boolean protectedFromBomb(int pointX, int pointY) {
        Iterator<Entity> itr = bombs.iterator();
        Entity cur;
        while (itr.hasNext()) {
            cur = itr.next();
            if (Math.abs(cur.getCoordinate().getY() - pointY) == 1 && Math.abs(cur.getCoordinate().getX() - pointX) == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isFlameSegment(int pointX, int pointY) {
        Iterator<Flame> itr = flames.iterator();
        Entity cur;
        while (itr.hasNext()) {
            cur = itr.next();
            if (Math.abs(cur.getCoordinate().getY() - pointY) + Math.abs(cur.getCoordinate().getX() - pointX) < 2) {
                return true;
            }
        }
        return false;
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
                        case '3':
                            Entity kondoria = new Kondoria(new Point(j, i), Sprite.kondoria_right1.getFxImage());
                            enemies.add(kondoria);
                            entities.add(kondoria);
                            break;
                        case '4':
                            Entity minvo = new Minvo(new Point(j, i), Sprite.minvo_right1.getFxImage());
                            enemies.add(minvo);
                            entities.add(minvo);
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
                        case 'l':
                            Entity lifeItem = new LifeItem(new Point(j, i), Sprite.powerup_detonator.getFxImage());
                            Entity brick4 = new Brick(new Point(j, i), Sprite.brick.getFxImage());
                            items.add(lifeItem);
                            bricks.add(brick4);
                            entities.add(lifeItem);
                            entities.add(brick4);
                            break;
                        default:
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
        if(end) {
            gameOver();
            end = false;
        }
        moveBomberman(scene);
        try {
            if (!paused) {
            if (bomberman != null) {
                bomberman.update();
                bricks.forEach(Entity::update);
                enemies.forEach(Entity::update);
                bombs.forEach(Entity::update);
                flames.forEach(Flame::update);
            }
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

    public void moveBomberman(Scene scene) {
        if (autoPlay) {
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case P:
                        paused = !paused;
                        break;
                    case K:
                        autoPlay = false;
                        autoStatus = 0;
                        goDown = false;
                        goUp = false;
                        goRight = false;
                        goLeft = false;
                        break;
                };
            });
            moveBomberman();
            return;
        }
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
                    paused = !paused;
                    break;
                case K:
                    autoPlay = true;
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

    public void moveBomberman() {
        if (GamePlay.getBomberman() == null) {
            return;
        }
        Point b = GamePlay.getBomberman().getCoordinate();
        createBomb = false;
        boolean ok = true;

        if (protectedFromBomb(b.getX(), b.getY()) || isFlameSegment(b.getX() - 1, b.getY()) || isFlameSegment(b.getX() + 1, b.getY())
                || isFlameSegment(b.getX(), b.getY() - 1) || isFlameSegment(b.getX(), b.getY() + 1)) {
            setGoDown();
            goDown = false;
            return;
        }

        switch (autoStatus) {
            case 1:
                setGoRight();
                autoStatus = 0;
                return;
            case 2:
                setGoUp();
                autoStatus = 0;
                return;
            case 3:
                setGoLeft();
                autoStatus = 0;
                return;
            case 4:
                setGoDown();
                autoStatus = 0;
                return;
        }

        if (getEntityAtPosition(b.getX(), b.getY()) instanceof Bomb) {
            if (bomberman.canMove(-1, 0) && !(getEntityAtPosition(b.getX() - 2, b.getY()) instanceof Enemy)) {
                setGoLeft();
                if (b.getX() > 1 && bomberman.canMove(-2, 0)) {
                    autoStatus = 3;
                    return;
                }
                if (bomberman.canMove(-1, -1)) {
                    autoStatus = 2;
                    return;
                }
                if (bomberman.canMove(-1, 1)) {
                    autoStatus = 4;
                    return;
                }
            }
            if (bomberman.canMove(1, 0) && !(getEntityAtPosition(b.getX() + 2, b.getY()) instanceof Enemy)) {
                setGoRight();
                if (bomberman.canMove(2, 0)) {
                    autoStatus = 1;
                    return;
                }
                if (bomberman.canMove(1, -1)) {
                    autoStatus = 2;
                    return;
                }
                if (bomberman.canMove(1, 1)) {
                    autoStatus = 4;
                    return;
                }
            }
            if (bomberman.canMove(0, -1) && !(getEntityAtPosition(b.getX(), b.getY() - 2) instanceof Enemy)) {
                setGoUp();
                if (b.getY() > 1 && bomberman.canMove(0, -2)) {
                    autoStatus = 2;
                    return;
                }
                if (bomberman.canMove(1, -1)) {
                    autoStatus = 1;
                    return;
                }
                if (bomberman.canMove(-1, -1)) {
                    autoStatus = 3;
                    return;
                }
            }
            if (bomberman.canMove(0, 1) && !(getEntityAtPosition(b.getX(), b.getY() + 2) instanceof Enemy)) {
                setGoDown();
                if (bomberman.canMove(0, 2)) {
                    autoStatus = 4;
                    return;
                }
                if (bomberman.canMove(1, 1)) {
                    autoStatus = 1;
                    return;
                }
                if (bomberman.canMove(-1, 1)) {
                    autoStatus = 3;
                    return;
                }
            }
            ok = true;
        }

        if (!isSafe(b.getX() + 1, b.getY())) {
            goRight = false;
            ok = false;
        } else if (!isSafe(b.getX() - 1, b.getY())) {
            goLeft = false;
            ok = false;
        } else if (!isSafe(b.getX(), b.getY() + 1)) {
            goDown = false;
            ok = false;
        } else if (!isSafe(b.getX(), b.getY() - 1)) {
            goUp = false;
            ok = false;
        }
        if (!ok) {
            return;
        } else {
            ok = false;
        }
        for (Entity en : GamePlay.getEnemies()) {
            Enemy e = (Enemy) en;
            if (b.getY() - e.getCoordinate().getY() < 3 && b.getY() - e.getCoordinate().getY() > 0) {
                if (b.getX() - e.getCoordinate().getX() < 2 && b.getX() - e.getCoordinate().getX() > -2) {
                    if (bomberman.canMove(0, -2)) {
                        createBomb = true;
                        setGoDown();
                    }
                    ok = true;
                }
            } else if (b.getY() - e.getCoordinate().getY() > -3 && b.getY() - e.getCoordinate().getY() < 0) {
                if (b.getX() - e.getCoordinate().getX() < 2 && b.getX() - e.getCoordinate().getX() > -2) {
                    if (bomberman.canMove(0, 2)) {
                        createBomb = true;
                        setGoUp();
                    }
                    ok = true;
                }
            } else if (b.getX() - e.getCoordinate().getX() < 3 && b.getX() - e.getCoordinate().getX() > 0) {
                if (b.getY() - e.getCoordinate().getY() < 2 && b.getY() - e.getCoordinate().getY() > -2) {
                    if (bomberman.canMove(2, 0)) {
                        createBomb = true;
                        setGoRight();
                    }
                    ok = true;
                }
            } else if (b.getX() - e.getCoordinate().getX() > -3 && b.getX() - e.getCoordinate().getX() < 0) {
                if (b.getY() - e.getCoordinate().getY() < 3 && b.getY() - e.getCoordinate().getY() > -3) {
                    if (bomberman.canMove(-2, 0)) {
                        createBomb = true;
                        setGoLeft();
                    }
                    ok = true;
                }
            }
        }
        if (!ok) {
            if (enemies.size() > 0) {
                Point e0 = enemies.get(0).getCoordinate();
                autoMoveTo(e0);
            } else {
                Point pt = portals.get(0).getCoordinate();
                autoMoveTo(pt);
            }
        }
    }

    public void autoMoveTo(Point pt) {
        Random r = new Random();
        int i = r.nextInt(2);
        Point b = bomberman.getCoordinate();
        if (i == 0) {
            goUp = false;
            goDown = false;
            if (pt.getX() > b.getX()) {
                createBomb = false;
                if (getEntityAtPosition(b.getX() + 3, b.getY()) instanceof Bomb) {
                    goLeft = false;
                    goRight = false;
                } else if (getEntityAtPosition(b.getX() - 3, b.getY()) instanceof Bomb) {
                    goLeft = false;
                    goRight = false;
                } else if (!bomberman.canMove(1, 0) || getEntityAtPosition(b.getX() + 1, b.getY()) instanceof Bomb
                        || getEntityAtPosition(b.getX() + 2, b.getY()) instanceof Bomb) {
                    if (getEntityAtPosition(b.getX() + 1, b.getY()) instanceof Brick) {
                        createBomb = true;
                    }
                    goRight = false;
                    goLeft = true;
                } else {
                    createBomb = false;
                    goLeft = false;
                    goRight = true;
                }
            } else if (pt.getX() < b.getX()) {
                createBomb = false;
                if (getEntityAtPosition(b.getX() - 3, b.getY()) instanceof Bomb) {
                    goLeft = false;
                    goRight = false;
                } else if (!bomberman.canMove(-1, 0) || getEntityAtPosition(b.getX() - 1, b.getY()) instanceof Bomb
                        || getEntityAtPosition(b.getX() - 2, b.getY()) instanceof Bomb) {
                    if (getEntityAtPosition(b.getX() - 1, b.getY()) instanceof Brick) {
                        createBomb = true;
                    }
                    goLeft = false;
                    goRight = true;
                } else {
                    createBomb = false;
                    goRight = false;
                    goLeft = true;
                }
            }
            //createBomb = false;
        } else {
            goLeft = false;
            goRight = false;
            if (pt.getY() > b.getY()) {
                createBomb = false;
                if (getEntityAtPosition(b.getX(), 3 + b.getY()) instanceof Bomb) {
                    goUp = false;
                    goDown = false;
                } else if (!bomberman.canMove(0, 1) || getEntityAtPosition(b.getX(), 1 + b.getY()) instanceof Bomb
                        || getEntityAtPosition(b.getX(), 2 + b.getY()) instanceof Bomb) {
                    if (getEntityAtPosition(b.getX(), b.getY() + 1) instanceof Brick) {
                        createBomb = true;
                    }
                    goDown = false;
                    goUp = true;
                } else {
                    createBomb = false;
                    goUp = false;
                    goDown = true;
                }
            } else if (pt.getY() < b.getY()) {
                createBomb = false;
                if (getEntityAtPosition(b.getX(), b.getY() - 3) instanceof Bomb) {
                    goUp = false;
                    goDown = false;
                } else if (!bomberman.canMove(0, -1) || getEntityAtPosition(b.getX(), b.getY() - 1) instanceof Bomb
                        || getEntityAtPosition(b.getX(), b.getY() - 2) instanceof Bomb) {
                    if (getEntityAtPosition(b.getX(), b.getY() - 1) instanceof Brick) {
                        createBomb = true;
                    }
                    goUp = false;
                    goDown = true;
                } else {
                    createBomb = false;
                    goDown = false;
                    goUp = true;
                }
            }
        }
    }

    public void setGoRight() {
        goRight = true;
        goDown = false;
        goUp = false;
        goLeft = false;
    }

    public void setGoLeft() {
        goRight = false;
        goDown = false;
        goUp = false;
        goLeft = true;
    }

    public static void setGoUp() {
        goRight = false;
        goDown = false;
        goUp = true;
        goLeft = false;
    }

    public static void setGoDown() {
        goRight = false;
        goDown = true;
        goUp = false;
        goLeft = false;
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

    public static void nextLevel() {
        MediaPlayer music = new MediaPlayer(new Media(new File("res/medias/level" + gameLevel + ".mp4").toURI().toString()));
        MediaView viewer = new MediaView(music);
        viewer.setPreserveRatio(true);
        root.getChildren().add(viewer);
        music.setVolume(BombermanGame.musicD);
        music.play();
        viewer.setOnMouseClicked(event -> {
            music.setRate(rate++);
        });
        music.setOnEndOfMedia(() -> {
            //GamePlay.play = true;
            viewer.setVisible(false);
        });

    }

    public static void gameOver() {
        MediaPlayer music = new MediaPlayer(new Media(new File("res/medias/gameover.mp4").toURI().toString()));
        MediaView viewer = new MediaView(music);
        viewer.setPreserveRatio(true);
        viewer.setOnMouseClicked(event -> {
            music.setRate(rate++);
        });
        root.getChildren().add(viewer);
        music.setVolume(BombermanGame.musicD);
        music.play();
        music.setOnEndOfMedia(()->{
            System.exit(0);
        });
    }

    public static void endgame() {
        MediaPlayer music = new MediaPlayer(new Media(new File("res/medias/end.mp4").toURI().toString()));
        MediaView viewer = new MediaView(music);
        viewer.setPreserveRatio(true);
        viewer.setOnMouseClicked(event -> {
            music.setRate(rate++);
        });
        root.getChildren().add(viewer);
        music.setVolume(BombermanGame.musicD);
        music.play();
        music.setOnEndOfMedia(()->{
            System.exit(0);
        });
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

    public static void setVolume(double music, double sound) {
        BombermanGame.musicD = music/200;
        BombermanGame.soundD = sound/100;
    }
}
