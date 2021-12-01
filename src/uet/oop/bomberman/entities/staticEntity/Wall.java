package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;

public class Wall extends Entity {

    public Wall(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
    }
}
