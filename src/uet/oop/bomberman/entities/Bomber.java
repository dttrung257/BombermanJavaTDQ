package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.staticEntity.Bomb;
import uet.oop.bomberman.entities.staticEntity.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.createBomb;

public class Bomber extends AnimatedEntity {
    public Bomber(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        createBomb();
        handleMove();
        checkEnemies();
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
        if ((BombermanGame.goDown && typeMove.getX() == 0 && canMove(0, 1)) || typeMove.getY() > 0) {
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
        if ((BombermanGame.goRight && typeMove.getY() == 0 && canMove(1, 0)) || typeMove.getX() > 0) {
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

    public void checkEnemies() {
        for (Enemy e : GamePlay.getEnemies()) {
            if (e.coordinate.getX() == this.coordinate.getX()
                    && e.coordinate.getY() == this.coordinate.getY()) {
                //animation(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3);
                System.exit(0);
                alive = false;
                return;
            }
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

    public void createBomb() {
        if(BombermanGame.createBomb) {
            Bomb bomb = new Bomb(coordinate, Sprite.bomb_2.getFxImage(), System.currentTimeMillis());
            GamePlay.bombs.add(bomb);
            BombermanGame.createBomb = false;
        }
    }
    public Image getImg() {
        return img;
    }
}
