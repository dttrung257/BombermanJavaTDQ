package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Oneal extends Entity {
    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update(int newX, int newY, Image newImage) {
        x = newX;
        y = newY;
        img = newImage;
    }
}