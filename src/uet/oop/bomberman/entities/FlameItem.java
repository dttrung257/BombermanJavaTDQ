package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class FlameItem extends HiddenItem {

    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update(int newX, int newY, Image newImage) {
        x = newX;
        y = newY;
    }
}
