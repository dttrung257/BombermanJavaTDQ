package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.entities.staticEntity.Grass;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends HiddenItem {
    public BombItem(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
    }
}
