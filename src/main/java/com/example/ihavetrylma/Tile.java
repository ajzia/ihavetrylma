package com.example.ihavetrylma;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tile extends Circle {

    Board board;
    int column, row;
    int owner = -1;
    int base = -1;
    int sideLength;
    int radius = 15;

    public Tile(int column, int row, Board board, int sideLength) {
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
            setRadius(radius - (3 * (sideLength - 5)));
        }

        setStroke(Color.GRAY);
        setStrokeWidth(3);

        setOnMousePressed(e -> {
            if (Client.turn) {
                if (board.movingRow == -1 && board.movingColumn == -1) {
                    if (Client.colour == getOwner()) {
                        board.movingRow = this.row;
                        board.movingColumn = this.column;
                    } else System.out.println("It's not one of your pieces!");
                } else {
                    board.movePiece(board.movingColumn, board.movingRow, this.column, this.row);
                    board.movingColumn = -1;
                    board.movingRow = -1;
                }
            } else System.out.println("Wait for your turn!");
        });
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

    public Color getColor() {
        return Colour.setTileColor(owner);
    }
}