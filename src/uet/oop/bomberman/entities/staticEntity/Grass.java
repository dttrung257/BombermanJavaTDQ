package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;

public class Grass extends Entity {

    public Grass(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
    }

}
