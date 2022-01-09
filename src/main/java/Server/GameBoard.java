package Server;

import com.example.ihavetrylma.Tile;
import javafx.scene.paint.Color;

public class GameBoard {

    int numberOfPlayers;
    int sideLength;
    int height, width;
    GameTile[][] arrayOfTiles;

    GameBoard(int width, int height, GameTile[][] arrayOfTiles, int numberOfPlayers, int sideLength) {
        this.height = height;
        this.width = width;
        this.arrayOfTiles = arrayOfTiles;
        this.numberOfPlayers = numberOfPlayers;
        this.sideLength = sideLength;
    }

    // Width of each row of the triangle
    private static final int[] WIDTHS = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
    };

    public void createTiles() {
        int temp = WIDTHS[WIDTHS.length-2];
        GameTile tile;

        //create triangle of tiles
        for (int i = 0; i < WIDTHS[WIDTHS.length - 1]; i++) {
            temp = temp - i;
            for (int j = 0; j < WIDTHS[i]; j++) {
                tile = new GameTile(temp, i, this, sideLength);
                arrayOfTiles[temp][i] = tile;
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
                    tile = new GameTile(temp, i, this, sideLength);
                    arrayOfTiles[temp][i] = tile;
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

    public boolean makeMove(int oldColumn, int oldRow, int newColumn, int newRow) {
        int oldOwner = arrayOfTiles[oldColumn][oldRow].getOwner();
        int newOwner = arrayOfTiles[newColumn][newRow].getOwner();

        GameRules gameRules = new GameRules(oldColumn, oldRow, newColumn, newRow, oldOwner, newOwner, arrayOfTiles);
        if (gameRules.isValid()) {
            addPiece(newColumn, newRow, arrayOfTiles[oldColumn][oldRow].getOwner());
            removePiece(oldColumn, oldRow);
            return true;
        }
        return false;
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