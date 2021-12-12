package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;

public abstract class StaticEntity extends Entity {
    public StaticEntity(Point coordinate, Image img) {
        super(coordinate, img);
    }

}
