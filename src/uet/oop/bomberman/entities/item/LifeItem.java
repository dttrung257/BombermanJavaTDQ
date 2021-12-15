package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Point;

public class LifeItem extends HiddenItem{
    public LifeItem(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void handleItem() {
        Bomber.upLife();
        GamePlay.removeItem(this);
    }
}
