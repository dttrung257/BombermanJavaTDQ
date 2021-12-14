package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.enemies.Enemy;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;


public class Balloom extends Enemy {
    public static final int SPEED = 1;

    public Balloom(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        super.update();
        randomizeDirection();
        handleMove();
        handleAnimation(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3,
                Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3);
        //stand(Sprite.balloom_right1, Sprite.balloom_right1, Sprite.balloom_left1, Sprite.balloom_right1);
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((goUp && distance.getX() == 0 && canMove(0, -1)) || distance.getY() < 0) {
            y = -SPEED;
            if (distance.getY() >= 0) {
                distance.setY(distance.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((goDown && distance.getX() == 0 && canMove(0, 1)) || distance.getY() > 0) {
            y = SPEED;
            if (distance.getY() <= 0) {
                distance.setY(distance.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((goLeft && distance.getY() == 0 && canMove(-1, 0)) || distance.getX() < 0) {
            x = -SPEED;
            if (distance.getX() >= 0) {
                distance.setX(distance.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((goRight && distance.getY() == 0 && canMove(1, 0)) || distance.getX() > 0) {
            x = SPEED;
            if (distance.getX() <= 0) {
                distance.setX(distance.getX() + Sprite.SCALED_SIZE);
            }
        }
        if (distance.getX() != 0 || distance.getY() != 0) {
            move(x * SPEED, y * SPEED);
            distance.setX(distance.getX() - x * SPEED);
            distance.setY(distance.getY() - y * SPEED);
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
        roar.play();
        img = Sprite.balloom_dead.getFxImage();
        dying++;
    }
}
