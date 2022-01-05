package com.example.ihavetrylma;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tile extends Circle {

    Board board;
    int column, row;
    int owner = -1;

    public boolean hasOwner() {
        return owner != -1;
    }

    public Tile(int column, int row, Board board){
        this.column = column;
        this.row = row;
        this.board = board;
        setFill(getColor());
        setRadius(15);
        setStroke(Color.GRAY);
        setStrokeWidth(3);

        setOnMousePressed(e -> {
            if(board.movingRow == -1 && board.movingColumn == -1){
                board.movingRow = this.row;
                board.movingColumn = this.column;
            } else{
                board.movePiece(board.movingColumn, board.movingRow, this.column, this.row);
                board.movingColumn = -1;
                board.movingRow = -1;
            }
        });
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