package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.staticEntity.Brick;
import uet.oop.bomberman.entities.staticEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
    private int KONDORIA_SPEED = 1;
    private Point beginCoordinate;
    private int chasingRange = 3;
    private boolean reachLimitOfRange;

    public Kondoria(Point coordinate, Image img) {
        super(coordinate, img);
        beginCoordinate = coordinate;
        reachLimitOfRange = false;
    }

    @Override
    public void update() {
        System.out.println(coordinate.getX() + " " + coordinate.getY());
        super.update();
        if (!alive) {
            return;
        }
        check();
        if (!reachLimitOfRange) {
            findBomberAI();
        } else {
            comeBack();
        }
        handleMove();
        handleAnimation(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3,
                Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3);
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((goUp && distance.getX() == 0 && canMove(0, -1)) || distance.getY() < 0) {
            y = -KONDORIA_SPEED;
            if (distance.getY() >= 0) {
                distance.setY(distance.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((goDown && distance.getX() == 0 && canMove(0, 1)) || distance.getY() > 0) {
            y = KONDORIA_SPEED;
            if (distance.getY() <= 0) {
                distance.setY(distance.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((goLeft && distance.getY() == 0 && canMove(-1, 0)) || distance.getX() < 0) {
            x = -KONDORIA_SPEED;
            if (distance.getX() >= 0) {
                distance.setX(distance.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((goRight && distance.getY() == 0 && canMove(1, 0)) || distance.getX() > 0) {
            x = KONDORIA_SPEED;
            if (distance.getX() <= 0) {
                distance.setX(distance.getX() + Sprite.SCALED_SIZE);
            }
        }
        if (distance.getX() != 0 || distance.getY() != 0) {
            move(x * KONDORIA_SPEED, y * KONDORIA_SPEED);
            distance.setX(distance.getX() - x * KONDORIA_SPEED);
            distance.setY(distance.getY() - y * KONDORIA_SPEED);
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

    public void findBomberAI() {
        int x = coordinate.getX();
        int y = coordinate.getY();
        int bomberX = GamePlay.getBomber().getCoordinate().getX();
        int bomberY = GamePlay.getBomber().getCoordinate().getY();
        if (x == bomberX && y > bomberY) {
            setGoUp();
        } else if (x == bomberX && y < bomberY) {
            setGoDown();
        } else if (y == bomberY && x > bomberX) {
            setGoLeft();
        } else if (y == bomberY && x < bomberX) {
            setGoRight();
        }
    }

    public void comeBack() {
        int x = coordinate.getX();
        int y = coordinate.getY();
        int beginX = beginCoordinate.getX();
        int beginY = beginCoordinate.getY();
        if (x == beginX && y > beginY) {
            setGoUp();
        } else if (x == beginX && y < beginY) {
            setGoDown();
        } else if (y == beginY && x > beginX) {
            setGoLeft();
        } else if (y == beginY && x < beginX) {
            setGoRight();
        }
    }

    public void check() {
        int pointX = coordinate.getX();
        int pointY = coordinate.getY();
        int beginX = beginCoordinate.getX();
        int beginY = beginCoordinate.getY();
        if (Math.abs(pointX - beginX) == chasingRange || Math.abs(pointY - beginY) == chasingRange) {
            reachLimitOfRange = true;
        }
        if (Math.abs(pointX - beginX) > chasingRange || Math.abs(pointY - beginY) > chasingRange) {
            reachLimitOfRange = false;
        }
        if (pointX == beginX && pointY == beginY) {
            reachLimitOfRange = false;
        }
    }

    @Override
    public boolean canMove(int x, int y) {
        Entity checkEntity = GamePlay.getEntityAtPosition(coordinate.getX() + x, coordinate.getY() + y);

        if (!(checkEntity instanceof Wall
                && (checkEntity.getCoordinate().getX() == 0
                    || checkEntity.getCoordinate().getY() == 0
                    || checkEntity.getCoordinate().getX() == GamePlay.WIDTH - 1
                    || checkEntity.getCoordinate().getY() == GamePlay.HEIGHT - 1))) {
            return true;
        }
        if (reachLimitOfRange) {
            return false;
        }
        /*if (!(checkEntity instanceof Wall)) {
            return true;
        }*/
        return false;
    }

    @Override
    public void handleCollision() {

    }

    @Override
    public void handleDieAnimation() {
        roar.play();
        img = Sprite.kondoria_dead.getFxImage();
    }
}
