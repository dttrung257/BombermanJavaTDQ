package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Oneal extends Enemy {
    public Oneal(Point coordinate, Image img) {
        super(coordinate, img);
    }

    private int SPEED;

    @Override
    public void update() {
        setDirection();
        handleMove();
        handleAnimation(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3,
                Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3);
        //stand(Sprite.oneal_right1, Sprite.oneal_right1, Sprite.oneal_left1, Sprite.oneal_right1);
    }

    public void setDirection() {
        int x = coordinate.getX();
        int y = coordinate.getY();
        int bx = GamePlay.getBomberman().getCoordinate().getX();
        int by = GamePlay.getBomberman().getCoordinate().getY();
        Random r = new Random();
        this.SPEED = r.nextInt(2) + 1;
        int dir = r.nextInt(12);
        switch (dir) {
            case 1:
            case 2:
                if (y > by) {
                    setGoUp();
                } else if (y < by) {
                    setGoDown();
                }
                break;
            case 4:
            case 5:
                if (x > bx) {
                    setGoLeft();
                } else if (x < bx) {
                    setGoRight();
                }
                break;
            case 7:
                setGoRight();
                break;
            case 8:
                setGoLeft();
                break;
            case 9:
                setGoUp();
                break;
            case 0:
                setGoDown();
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
