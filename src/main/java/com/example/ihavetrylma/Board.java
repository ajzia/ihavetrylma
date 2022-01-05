package com.example.ihavetrylma;

import javafx.scene.layout.GridPane;

public class Board {

    int numberOfPlayers;
    int sideLength;
    int height, width;
    Tile[][] arrayOfTiles;

    GridPane gameBoard;

    public int movingRow = -1;
    public int movingColumn = -1;

    Board(int height, int width, Tile[][] arrayOfTiles, int numberOfPlayers, int sideLength, GridPane gameBoard) {
        this.height = height;
        this.width = width;
        this.arrayOfTiles = arrayOfTiles;
        this.numberOfPlayers = numberOfPlayers;
        this.sideLength = sideLength;
        this.gameBoard = gameBoard;
    }

    // Width of each row of the triangle
    private static final int[] WIDTHS = {
            1,2,3,4,5,6,7,8,9,10,11,12,13
    };

    public void createTiles(){
        int temp = WIDTHS[WIDTHS.length-1];
        Tile tile;

        //create triangle of tiles
        for(int i = 0; i < WIDTHS[WIDTHS.length-1]; i++){
            temp = temp - i;
            for(int j = 0; j < WIDTHS[i]; j++) {
                tile = new Tile( temp, i, this);
                arrayOfTiles[temp][i] = tile;

                gameBoard.add(tile, temp, i, 1, 1);

                temp = temp + 2;
            }
            temp = WIDTHS[WIDTHS.length-1];
        }

        //create inverted triangle of tiles
        int temp2 = 0;
        for(int i = 16; i > 3; i--){
            temp = temp - temp2;
            for(int j = 0; j < WIDTHS[temp2]; j++) {
                //check if tiles already exists
                if(arrayOfTiles[temp][i] == null) {
                    tile = new Tile(temp, i, this);
                    arrayOfTiles[temp][i] = tile;

                    gameBoard.add(tile, temp, i, 1, 1);

                }
                temp=temp+2;
            }
            temp = WIDTHS[WIDTHS.length-1];
            temp2++;
        }
    }

    public void makePieces() {
        switch (numberOfPlayers) {
            case 2 -> setForTwo();
            case 3 -> setForThree();
            case 4 -> setForFour();
            case 6 -> setForSix();
            default -> System.out.println("Wrong number of players");
        }
    }
    private void setForTwo() {
        setFirstPlayer();
        setFourthPlayer();
    }

    private void setForThree() {
        setSecondPlayer();
        setFourthPlayer();
        setSixthPlayer();
    }

    private void setForFour(){
        setSecondPlayer();
        setThirdPlayer();
        setFifthPlayer();
        setSixthPlayer();
    }

    private void setForSix() {
        setFirstPlayer();
        setSecondPlayer();
        setThirdPlayer();
        setFourthPlayer();
        setFifthPlayer();
        setSixthPlayer();
    }

    public void addPiece(int column, int row, int owner){
        arrayOfTiles[column][row].setOwner(owner);
    }

    public void removePiece(int column, int row){
        arrayOfTiles[column][row].setOwner(-1);
    }

    public void movePiece(int oldColumn, int oldRow, int newColumn, int newRow){
        addPiece(newColumn, newRow, arrayOfTiles[oldColumn][oldRow].getOwner());
        removePiece(oldColumn, oldRow);
    }

    private void setFirstPlayer(){
        addPiece(13, 0, 0);
        addPiece(12, 1, 0);
        addPiece(14, 1, 0);
        addPiece(11, 2, 0);
        addPiece(13, 2, 0);
        addPiece(15, 2, 0);
        addPiece(10, 3, 0);
        addPiece(12, 3, 0);
        addPiece(14, 3, 0);
        addPiece(16, 3, 0);
    }

    private void setSecondPlayer(){
        addPiece(25, 4, 1);
        addPiece(23, 4, 1);
        addPiece(21, 4, 1);
        addPiece(19, 4, 1);
        addPiece(24, 5, 1);
        addPiece(22, 5, 1);
        addPiece(20, 5, 1);
        addPiece(23, 6, 1);
        addPiece(21, 6, 1);
        addPiece(22, 7, 1);
    }

    private void setThirdPlayer(){
        addPiece(22, 9, 2);
        addPiece(21, 10, 2);
        addPiece(23, 10, 2);
        addPiece(24, 11, 2);
        addPiece(22, 11, 2);
        addPiece(20, 11, 2);
        addPiece(25, 12, 2);
        addPiece(23, 12, 2);
        addPiece(21, 12, 2);
        addPiece(19, 12, 2);
    }

    private void setFourthPlayer(){
        addPiece(13, 16, 3);
        addPiece(12, 15, 3);
        addPiece(14, 15, 3);
        addPiece(11, 14, 3);
        addPiece(13, 14, 3);
        addPiece(15, 14, 3);
        addPiece(10, 13, 3);
        addPiece(12, 13, 3);
        addPiece(14, 13, 3);
        addPiece(16, 13, 3);
    }

    private void setFifthPlayer(){
        addPiece(4, 9, 4);
        addPiece(3, 10, 4);
        addPiece(5, 10, 4);
        addPiece(2, 11, 4);
        addPiece(4, 11, 4);
        addPiece(6, 11, 4);
        addPiece(1, 12, 4);
        addPiece(3, 12, 4);
        addPiece(5, 12, 4);
        addPiece(7, 12, 4);
    }

    private void setSixthPlayer(){
        addPiece(1, 4, 5);
        addPiece(3, 4, 5);
        addPiece(5, 4, 5);
        addPiece(7, 4, 5);
        addPiece(2, 5, 5);
        addPiece(4, 5, 5);
        addPiece(6, 5, 5);
        addPiece(3, 6, 5);
        addPiece(5, 6, 5);
        addPiece(4, 7, 5);
    }
}
