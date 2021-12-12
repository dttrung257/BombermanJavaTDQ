package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Point;

import java.util.ArrayList;
import java.util.List;

public class Flame extends Entity {
    protected int time = 30;
    protected int frame = -1;
    protected List<FlameSegment> flameSegments;
    protected int range;

    public Flame(Point coordinate, Image img, int range) {
        super(coordinate, img);
        this.range = range;
        createFlameSegments();
    }

    public void update() {
        time--;
        if (time > 0) {
            flameSegments.forEach(FlameSegment::update);
        } else {
            GamePlay.removeFlame();
        }
    }

    public void createSpark(Direction direction) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        int xt = x;
        int yt = y;
        int radius = range;
        for (int i = 1; i <= radius; i++) {
            switch (direction) {
                case up:
                    xt = x;
                    yt = y - i;
                    break;
                case down:
                    xt = x;
                    yt = y + i;
                    break;
                case left:
                    xt = x - i;
                    yt = y;
                    break;
                case right:
                    xt = x + i
                    ;
                    yt = y;
                    break;
            }
            Entity entity = GamePlay.getEntityAtPosition(xt, yt);
            if (entity instanceof Wall) {
                radius = i - 1;
            } else if (entity instanceof Brick) {
                radius = i;
                flameSegments.add(new FlameSegment(new Point(xt, yt), img, direction, true));
                Brick brick = (Brick) entity;
                brick.destroy();
            } else if (entity instanceof Bomb) {
                radius = i - 1;
                ((Bomb) entity).timeExplode = 0;

            } else {
                if (i == radius) {
                    flameSegments.add(new FlameSegment(new Point(xt, yt), img, direction, true));
                } else {
                    flameSegments.add(new FlameSegment(new Point(xt, yt), img, direction, false));
                }
            }
        }
    }

    public void createFlameSegments() {
        int x = coordinate.getX();
        int y = coordinate.getY();
        flameSegments = new ArrayList<>();
        flameSegments.add(new FlameSegment(new Point(x, y), img, Direction.center, false));
        createSpark(Direction.up);
        createSpark(Direction.down);
        createSpark(Direction.left);
        createSpark(Direction.right);
    }

    public List<FlameSegment> get_flameSegments() {
        return flameSegments;
    }

}
