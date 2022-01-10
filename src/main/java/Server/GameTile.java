package Server;

import Client.Colour;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameTile extends Circle {

    GameBoard board;
    int column, row;
    int owner = -1;
    int base = -1;
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

    public void setBase(int owner){
        this.base = owner;
    }

    public int getBase(){
        return base;
    }

    public Color getColor() {
        return Colour.setTileColor(owner);
    }

}