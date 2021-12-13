package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.HiddenItem;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.entities.staticEntity.*;
import uet.oop.bomberman.graphics.Sprite;

import static java.lang.Thread.sleep;
import static uet.oop.bomberman.GamePlay.getEntityAtPosition;
import static uet.oop.bomberman.entities.staticEntity.Bomb.setRangeOfFlame;

public class Bomber extends AnimatedEntity {
    public static int bomberLife = 3;
    protected static int bomb = 1;
    protected static int length = 0;

    public Bomber(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        putBomb();
        handleMove();
        handleAnimation();
        stand(Sprite.player_up, Sprite.player_down, Sprite.player_left, Sprite.player_right);
        handleCollision();
        if (bomberLife <= 0) {
            //System.exit(0);
        }
    }

    public void handleAnimation() {
        displayAnimation(Sprite.player_up_1, Sprite.player_up_2,
                Sprite.player_down_1, Sprite.player_down_2,
                Sprite.player_left_1, Sprite.player_left_2,
                Sprite.player_right_1, Sprite.player_right_2);
        if (animation == 1600) {
            animation = 0;
        } else {
            animation++;
        }
        length--;
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((BombermanGame.goUp && distance.getX() == 0 && canMove(0, -1)) || distance.getY() < 0) {
            y = -speed;
            if (distance.getY() >= 0) {
                distance.setY(distance.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goDown && distance.getX() == 0 && canMove(0, 1)) || distance.getY() > 0) {
            y = speed;
            if (distance.getY() <= 0) {
                distance.setY(distance.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goLeft && distance.getY() == 0 && canMove(-1, 0)) || distance.getX() < 0) {
            x = -speed;
            if (distance.getX() >= 0) {
                distance.setX(distance.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goRight && distance.getY() == 0 && canMove(1, 0)) || distance.getX() > 0) {
            x = speed;
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
            if (BombermanGame.goUp) {
                direction = Direction.up;
            }
            if (BombermanGame.goDown) {
                direction = Direction.down;
            }
            if (BombermanGame.goLeft) {
                direction = Direction.left;
            }
            if (BombermanGame.goRight) {
                direction = Direction.right;
            }
            isMoving = false;
        }
    }

    @Override
    public void handleCollision() {
        Entity e = getEntityAtPosition(coordinate.getX(), coordinate.getY());
        if (e instanceof Portal) {
            if (GamePlay.gameLevel < 7) {
                GamePlay.createMap(++GamePlay.gameLevel);
                resetBomberAbilityWhenPassLevel();
            }
        }
        if (e instanceof HiddenItem) {
            ((HiddenItem) e).handleItem();
            GamePlay.removeItem(coordinate);
        }
        if (e instanceof Enemy) {
            die();
        }
    }

    protected void resetBomberAbilityWhenPassLevel() {
        setRangeOfFlame(1);
        speed = 1;
        bomb = 1;
    }

    protected void putBomb() {
        if (BombermanGame.createBomb && length < 0 && bomb > 0
                && !(getEntityAtPosition(coordinate.getX(), coordinate.getY()) instanceof Bomb)) {
            GamePlay.setBomb(new Bomb(new Point(coordinate.getX(), coordinate.getY()), Sprite.bomb_2.getFxImage()));
            bomb--;
            length = 10;
        }
    }

    @Override
    public void die() {
        if (alive) {
            alive = false;
            animation = 0;
        }
        /*try {
            sleep(1000);
        } catch (Exception e) {

        }*/
        Point p = new Point(Sprite.SCALED_SIZE, 2 * Sprite.SCALED_SIZE);
        if (canvas_coordinate.equals(p)) {
            setCanvas_coordinate(new Point(3 * Sprite.SCALED_SIZE, 3 * Sprite.SCALED_SIZE));
        } else {
            setCanvas_coordinate(p);
        }
        setCoordinate(canvas_coordinate.toCoordinate());
        bomberLife--;
        alive = true;
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);
    }

    @Override
    public boolean canMove(int x, int y) {
        return super.canMove(x, y);
    }

    public static void addBomb() {
        bomb++;
    }

    public static void UpSpeed() {
        speed++;
    }

    public Image getImg() {
        return img;
    }
}
