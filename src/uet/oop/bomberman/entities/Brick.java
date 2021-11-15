package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Brick extends Entity {
    //private Entity hidden;
    //public final char PORTAL = 'x';
    //public final char SPEED_ITEM = 2;
    //public final char FLAME_ITEM = 3;
    //public final char BOMB_ITEM = 4;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update(int newX, int newY, Image newImage) {
        x = newX;
        y = newY;
    }
}