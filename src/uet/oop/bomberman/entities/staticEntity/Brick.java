package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends StaticEntity {
    protected int destroying = 0;
    protected boolean destroyed = false;

    public Brick(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        if (destroyed) {
            if (destroying == 30) {
                GamePlay.destroyBrick(this);
                destroying = 0;
            }
            handleAnimation(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
        }
    }

    public void destroy() {
        destroyed = true;
    }

    public void handleAnimation(Sprite sprite1, Sprite sprite2, Sprite sprite3) {
        img = Sprite.movingSprite(sprite1, sprite2, sprite3, destroying, 10).getFxImage();
        destroying++;
    }
}