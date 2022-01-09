package com.example.ihavetrylma;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static com.example.ihavetrylma.Client.makeAction;

public class Board {

    int numberOfPlayers;
    int sideLength;
    int height, width;
    Tile[][] arrayOfTiles;

    GridPane gameBoard;
    Label yourTurn;

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
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
    };

    public void createSkipButton(){
        SkipButton skipButton = new SkipButton();
        gameBoard.add(skipButton, width - 2 - ((sideLength-1)+(sideLength-3))/2, height - sideLength/2, 3, 1);
    }

    public void createYourTurnText(){
        Pane pane = new Pane();
        yourTurn = new Label("Your turn!");
        pane.getChildren().add(yourTurn);

        if(Client.turn) {
            yourTurn.setTextFill(Colour.setTileColor(Client.colour));
            yourTurn.setVisible(true);
        } else yourTurn.setVisible(false);


        gameBoard.add(yourTurn, (((sideLength-1)+(sideLength-3))/2), sideLength/2 - 1, 3, 1);
    }

    public void changeTextVisibility() {
        if(Client.turn) {
            yourTurn.setVisible(true);
            yourTurn.setTextFill(Colour.setTileColor(Client.colour));
        } else {
            yourTurn.setVisible(false);
        }
    }


    public void createTiles() {
        int temp = WIDTHS[WIDTHS.length-2];
        Tile tile;

        //create triangle of tiles
        for (int i = 0; i < WIDTHS[WIDTHS.length - 1]; i++) {
            temp = temp - i;
            for (int j = 0; j < WIDTHS[i]; j++) {
                tile = new Tile(temp, i, this, sideLength);
                arrayOfTiles[temp][i] = tile;

                gameBoard.add(tile, temp, i, 1, 1);

                temp = temp + 2;
            }
            temp = WIDTHS[WIDTHS.length - 2];
        }

        //create inverted triangle of tiles
        int temp2 = 0;
        for (int i = height - 1; i > height - 1 - WIDTHS[WIDTHS.length-1]; i--) {
            temp = temp - temp2;
            for (int j = 0; j < WIDTHS[temp2]; j++) {
                //check if tiles already exists
                if (arrayOfTiles[temp][i] == null) {
                    tile = new Tile(temp, i, this, sideLength);
                    arrayOfTiles[temp][i] = tile;

                    gameBoard.add(tile, temp, i, 1, 1);

                }
                temp = temp + 2;
            }
            temp = WIDTHS[WIDTHS.length - 2];
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

    private void setForFour() {
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

    public void addPiece(int column, int row, int owner) {
        arrayOfTiles[column][row].setOwner(owner);
    }

    public void removePiece(int column, int row) {
        arrayOfTiles[column][row].setOwner(-1);
    }

    public void movePiece(int oldColumn, int oldRow, int newColumn, int newRow) {
        makeAction("MOVE " + oldColumn + " " + oldRow + " " + newColumn + " " + newRow);    // Move x1 y1 x2 y2
    }

    public void makeMove(int oldColumn, int oldRow, int newColumn, int newRow) {
        addPiece(newColumn, newRow, arrayOfTiles[oldColumn][oldRow].getOwner());
        removePiece(oldColumn, oldRow);
    }

    private void setFirstPlayer() {
        int temp = width/2;
        int temp2 = width/2;
        for(int row = 0; row < sideLength - 1 ; row++){
            for(int column = temp2; column <= temp; column = column + 2){
                addPiece(column, row, 0);
                arrayOfTiles[column][row].setStroke(Color.web("#FA26A0"));
            }
            temp++;
            temp2--;
        }
    }

    private void setSecondPlayer() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for(int row = sideLength - 1; row < (sideLength - 1) * 2; row++){
            for(int column = temp2; column > temp; column = column - 2){
                addPiece(column, row, 1);
                arrayOfTiles[column][row].setStroke(Color.web("#7954A1"));
            }
            temp++;
            temp2--;
        }
    }

    private void setThirdPlayer() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for(int row = height - sideLength; row > (sideLength - 1) * 2; row--){
            for(int column = temp2; column > temp; column = column - 2){
                addPiece(column, row, 2);
                arrayOfTiles[column][row].setStroke(Color.web("#0E86D4"));
            }
            temp++;
            temp2--;
        }
    }

    private void setFourthPlayer() {
        int temp = width/2;
        int temp2 = width/2;
        for(int row = height - 1; row > height - 1 - (sideLength - 1) ; row--){
            for(int column = temp2; column <= temp; column = column + 2){
                addPiece(column, row, 3);
                arrayOfTiles[column][row].setStroke(Color.web("#76B947"));
            }
            temp++;
            temp2--;
        }
    }

    private void setFifthPlayer() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for(int row = height - sideLength; row > (sideLength - 1) * 2; row--){
            for(int column = temp2; column < temp; column = column + 2){
                addPiece(column, row, 4);
                arrayOfTiles[column][row].setStroke(Color.web("#C85250"));
            }
            temp--;
            temp2++;
        }
    }

    private void setSixthPlayer() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for(int row = sideLength - 1; row < (sideLength - 1) * 2; row++){
            for(int column = temp2; column < temp; column = column + 2){
                addPiece(column, row, 5);
                arrayOfTiles[column][row].setStroke(Color.web("#FD7F20"));
            }
            temp--;
            temp2++;
        }
    }
}
