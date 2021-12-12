package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends StaticEntity {
    protected int timeExplode = 120;
    protected static int rangeOfFlame = 2;
    protected int animate = 0;

    public Bomb(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        handleAnimation(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2);
        if (timeExplode < 0) {
            explode();
        }
    }

    public void handleAnimation(Sprite sprite1, Sprite sprite2, Sprite sprite3) {
        img = Sprite.movingSprite(sprite1, sprite2, sprite3, animate, 30).getFxImage();
        if (animate < 1600) {
            animate = 0;
        } else {
            animate++;
        }
        timeExplode--;
    }

    protected void explode() {
        GamePlay.setFlame(new Flame(coordinate, img, rangeOfFlame));
        GamePlay.removeBomb();
    }

    public static void setRangeOfFlame(int rangeOfFlame) {
        Bomb.rangeOfFlame = rangeOfFlame;
    }
}
