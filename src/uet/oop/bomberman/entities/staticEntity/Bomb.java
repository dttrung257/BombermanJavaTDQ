package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Point;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimatedEntity {
    private int BombPower = 1;
    private long startTime;
    private boolean waitingExplosion = false;

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
            for(int i = 1; i <= getBombPower(); i++) {
                Point temp = coordinate;
                temp.setX(temp.getX() + i);
                GamePlay.flames.add(new Flame(coordinate, Sprite.explosion_horizontal1.getFxImage()));
                temp.setX(temp.getX() - i * 2);
                GamePlay.flames.add(new Flame(coordinate, Sprite.explosion_horizontal1.getFxImage()));
                temp = coordinate;
                temp.setX(temp.getX() + 1);
                temp.setY(temp.getY() + i);
                GamePlay.flames.add(new Flame(coordinate, Sprite.explosion_vertical1.getFxImage()));
                temp.setY(temp.getY() - i*2);
                GamePlay.flames.add(new Flame(coordinate, Sprite.explosion_vertical1.getFxImage()));
            }
            GamePlay.bombs.remove(0);
        }
    }

    public void buzz() {
        //GamePlay.flames.forEach(g -> g.render(gc));
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
}
