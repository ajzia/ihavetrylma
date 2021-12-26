package com.example.ihavetrylma;

import javafx.scene.paint.Color;

public class Board {

    int numberOfPlayers;
    int sideLength;
    int height, width;

    Board(int height, int width, int numberOfPlayers, int sideLength) {
        this.height = height;
        this.width = width;
        this.numberOfPlayers = numberOfPlayers;
        this.sideLength = sideLength;
    }

    public void makePieces(Tile[][] arrayOfTiles) {

        switch (numberOfPlayers) {
            case 2 -> setForTwo(arrayOfTiles);
            case 3 -> setForThree(arrayOfTiles);
            case 4 -> setForFour(arrayOfTiles);
            case 6 -> setForSix(arrayOfTiles);
            default -> System.out.println("Wrong number of players");
        }
    }

    private void setForTwo(Tile[][] arrayOfTiles) {
        int temp = width / 2;
        for(int i = 0; i < sideLength - 1; i++) {
            for (int j = 0; j <= i; j++) {
                arrayOfTiles[i][temp + 2 * j].setColor(Color.FORESTGREEN);
                arrayOfTiles[height - i - 1][temp + 2 * j].setColor(Color.INDIANRED);
            }
            temp--;
        }
    }

    private void setForThree(Tile[][] arrayOfTiles) {
        int temp = 0;
        for(int i = sideLength - 1; i < 2 * sideLength - 2; i++){
            for(int j = 0; j < 2 * sideLength - i - 2; j++){
                arrayOfTiles[i][temp + 2 * j].setColor(Color.FORESTGREEN);
                arrayOfTiles[i][width - 1 - (temp + 2 * j)].setColor(Color.GOLD);
            }
            temp++;
        }
        temp = width / 2;
        for(int i = 0; i < sideLength - 1; i++) {
            for (int j = 0; j <= i; j++) {
                arrayOfTiles[height - 1 - i][temp + 2 * j].setColor(Color.INDIANRED);
            }
            temp--;
        }
    }

    private void setForFour(Tile[][] arrayOfTiles) {
        int temp = 0;
        for(int i = sideLength - 1; i < 2 * sideLength - 2; i++){
            for(int j = 0; j < 2 * sideLength - i - 2; j++){
                arrayOfTiles[i][temp + 2 * j].setColor(Color.GOLD);
                arrayOfTiles[i][width - 1 - (temp + 2 * j)].setColor(Color.FUCHSIA);
                arrayOfTiles[height -i - 1][temp + 2 * j].setColor(Color.CORAL);
                arrayOfTiles[height - i - 1][width - 1 - (temp + 2 * j)].setColor(Color.CYAN);
            }
            temp++;
        }
    }

    private void setForSix(Tile[][] arrayOfTiles) {
        setForTwo(arrayOfTiles);
        setForFour(arrayOfTiles);
    }

}
