package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Point;

public class FlameItem extends HiddenItem {

    public FlameItem(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
    }
}
