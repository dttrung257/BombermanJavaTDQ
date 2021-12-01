package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;

public class Brick extends Entity {
    //private Entity hidden;
    //public final char PORTAL = 'x';
    //public final char SPEED_ITEM = 2;
    //public final char FLAME_ITEM = 3;
    //public final char BOMB_ITEM = 4;

    public Brick(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
    }
}