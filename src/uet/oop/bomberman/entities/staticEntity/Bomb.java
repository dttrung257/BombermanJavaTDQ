package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.GamePlay.opened;

public class Bomb extends AnimatedEntity {
    private int BombPower = 1;
    private long startTime;
    private boolean waitingExplosion;
    private List<Flame> flames = new ArrayList<>();

    public Bomb(Point coordinate, Image img, long startTime) {
        super(coordinate, img);
        this.waitingExplosion = true;
        this.startTime = startTime;
    }

    @Override
    public void handleMove() {
    }

    @Override
    public void update() {
        if(System.currentTimeMillis() - startTime >= 1000) {
            int x = coordinate.getX();
            int y = coordinate.getY();
            Point temp = new Point(x, y);
            for(int i = getBombPower(); i >= 1; i--) {
                temp.setY(y - i);
                if(checkFlame(temp)) {
                    flames.add(new Flame(temp, Sprite.explosion_vertical1.getFxImage()));
                }
            }
            temp.setY(y);
            for(int i = getBombPower(); i >= 1; i--) {
                temp.setX(x - i);
                if(checkFlame(temp)) {
                    flames.add(new Flame(temp, Sprite.explosion_horizontal1.getFxImage()));
                }
            }
            for(int i = getBombPower(); i >= 1; i--) {
                temp.setX(x + i);
                if(checkFlame(temp)) {
                    flames.add(new Flame(temp, Sprite.explosion_horizontal1.getFxImage()));
                }
            }
            temp.setX(x);
            for(int i = getBombPower(); i >= 1; i--) {
                temp.setY(y + i);
                if(checkFlame(temp)) {
                    flames.add(new Flame(temp, Sprite.explosion_vertical1.getFxImage()));
                }
            }
            waitingExplosion = false;
        }
    }

    public static boolean checkFlame(Point p) {
        Entity checkEntity = GamePlay.getEntityAtPosition(p.getX(), p.getY());
        if(!(checkEntity instanceof Wall)) {
            if(checkEntity instanceof Brick) {
                GamePlay.opened.add(GamePlay.getBrickIndex(p));
            }
            return true;
        }
        return false;
    }

    public int getBombPower() {
        return BombPower;
    }

    public void setBombPower(int bombPower) {
        BombPower = bombPower;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public boolean isWaitingExplosion() {
        return waitingExplosion;
    }

    public void setWaitingExplosion(boolean waitingExplosion) {
        this.waitingExplosion = waitingExplosion;
    }

    public List<Flame> getFlames() {
        return flames;
    }

    public void setFlames(List<Flame> flames) {
        this.flames = flames;
    }
}
