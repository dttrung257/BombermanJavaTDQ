package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends AnimatedEntity {

    public Bomber(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        handleMove();
        handleAnimation(Sprite.player_up_1, Sprite.player_up_2,
                        Sprite.player_down_1, Sprite.player_down_2,
                        Sprite.player_left_1, Sprite.player_left_2,
                        Sprite.player_right_1, Sprite.player_right_2);
        stand(Sprite.player_up, Sprite.player_down, Sprite.player_left, Sprite.player_right);
    }

    @Override
    public void handleMove() {
        int x = 0;
        int y = 0;
        if ((BombermanGame.goUp && typeMove.getX() == 0 && canMove(0, -1)) || typeMove.getY() < 0) {
            y = -Sprite.BOMBER_SPEED;
            if (typeMove.getY() >= 0) {
                typeMove.setY(typeMove.getY() - Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goDown &&typeMove.getX() == 0 && canMove(0, 1)) || typeMove.getY() > 0) {
            y = Sprite.BOMBER_SPEED;
            if (typeMove.getY() <= 0) {
                typeMove.setY(typeMove.getY() + Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goLeft && typeMove.getY() == 0 && canMove(-1, 0)) || typeMove.getX() < 0) {
            x = -Sprite.BOMBER_SPEED;
            if (typeMove.getX() >= 0) {
                typeMove.setX(typeMove.getX() - Sprite.SCALED_SIZE);
            }
        }
        if ((BombermanGame.goRight && typeMove.getY() == 0 && canMove(1, 0))|| typeMove.getX() > 0) {
            x = Sprite.BOMBER_SPEED;
            if (typeMove.getX() <= 0) {
                typeMove.setX(typeMove.getX() + Sprite.SCALED_SIZE);
            }
        }
        if (typeMove.getX() != 0 || typeMove.getY() != 0) {
            move(x * Sprite.BOMBER_SPEED, y * Sprite.BOMBER_SPEED);
            typeMove.setX(typeMove.getX() - x * Sprite.BOMBER_SPEED);
            typeMove.setY(typeMove.getY() - y * Sprite.BOMBER_SPEED);
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
    public void move(int x, int y) {
        super.move(x, y);
    }

    @Override
    public boolean canMove(int x, int y) {
        return super.canMove(x, y);
    }
}
