package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.enemies.Enemy;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.staticEntity.Brick;
import uet.oop.bomberman.entities.staticEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;


public class Balloom extends Enemy {
    public static final int BALLOOM_SPEED = 1;

    public Balloom(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        super.update();
        if (!alive) {
            return;
        }
        randomizeDirection();
        handleMove();
        handleAnimation(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3,
                Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3);
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
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((goUp && distance.getX() == 0 && canMove(0, -1)) || distance.getY() < 0) {
            y = -BALLOOM_SPEED;
            if (distance.getY() >= 0) {
                distance.setY(distance.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((goDown && distance.getX() == 0 && canMove(0, 1)) || distance.getY() > 0) {
            y = BALLOOM_SPEED;
            if (distance.getY() <= 0) {
                distance.setY(distance.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((goLeft && distance.getY() == 0 && canMove(-1, 0)) || distance.getX() < 0) {
            x = -BALLOOM_SPEED;
            if (distance.getX() >= 0) {
                distance.setX(distance.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((goRight && distance.getY() == 0 && canMove(1, 0)) || distance.getX() > 0) {
            x = BALLOOM_SPEED;
            if (distance.getX() <= 0) {
                distance.setX(distance.getX() + Sprite.SCALED_SIZE);
            }
        }
        if (distance.getX() != 0 || distance.getY() != 0) {
            move(x, y);
            distance.setX(distance.getX() - x);
            distance.setY(distance.getY() - y);
            isMoving = true;
        } else {
            if (goUp) {
                direction = Direction.up;
            }
            if (goDown) {
                direction = Direction.down;
            }
            if (goLeft) {
                direction = Direction.left;
            }
            if (goRight) {
                direction = Direction.right;
            }
            isMoving = false;
        }
    }

    @Override
    public void handleCollision() {}

    @Override
    public void handleDieAnimation() {
        roar.setVolume(BombermanGame.soundD);
        roar.play();
        img = Sprite.balloom_dead.getFxImage();
    }
}
