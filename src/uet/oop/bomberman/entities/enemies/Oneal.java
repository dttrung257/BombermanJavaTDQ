package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.staticEntity.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

public class Oneal extends Enemy {
    public Oneal(Point coordinate, Image img) {
        super(coordinate, img);
    }

    private int SPEED;

    private Point lastVisited = new Point(0,0);

    @Override
    public void update() {
        super.update();
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
        this.SPEED = r.nextInt(2);
        int [][]B = new int[13][31];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                B[i][j] = 0;
            }
        }
        Point b = GamePlay.getBomberman().getCoordinate();
        if (x < bx && y < by) {
            int dx[] = {1, 0, 0, -1};
            int dy[] = {0, 1, -1, 0};
            minPath(coordinate, b, B, dx, dy);
        } else if (x < bx) {
            int dx[] = {1, 0, 0, -1};
            int dy[] = {0, -1, 1, 0};
            minPath(coordinate, b, B, dx, dy);
        } else if (y >= by) {
            int dx[] = {-1, 0, 0, 1};
            int dy[] = {0, 1, -1, 0};
            minPath(coordinate, b, B, dx, dy);
        } else {
            int dx[] = {-1, 0, 0, 1};
            int dy[] = {0, -1, 1, 0};
            minPath(coordinate, b, B, dx, dy);
        }
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((goUp && distance.getX() == 0 && canMove(0, -1/*SPEED*/) && canMove(0, -SPEED)) || distance.getY() < 0) {
            y = -SPEED;
            if (distance.getY() >= 0) {
                distance.setY(distance.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((goDown && distance.getX() == 0 && canMove(0, 1/*SPEED*/) && canMove(0, SPEED)) || distance.getY() > 0) {
            y = SPEED;
            if (distance.getY() <= 0) {
                distance.setY(distance.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((goLeft && distance.getY() == 0 && canMove(-1/*SPEED*/, 0) && canMove(-SPEED, 0)) || distance.getX() < 0) {
            x = -SPEED;
            if (distance.getX() >= 0) {
                distance.setX(distance.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((goRight && distance.getY() == 0 && canMove(1/*SPEED*/, 0) && canMove(SPEED, 0)) || distance.getX() > 0) {
            x = SPEED;
            if (distance.getX() <= 0) {
                distance.setX(distance.getX() + Sprite.SCALED_SIZE);
            }
        }
        if (distance.getX() != 0 || distance.getY() != 0) {
            if (coordinate.getX() + x * SPEED != lastVisited.getX()
                || coordinate.getY() + y * SPEED != lastVisited.getY()) {
                lastVisited = coordinate;
                move(x * SPEED, y * SPEED);
            }
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

    public void minPath(Point o, Point b, int[][] B, int[] dx, int[] dy) {
        B[b.getY()][b.getX()] = 1;
        Point oUp = new Point(o.getX(), o.getY() - SPEED);
        Point oDown = new Point(o.getX(), o.getY() + SPEED);
        Point oLeft = new Point(o.getX() - SPEED, o.getY());
        Point oRight = new Point(o.getX() + SPEED, o.getY());
        Point p;
        if (b.equals(o)) return;
        if (b.equals(oUp)) {
            setGoUp();
            return;
        } else if (b.equals(oDown)) {
            setGoDown();
            return;
        } else if (b.equals(oLeft)) {
            setGoLeft();
            return;
        } else if (b.equals(oRight)) {
            setGoRight();
            return;
        }
        Queue<Point> q = new LinkedList<>();
        q.add(b);
        int num = 0;
        while (!q.isEmpty()) {
            p = q.poll();
            for (int i = 0; i < 4; i++) {
                num++;
                if (num > 60) {
                    randomizeDirection();
                    return;
                }
                Point tmp = new Point(p.getX() + dx[i], p.getY() + dy[i]);
                if (GamePlay.getEntityAtPosition(tmp.getX(), tmp.getY()) instanceof Grass
                        && B[tmp.getY()][tmp.getX()] == 0) {
                    if (tmp.equals(o)) return;
                    if (tmp.equals(oUp)) {
                        setGoUp();
                        return;
                    } else if (tmp.equals(oDown)) {
                        setGoDown();
                        return;
                    } else if (tmp.equals(oLeft)) {
                        setGoLeft();
                        return;
                    } else if (tmp.equals(oRight)) {
                        setGoRight();
                        return;
                    }
                    q.add(tmp);
                    B[tmp.getY()][tmp.getX()] = 1;
                }
            }
        }
        randomizeDirection();
    }

    @Override
    public void handleCollision() {

    }

    @Override
    public void handleDieAnimation() {
        img = Sprite.oneal_dead.getFxImage();
        dying++;
    }
}
