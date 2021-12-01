package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected Point coordinate;
    protected Point canvas_coordinate;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(Point coordinate, Image img) {
        this.coordinate = coordinate;
        this.img = img;
        canvas_coordinate = coordinate.toCanvasCoordinate();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, canvas_coordinate.getX(), canvas_coordinate.getY());
    }

    public abstract void update();

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public Point getCanvas_coordinate() {
        return canvas_coordinate;
    }

    public void setCanvas_coordinate(Point canvas_coordinate) {
        this.canvas_coordinate = canvas_coordinate;
    }
}
