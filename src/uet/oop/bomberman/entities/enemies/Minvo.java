package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.staticEntity.Bomb;
import uet.oop.bomberman.entities.staticEntity.Brick;
import uet.oop.bomberman.entities.staticEntity.Flame;
import uet.oop.bomberman.entities.staticEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    private int MINVO_SPEED = 1;
    private Point beginCoordinate;

    public Minvo(Point coordinate, Image img) {
        super(coordinate, img);
        beginCoordinate = coordinate;
    }

    @Override
    public void update() {
        super.update();
        if (!alive) {
            return;
        }
        findBomberAI();
        handleMove();
        handleAnimation(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3,
                Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3);
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

    @Override
    public boolean canMove(int x, int y) {
        Entity checkEntity = GamePlay.getEntityAtPosition(coordinate.getX() + x, coordinate.getY() + y);
        int x1 = 0, x2 = 0, x3 = 0;
        int y1 = 0, y2 = 0, y3 = 0;
        if (x == 1) {
            x1 = x + 1;
            x2 = x + 2;
            x3 = x + 3;
        }
        if (x == -1) {
            x1 = x - 1;
            x2 = x - 2;
            x3 = x - 3;
        }
        if (y == 1) {
            y1 = y + 1;
            y2 = y + 2;
            y3 = y + 3;
        }
        if (y == -1) {
            y1 = y - 1;
            y2 = y - 2;
            y3 = y - 3;
        }
        Entity checkBomb1 = GamePlay.getEntityAtPosition(coordinate.getX() + x1, coordinate.getY() + y1);
        Entity checkBomb2 = GamePlay.getEntityAtPosition(coordinate.getX() + x2, coordinate.getY() + y2);
        Entity checkBomb3 = GamePlay.getEntityAtPosition(coordinate.getX() + x3, coordinate.getY() + y3);
        Entity checkFlame = GamePlay.getEntityAtPosition(coordinate.getX() + x1, coordinate.getY() + y1);
        Entity checkFlame1 = GamePlay.getEntityAtPosition(coordinate.getX() + x2, coordinate.getY() + y2);

        if (!(checkEntity instanceof Wall)
                && !(checkBomb1 instanceof Bomb)
                && !(checkBomb2 instanceof Bomb)
                && !(checkBomb3 instanceof Bomb)
                && !(checkFlame instanceof Flame)
                && !(checkFlame1 instanceof Flame)) {
            return true;
        }
        return false;
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((goUp && distance.getX() == 0 && canMove(0, -1)) || distance.getY() < 0) {
            y = -MINVO_SPEED;
            if (distance.getY() >= 0) {
                distance.setY(distance.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((goDown && distance.getX() == 0 && canMove(0, 1)) || distance.getY() > 0) {
            y = MINVO_SPEED;
            if (distance.getY() <= 0) {
                distance.setY(distance.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((goLeft && distance.getY() == 0 && canMove(-1, 0)) || distance.getX() < 0) {
            x = -MINVO_SPEED;
            if (distance.getX() >= 0) {
                distance.setX(distance.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((goRight && distance.getY() == 0 && canMove(1, 0)) || distance.getX() > 0) {
            x = MINVO_SPEED;
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
    public void handleCollision() {

    }

    @Override
    public void handleDieAnimation() {
        roar.setVolume(BombermanGame.soundD);
        roar.play();
        img = Sprite.minvo_dead.getFxImage();
    }
}

