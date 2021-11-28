package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Animation {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    /**
     * Chỉnh vị trí và sprite khi di chuyển
     */
    @Override
    public void moving() {
        switch(direction) {
            case goUp:
                update(x, y - speed, Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 16).getFxImage());
                break;
            case goDown:
                update(x, y + speed, Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 16).getFxImage());
                break;
            case goLeft:
                update(x - speed, y, Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 16).getFxImage());
                break;
            case goRight:
                update(x + speed, y, Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 16).getFxImage());
                break;
        }
        speed = 5;
    }

    //Nếu nhả nút mũi tên thì đứng lại
    public void stand() {
        if (x % Sprite.SCALED_SIZE <= 6) {
            x = x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE;
        } else if (x % Sprite.SCALED_SIZE >= 26) {
            x = (x / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE;
        }
        if (y % Sprite.SCALED_SIZE <= 6) {
            y = y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE;
        } else if (y % Sprite.SCALED_SIZE >= 26) {
            y = (y / Sprite.SCALED_SIZE + 1) * Sprite.SCALED_SIZE;
        }
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

    @Override
    public void update(int newX, int newY, Image newImage) {
        x = newX;
        y = newY;
        img = newImage;
    }
}
