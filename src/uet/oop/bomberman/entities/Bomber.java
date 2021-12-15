package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.HiddenItem;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.entities.staticEntity.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;

import static java.lang.Thread.sleep;
import static uet.oop.bomberman.GamePlay.getEntityAtPosition;
import static uet.oop.bomberman.entities.staticEntity.Bomb.setRangeOfFlame;

public class Bomber extends AnimatedEntity {
    private static Media putBom = new Media(new File("res/audios/createBomb.mp3").toURI().toString());
    private static Media eatItems = new Media(new File("res/audios/eatItems.mp3").toURI().toString());
    private MediaPlayer scream = new MediaPlayer(new Media(new File("res/audios/scream.mp3").toURI().toString()));

    public static int bomberLife = 3;
    protected static int bomb = 1;

    public Bomber(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        handleAnimation();
        if (!alive) {
            handleDieAnimation();
            if (animation == 60) {
                Bomber copy = this;
                GamePlay.removeBomber();
                if (bomberLife > 0) {
                    GamePlay.setBomber(copy);
                    alive = true;
                } else {
                    GamePlay.end = true;
                }
            }
            return;
        }
        if (bomberLife <= 0) {
            GamePlay.end = true;
            BombermanGame.stage.hide();
        }
        putBomb();
        handleMove();
        handleMoveAnimation();
        stand(Sprite.player_up, Sprite.player_down, Sprite.player_left, Sprite.player_right);
        handleCollision();
    }

    public void handleAnimation() {
        if (animation > 120) {
            animation = 0;
        } else {
            animation++;
        }
    }

    public void handleMoveAnimation() {
        displayAnimation(Sprite.player_up_1, Sprite.player_up_2,
                Sprite.player_down_1, Sprite.player_down_2,
                Sprite.player_left_1, Sprite.player_left_2,
                Sprite.player_right_1, Sprite.player_right_2);
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((GamePlay.goUp && distance.getX() == 0 && canMove(0, -1)) || distance.getY() < 0) {
            y = -bomber_speed;
            if (distance.getY() >= 0) {
                distance.setY(distance.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((GamePlay.goDown && distance.getX() == 0 && canMove(0, 1)) || distance.getY() > 0) {
            y = bomber_speed;
            if (distance.getY() <= 0) {
                distance.setY(distance.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((GamePlay.goLeft && distance.getY() == 0 && canMove(-1, 0)) || distance.getX() < 0) {
            x = -bomber_speed;
            if (distance.getX() >= 0) {
                distance.setX(distance.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((GamePlay.goRight && distance.getY() == 0 && canMove(1, 0)) || distance.getX() > 0) {
            x = bomber_speed;
            if (distance.getX() <= 0) {
                distance.setX(distance.getX() + Sprite.SCALED_SIZE);
            }
        }
        if (distance.getX() != 0 || distance.getY() != 0) {
            move(x * Sprite.SPEED, y * Sprite.SPEED);
            distance.setX(distance.getX() - x * Sprite.SPEED);
            distance.setY(distance.getY() - y * Sprite.SPEED);
            isMoving = true;
        } else {
            if (GamePlay.goUp) {
                direction = Direction.up;
            }
            if (GamePlay.goDown) {
                direction = Direction.down;
            }
            if (GamePlay.goLeft) {
                direction = Direction.left;
            }
            if (GamePlay.goRight) {
                direction = Direction.right;
            }
            isMoving = false;
        }
    }

    @Override
    public boolean canMove(int x, int y) {
        Entity checkEntity = GamePlay.getEntityAtPosition(coordinate.getX() + x, coordinate.getY() + y);

        if (!(checkEntity instanceof Wall) && !(checkEntity instanceof Brick)) {
            return true;
        }
        return false;
    }

    @Override
    public void handleDieAnimation() {
        img = Sprite.movingSprite(Sprite.player_dead1,
                                    Sprite.player_dead2,
                                    Sprite.player_dead3,
                                    animation, 20).getFxImage();
    }

    @Override
    public void handleCollision() {
        Entity e = getEntityAtPosition(coordinate.getX(), coordinate.getY());
        if (e instanceof Portal && GamePlay.getEnemies().size() == 0) {
            if (GamePlay.gameLevel < 7) {
                GamePlay.createMap(++GamePlay.gameLevel);
                GamePlay.nextLevel();
                resetBomberAbilityWhenPassLevel();
            } else if (GamePlay.gameLevel == 7) {
                GamePlay.removeBomber();
                GamePlay.endgame();
            }
        }
        if (e instanceof HiddenItem) {
            MediaPlayer put = new MediaPlayer(eatItems);
            put.setVolume(BombermanGame.soundD);
            put.play();
            ((HiddenItem) e).handleItem();
        }
        if (e instanceof Enemy) {
            die();
        }
    }

    protected void resetBomberAbilityWhenPassLevel() {
        setRangeOfFlame(1);
        bomber_speed = 1;
        bomb = 1;
    }

    protected void putBomb() {
        if (GamePlay.createBomb && bomb > 0
                && !(getEntityAtPosition(coordinate.getX(), coordinate.getY()) instanceof Bomb)) {
            GamePlay.setBomb(new Bomb(new Point(coordinate.getX(), coordinate.getY()), Sprite.bomb_2.getFxImage()));
            bomb--;
            MediaPlayer put = new MediaPlayer(putBom);
            put.setVolume(BombermanGame.soundD);
            put.play();
        }
        if (bomb == 0) GamePlay.createBomb = false;
    }

    @Override
    public void die() {
        scream.setVolume(BombermanGame.soundD);
        scream.play();
        if (alive) {
            bomberLife--;
            alive = false;
            animation = 0;
        }
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);
    }

    public static void addBomb() {
        bomb++;
    }

    public static void upLife() {
        bomberLife++;
    }

    public void setBomberSpeed(int speed) {
        this.bomber_speed = speed;
    }

    public Image getImg() {
        return img;
    }
}
