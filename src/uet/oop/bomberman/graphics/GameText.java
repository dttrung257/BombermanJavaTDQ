package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.GamePlay;
import uet.oop.bomberman.entities.Bomber;

import java.util.ArrayList;

public class GameText extends ArrayList<Text> {
    public GameText() {
        add(0, new Text(5, 20, "Level 1"));
        add(1, new Text(120, 20, "Lives: 3"));
        add(2, new Text(250, 20, "Score: 0"));
        add(3, new Text(220, 475, "[SPACE]: Place bomb"));
        add(4, new Text(10, 475, "[P]: Pause/Resume"));
        add(5, new Text(385, 415, "Lives: 3"));
        add(6, new Text(585, 415, "Lives: 3"));
        for (int i = 0; i < 7; i++) {
            get(i).setFont(Font.font("verdana", 18));
            get(i).setFill(Color.BLUE);
        }
    }

    public void update() {
        get(0).setText("Level " + GamePlay.gameLevel);
        get(1).setText("Lives: " + Bomber.bomberLife);
        get(2).setText("Score: " + GamePlay.score);
        /**text.setText("Hello how are you");
        text.setX(50);
        text.setY(50);*/
    }

    /**public void render(GraphicsContext gc) {
        gc.drawImage(img, canvas_coordinate.getX(), canvas_coordinate.getY());
    }*/
}
