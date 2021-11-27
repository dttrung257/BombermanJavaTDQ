package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class HiddenItem extends Entity {
    public HiddenItem(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update(int newX, int newY, Image newImage) {

    }
}
