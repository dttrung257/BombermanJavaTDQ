package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;


/**
 * Class animation là những entity hoạt hình
 */
public abstract class Animation extends Entity{
    int animate = 0;
    int speed = 2;
    //Phương hướng di chuyển
    enum Direction {
        goUp,
        goDown,
        goLeft,
        goRight
    }
    Direction direction = Direction.goDown;

    public Animation(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    //Check xem có di chuyển tới được không và chỉnh hướng
    public void move_direction(KeyEvent event, List<Entity> MapInfor) {
        switch (event.getCode()) {
            case UP:
                direction = Direction.goUp;
                if (!check_move(MapInfor, x, y - speed)) {
                    speed = 0;
                }
                break;
            case DOWN:
                direction = Direction.goDown;
                if (!check_move(MapInfor, x, y + speed)) {
                    speed = 0;
                }
                break;
            case RIGHT:
                direction = Direction.goRight;
                if (!check_move(MapInfor, x + speed, y)) {
                    speed = 0;
                }
                break;
            case LEFT:
                direction = Direction.goLeft;
                if (!check_move(MapInfor, x - speed, y)) {
                    speed = 0;
                }
                break;
        };
        //tăng chỉ số hoạt ảnh để thay đổi sprite
        if(animate == 16) animate = 0;
        else animate++;
    }

    //Kiểm tra 4 góc ở vị trí bước tới xem có trùng với vật nào không
    public boolean check_move(List<Entity> MapInfor, int x, int y) {
        int indexX_left = x / Sprite.SCALED_SIZE;
        int indexY_above = y / Sprite.SCALED_SIZE;
        int indexX_right = indexX_left;
        int indexY_below = indexY_above;
        if (x % Sprite.SCALED_SIZE != 0) {
            indexX_right = (x + Sprite.player_right.get_realWidth()*2) / Sprite.SCALED_SIZE;
        }
        if (y % Sprite.SCALED_SIZE != 0) {
            indexY_below = indexY_above++;
        }

        if (MapInfor.get(indexX_left + indexY_above * 31) instanceof Grass &&
                MapInfor.get(indexX_right + indexY_above * 31) instanceof Grass) {
            if (MapInfor.get(indexX_left + indexY_below * 31) instanceof Grass &&
                    MapInfor.get(indexX_right + indexY_below * 31) instanceof Grass) {
                return true;
            }
        }
        return false;
    }

    public abstract void moving();
    public abstract void stand();
}
