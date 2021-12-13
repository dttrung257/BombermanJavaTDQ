package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

public class FlameSegment extends StaticEntity {
    protected boolean _last;
    protected int _animate = 0;

    public FlameSegment(Point coordinate, Image img, Entity.Direction direction, boolean last) {
        super(coordinate, img);
        _last = last;
        this.direction = direction;
    }


    public void animate() {
        if (_animate > 29) _animate = 0;
        else _animate++;
    }

    protected void handleAnimation() {
        switch (direction) {
            case center:
                imageAnimation(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2);
                break;
            case up:
                if (_last) {
                    imageAnimation(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2);
                } else {
                    imageAnimation(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2);
                }
                break;
            case down:
                if (_last) {
                    imageAnimation(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2);
                } else {
                    imageAnimation(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2);
                }
                break;
            case left:
                if (_last) {
                    imageAnimation(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2);
                } else {
                    imageAnimation(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2);
                }
                break;
            case right:
                if (_last) {
                    imageAnimation(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2);
                } else {
                    imageAnimation(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2);
                }
                break;

            case other:
            default:
                break;
        }
    }


    public void imageAnimation(Sprite sprite1, Sprite sprite2, Sprite sprite3) {
        img = Sprite.movingSprite(sprite2, sprite3, sprite1, _animate, 30).getFxImage();
    }

    @Override
    public void update() {
        animate();
        handleAnimation();
        handleCollision();
    }


    public void handleCollision() {
        Entity entity = GamePlay.getEntityAtPosition(coordinate.getX(), coordinate.getY());
        if (entity instanceof AnimatedEntity) {
            if (!((AnimatedEntity) entity).isCaughtFlame()) {
                ((AnimatedEntity) entity).die();
            }
        }
        if (entity instanceof Brick) {
            ((Brick) entity).destroy();
        }
    }
}
