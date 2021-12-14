package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.util.Random;

public abstract class Enemy extends AnimatedEntity {
    protected MediaPlayer roar = new MediaPlayer(new Media(new File("res/audios/EnemyEat.mp3").toURI().toString()));

    public Enemy(Point coordinate, Image img) {
        super(coordinate, img);
    }

    protected boolean goUp, goDown, goRight, goLeft;

    @Override
    public void update() {
        handleAnimation();
        if (!alive) {
            handleDieAnimation();
            if (animation == 60) {
                GamePlay.removeEnemy(this);
            }
        }
    }

    public void handleAnimation() {
        if (animation > 120) {
            animation = 0;
        } else {
            animation++;
        }
    }

    public void setGoDown() {
        goDown = true;
        goUp = false;
        goRight = false;
        goLeft = false;
    }

    public void setGoUp() {
        goDown = false;
        goUp = true;
        goRight = false;
        goLeft = false;
    }

    public void setGoRight() {
        goDown = false;
        goUp = false;
        goRight = true;
        goLeft = false;
    }

    public void setGoLeft() {
        goDown = false;
        goUp = false;
        goRight = false;
        goLeft = true;
    }

    /** Tao huong ngau nhien. */
    public void randomizeDirection() {
        Random r = new Random();
        int dir = r.nextInt(80);
        switch (dir) {
            case 1:
                setGoUp();
                break;
            case 2:
                setGoDown();
                break;
            case 3:
                setGoRight();
                break;
            case 4:
                setGoLeft();
                break;
        }
    }

    @Override
    public void die() {
        if (alive) {
            this.alive = false;
            animation = 0;
            GamePlay.score += 100;
        }
    }

}
