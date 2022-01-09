package Server;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameTile extends Circle {

    GameBoard board;
    int column, row;
    int owner = -1;
    int sideLength;
    int radius = 15;

    public boolean hasOwner() {
        return owner != -1;
    }

    public GameTile(int column, int row, GameBoard board, int sideLength) {
        this.column = column;
        this.row = row;
        this.board = board;
        this.sideLength = sideLength;

        setFill(getColor());
        if (sideLength == 5) {
            setRadius(radius);
        } else if (sideLength < 5) {
            setRadius(radius + 5);
        } else {
            setRadius(radius - 5);
        }
        setStroke(Color.GRAY);
        setStrokeWidth(3);
    }

    public void setOwner(int owner) {
        this.owner = owner;
        setFill(getColor());
    }

    public int getOwner() {
        return owner;
    }

    public Color getColor() {
        return switch (owner) {
            case 0 -> Color.web("#FCC6F6");
            case 1 -> Color.web("#BC64FF");
            case 2 -> Color.web("#3E40EC");
            case 3 -> Color.web("#75D23D");
            case 4 -> Color.web("#BD3335");
            case 5 -> Color.web("#FE9F04");
            default -> Color.web("#F3F3F3");
        };
    }

}