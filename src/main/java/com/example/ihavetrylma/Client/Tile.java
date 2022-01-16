package com.example.ihavetrylma.Client;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class responsible for tiles
 */
public class Tile extends Circle {

    /**
     * Object of Board class
     */
    protected Board board;
    /**
     * Column number
     */
    protected int column;
    /**
     * Row number
     */
    protected int row;
    /**
     * Default owner number
     */
    protected int owner = -1;
    /**
     * Length of a side of a hexagon
     */
    protected int sideLength;
    /**
     * Default length of a radius
     */
    protected int radius = 15;

    /**
     * Tile constructor
     * @param column - column number
     * @param row - row number
     * @param board - board
     * @param sideLength - length of a side of a hexagon
     */
    protected Tile(int column, int row, Board board, int sideLength) {
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

    /**
     * Method setting an owner of a tile
     * @param owner - owner of a tile
     */
    public void setOwner(int owner) {
        this.owner = owner;
        setFill(getColor());
    }

    /**
     * Method returning owner number
     * @return owner number
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Method checking if a tile has a piece
     * @return true if has a piece, false if doesn't have a piece
     */
    public boolean hasOwner() {
        return owner != -1;
    }

    /**
     * Method returning color of a tile
     * @return color of a tile
     */
    public Color getColor() {
        return Colour.getTileColor(owner);
    }
}