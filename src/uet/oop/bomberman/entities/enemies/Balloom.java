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
    
    private boolean goUp, goDown, goRight, goLeft;

    public Balloom(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        setDirection();
        handleMove();
        handleAnimation(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3,
                Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3);
        //stand(Sprite.balloom_right1, Sprite.balloom_right1, Sprite.balloom_left1, Sprite.balloom_right1);
    }

    public void setDirection() {
        Random r = new Random();
        int dir = r.nextInt(12);
        switch (dir) {
            case 1:
                goUp = true;
                goDown = false;
                goLeft = false;
                goRight = false;
                break;
            case 2:
                goDown = true;
                goUp = false;
                goLeft = false;
                goRight = false;
                break;
            case 3:
                goRight = true;
                goDown = false;
                goLeft = false;
                goUp = false;
                break;
            case 4:
                goLeft = true;
                goDown = false;
                goUp = false;
                goRight = false;
                break;
        }
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((goUp && typeMove.getX() == 0 && canMove(0, -1)) || typeMove.getY() < 0) {
            y = -SPEED;
            if (typeMove.getY() >= 0) {
                typeMove.setY(typeMove.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((goDown && typeMove.getX() == 0 && canMove(0, 1)) || typeMove.getY() > 0) {
            y = SPEED;
            if (typeMove.getY() <= 0) {
                typeMove.setY(typeMove.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((goLeft && typeMove.getY() == 0 && canMove(-1, 0)) || typeMove.getX() < 0) {
            x = -SPEED;
            if (typeMove.getX() >= 0) {
                typeMove.setX(typeMove.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((goRight && typeMove.getY() == 0 && canMove(1, 0)) || typeMove.getX() > 0) {
            x = SPEED;
            if (typeMove.getX() <= 0) {
                typeMove.setX(typeMove.getX() + Sprite.SCALED_SIZE);
            }
        }
        if (typeMove.getX() != 0 || typeMove.getY() != 0) {
            move(x * SPEED, y * SPEED);
            typeMove.setX(typeMove.getX() - x * SPEED);
            typeMove.setY(typeMove.getY() - y * SPEED);
            isMoving = true;
        } else {
            if (goUp) {
                direction = AnimatedEntity.Direction.up;
            }
            if (goDown) {
                direction = AnimatedEntity.Direction.down;
            }
            if (goLeft) {
                direction = AnimatedEntity.Direction.left;
            }
            if (goRight) {
                direction = AnimatedEntity.Direction.right;
            }
            isMoving = false;
        }
    }
}
