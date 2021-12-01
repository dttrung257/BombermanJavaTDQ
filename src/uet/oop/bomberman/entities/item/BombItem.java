package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Point;

public class BombItem extends HiddenItem {

    public BombItem(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
    }
}
