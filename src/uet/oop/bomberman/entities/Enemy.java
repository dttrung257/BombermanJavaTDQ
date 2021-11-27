package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Animation {
    public Enemy(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update(int newX, int newY, Image newImage) {
        x = newX;
        y = newY;
        img = newImage;
    }

    /**
     * Chỉnh vị trí và sprite khi di chuyển
     */
    @Override
    public void moving() {
        switch(direction) {
            case goUp:
                update(x, y - speed, Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, animate, 16).getFxImage());
                break;
            case goDown:
                update(x, y + speed, Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, animate, 16).getFxImage());
                break;
            case goLeft:
                update(x - speed, y, Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left3, animate, 16).getFxImage());
                break;
            case goRight:
                update(x + speed, y, Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right3, animate, 16).getFxImage());
                break;
        }
        speed = 2;
    }

    //Nếu nhả nút mũi tên thì đứng lại
    public void stand() {
        switch(direction) {
            case goUp:
                update(x, y, Sprite.player_up.getFxImage());
                break;
            case goDown:
                update(x, y, Sprite.player_down.getFxImage());
                break;
            case goLeft:
                update(x, y, Sprite.player_left.getFxImage());
                break;
            case goRight:
                update(x, y, Sprite.player_right.getFxImage());
                break;
        }
    }
}
