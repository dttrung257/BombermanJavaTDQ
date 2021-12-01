package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Entity {
    public Enemy(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
    }

}
