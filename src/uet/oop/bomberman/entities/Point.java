package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

public class Point {
    protected int x;
    protected int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point toCanvasCoordinate() {
        return new Point(x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
    }

    public Point toCoordinate() {
        return new Point((x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                            (y + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE);
    }
}
