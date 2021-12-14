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

    public Bomber(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        if (!alive) {
            if (animation == 60) {
                GamePlay.removeBomber();
                if (bomberLife > 0) {
                    GamePlay.setBomber(new Bomber(new Point(1, 1), Sprite.player_right.getFxImage()));
                    alive = true;
                }
            }
        }
        if (bomberLife <= 0) {
            System.exit(0);
        }
        putBomb();
        handleMove();
        handleAnimation();
        stand(Sprite.player_up, Sprite.player_down, Sprite.player_left, Sprite.player_right);
        handleCollision();
        System.out.println(distance.getX() + " " + distance.getY());
    }

    public void handleAnimation() {
        displayAnimation(Sprite.player_up_1, Sprite.player_up_2,
                Sprite.player_down_1, Sprite.player_down_2,
                Sprite.player_left_1, Sprite.player_left_2,
                Sprite.player_right_1, Sprite.player_right_2);
        if (animation > 120) {
            animation = 0;
        } else {
            animation++;
        }
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((BombermanGame.goUp && distance.getX() == 0 && canMove(0, -1)) || distance.getY() < 0) {
            y = -bomber_speed;
            if (distance.getY() >= 0) {
                distance.setY(distance.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goDown && distance.getX() == 0 && canMove(0, 1)) || distance.getY() > 0) {
            y = bomber_speed;
            if (distance.getY() <= 0) {
                distance.setY(distance.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goLeft && distance.getY() == 0 && canMove(-1, 0)) || distance.getX() < 0) {
            x = -bomber_speed;
            if (distance.getX() >= 0) {
                distance.setX(distance.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goRight && distance.getY() == 0 && canMove(1, 0)) || distance.getX() > 0) {
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
        if (BombermanGame.createBomb && bomb > 0
                && !(getEntityAtPosition(coordinate.getX(), coordinate.getY()) instanceof Bomb)) {
            GamePlay.setBomb(new Bomb(new Point(coordinate.getX(), coordinate.getY()), Sprite.bomb_2.getFxImage()));
            bomb--;
        }
    }

    @Override
    public void die() {
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

    @Override
    public boolean canMove(int x, int y) {
        return super.canMove(x, y);
    }

    public static void addBomb() {
        bomb++;
    }

    public void setBomberSpeed(int speed) {
        this.bomber_speed = speed;
    }

    public Image getImg() {
        return img;
    }
}
