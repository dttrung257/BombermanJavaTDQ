package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.enemies.Enemy;

public class Oneal extends Enemy {
    public Oneal(Point coordinate, Image img) {
        super(coordinate, img);
    }

    /**@Override
    public void update(int newX, int newY, Image newImage) {
        x = newX;
        y = newY;
        img = newImage;
    }*/
}
