package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;

public abstract class HiddenItem extends Entity {
    public HiddenItem(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {

    }

    public abstract void handleItem();

}
