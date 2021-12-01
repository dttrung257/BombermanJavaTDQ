package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends AnimatedEntity {
    public Enemy(Point coordinate, Image img) {
        super(coordinate, img);
    }

    protected boolean goUp, goDown, goRight, goLeft;

    @Override
    public void update() {
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
}
