package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.staticEntity.Grass;

import static uet.oop.bomberman.GamePlay.getEntityAtPosition;
import static uet.oop.bomberman.entities.staticEntity.Bomb.setRangeOfFlame;

public class FlameItem extends HiddenItem {

    public FlameItem(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
    }

    @Override
    public void handleItem() {
        setRangeOfFlame(0);
        GamePlay.removeItem(this);
    }
}
