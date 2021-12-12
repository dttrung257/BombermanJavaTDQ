package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public abstract class Enemy extends AnimatedEntity {
    protected int dying = 0;

    public Enemy(Point coordinate, Image img) {
        super(coordinate, img);
    }

    protected boolean goUp, goDown, goRight, goLeft;

    @Override
    public void update() {
        if (!alive) {
            handleDieAnimation();
            if (dying == 60) {
                GamePlay.removeEnemy(this);
            }
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

    public abstract void handleDieAnimation();
}
