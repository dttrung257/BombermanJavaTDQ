package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;

public class Bomb extends StaticEntity {
    private static Media explosion = new Media(new File("res/audios/explosion1.mp3").toURI().toString());
    protected int timeExplode = 120;
    protected static int rangeOfFlame = 1;
    protected int animate = 0;

    public Bomb(Point coordinate, Image img) {
        super(coordinate, img);
    }

    @Override
    public void update() {
        handleAnimation(Sprite.bomb_1, Sprite.bomb, Sprite.bomb_2);
        timeExplode--;
        if (timeExplode < 0) {
            explode();
        }
    }

    public void handleAnimation(Sprite sprite1, Sprite sprite2, Sprite sprite3) {
        img = Sprite.movingSprite(sprite1, sprite2, sprite3, animate, 50).getFxImage();
        if (animate > 90) {
            animate = 0;
        } else {
            animate++;
        }
    }

    protected void explode() {
        GamePlay.setFlame(new Flame(coordinate, img, rangeOfFlame));
        new MediaPlayer(explosion).play();
        GamePlay.removeBomb();
    }

    public static void setRangeOfFlame(int range) {
        if(range != 0) {
            rangeOfFlame = range;
        } else {
            rangeOfFlame++;
        }
    }

    public static int getRangeOfFlame() {
        return rangeOfFlame;
    }
}
