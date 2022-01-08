package Server;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameTile extends Circle {

    GameBoard board;
    int column, row;
    int owner = -1;

    public boolean hasOwner() {
        return owner != -1;
    }

    GameTile(int column, int row, GameBoard board){
        this.column = column;
        this.row = row;
        this.board = board;
        setFill(getColor());
        setRadius(15);
        setStroke(Color.GRAY);
        setStrokeWidth(3);
    }

    public void setOwner(int owner){
        this.owner = owner;
        setFill(getColor());
    }

    public int getOwner() {
        return owner;
    }

    public Color getColor(){
        if(owner == 0 ){
            return Color.web("#FCC6F6");
        }else if(owner == 1){
            return Color.web("#BC64FF");
        }else if(owner == 2){
            return Color.web("#3E40EC");
        }else if(owner == 3){
            return Color.web("#75D23D");
        }else if(owner == 4){
            return Color.web("#BD3335");
        }else if(owner == 5){
            return Color.web("#FE9F04");
        }
        return Color.web("#F3F3F3");
    }
}