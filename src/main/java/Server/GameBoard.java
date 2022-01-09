package Server;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameBoard {

    protected ArrayList<GameTile> firstPlayer = new ArrayList<>();
    protected ArrayList<GameTile> secondPlayer = new ArrayList<>();
    protected ArrayList<GameTile> thirdPlayer = new ArrayList<>();
    protected ArrayList<GameTile> fourthPlayer = new ArrayList<>();
    protected ArrayList<GameTile> fifthPlayer = new ArrayList<>();
    protected ArrayList<GameTile> sixthPlayer = new ArrayList<>();

    private final int numberOfPlayers;
    private final int sideLength;
    private final int height, width;
    private final GameTile[][] arrayOfTiles;

    protected int jumpColumn = 0;
    protected int jumpRow = 0;
    protected boolean previousJump = false;

    protected GameBoard(int width, int height, GameTile[][] arrayOfTiles, int numberOfPlayers, int sideLength) {
        this.height = height;
        this.width = width;
        this.arrayOfTiles = arrayOfTiles;
        this.numberOfPlayers = numberOfPlayers;
        this.sideLength = sideLength;

        createTiles();
        makePieces();
    }

    // Width of each row of the triangle
    private static final int[] WIDTHS = {
            1, 2, 3, 4, 5, 6, 7//, 8, 9, 10, 11, 12, 13
    };

    private void createTiles() {
        int temp = WIDTHS[WIDTHS.length - 2];
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
        for (int i = height - 1; i > height - 1 - WIDTHS[WIDTHS.length - 1]; i--) {
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

    private void makePieces() {
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

    private void addPiece(int column, int row, int owner) {
        arrayOfTiles[column][row].setOwner(owner);
    }

    private void removePiece(int column, int row) {
        arrayOfTiles[column][row].setOwner(-1);
    }

    protected int makeMove(int oldColumn, int oldRow, int newColumn, int newRow) {
        GameRules gameRules;

        int oldOwner = arrayOfTiles[oldColumn][oldRow].getOwner();
        int newOwner = arrayOfTiles[newColumn][newRow].getOwner();

        if (jumpColumn == 0 && jumpRow == 0) {
            gameRules = new GameRules(oldColumn, oldRow, newColumn, newRow, oldOwner, newOwner, arrayOfTiles);
            previousJump = false;
        } else {
            gameRules = new GameRules(jumpColumn, jumpRow, newColumn, newRow, oldOwner, newOwner, arrayOfTiles);
        }

        int move = gameRules.isValid();
        if (move > 0) {
            if (move == 1) {
                if (previousJump) {
                    if (jumpColumn != oldColumn && jumpRow != oldRow) {
                        return 0;
                    }
                }

                jumpColumn = newColumn;
                jumpRow = newRow;
            }
            if (move == 2) {
                if (previousJump) {
                    if (jumpColumn != oldColumn && jumpRow != oldRow) {
                        return 0;
                    }
                    return 0;
                }
            }

            addPiece(newColumn, newRow, arrayOfTiles[oldColumn][oldRow].getOwner());
            removePiece(oldColumn, oldRow);

            previousJump = move == 1;
            return gameRules.isValid() == 1 ? 1 : 2;
        }
        return 0;
    }

    protected void resetJump() {
        jumpColumn = 0;
        jumpRow = 0;
    }

    protected boolean isThisTheEnd(int side, int color) {
        switch (color) {
            case 0 -> {
                System.out.println(0);
                WinningConditions win = new WinningConditions(side, firstPlayer);
                return win.ifWon();
            }
            case 1 -> {
                System.out.println(1);
                WinningConditions win = new WinningConditions(side, secondPlayer);
                return win.ifWon();
            }
            case 2 -> {
                System.out.println(2);
                WinningConditions win = new WinningConditions(side, thirdPlayer);
                return win.ifWon();
            }
            case 3 -> {
                System.out.println(3);
                WinningConditions win = new WinningConditions(side, fourthPlayer);
                return win.ifWon();
            }
            case 4 -> {
                System.out.println(4);
                WinningConditions win = new WinningConditions(side, fifthPlayer);
                return win.ifWon();
            }
            case 5 -> {
                System.out.println(5);
                WinningConditions win = new WinningConditions(side, sixthPlayer);
                return win.ifWon();
            }
            default -> {
                return false;
            }
        }
    }

    private void setFirstPlayer() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = 0; row < sideLength - 1; row++) {
            for (int column = temp2; column <= temp; column = column + 2) {
                addPiece(column, row, 0);
                arrayOfTiles[column][row].setStroke(Color.web("#FA26A0"));
                arrayOfTiles[column][row].setBase(3);
                fourthPlayer.add(arrayOfTiles[column][row]);  // czwarta baza
            }
            temp++;
            temp2--;
        }
    }

    private void setSecondPlayer() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = sideLength - 1; row < (sideLength - 1) * 2; row++) {
            for (int column = temp2; column > temp; column = column - 2) {
                addPiece(column, row, 1);
                arrayOfTiles[column][row].setStroke(Color.web("#7954A1"));

                if (numberOfPlayers != 3) {
                    arrayOfTiles[column][row].setBase(4);    // ustawia dla 4
                    fifthPlayer.add(arrayOfTiles[column][row]);
                }
            }
            temp++;
            temp2--;
        }

        if (numberOfPlayers == 3) {                      // ustawia dla siebie
            int x = (sideLength - 1) * 2;
            int y = 0;
            for (int r = height - sideLength; r > (sideLength - 1) * 2; r--) {
                for (int c = y; c < x; c = c + 2) {
                    arrayOfTiles[c][r].setBase(1);
                    secondPlayer.add(arrayOfTiles[c][r]);
                }
                x--;
                y++;
            }

        }
    }

    private void setThirdPlayer() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = height - sideLength; row > (sideLength - 1) * 2; row--) {
            for (int column = temp2; column > temp; column = column - 2) {
                addPiece(column, row, 2);
                arrayOfTiles[column][row].setStroke(Color.web("#0E86D4"));
                arrayOfTiles[column][row].setBase(5);
                sixthPlayer.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    private void setFourthPlayer() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = height - 1; row > height - 1 - (sideLength - 1); row--) {
            for (int column = temp2; column <= temp; column = column + 2) {
                addPiece(column, row, 3);
                arrayOfTiles[column][row].setStroke(Color.web("#76B947"));
                if (numberOfPlayers != 3) {
                    arrayOfTiles[column][row].setBase(0);
                    firstPlayer.add(arrayOfTiles[column][row]);
                }
            }
            temp++;
            temp2--;
        }

        if (numberOfPlayers == 3) {
            int x = width / 2;
            int y = width / 2;
            for (int r = 0; r < sideLength - 1; r++) {
                for (int c = y; c <= x; c = c + 2) {
                    arrayOfTiles[c][r].setBase(3);
                    fourthPlayer.add(arrayOfTiles[c][r]);
                }
                x++;
                y--;
            }
        }

    }

    private void setFifthPlayer() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for (int row = height - sideLength; row > (sideLength - 1) * 2; row--) {
            for (int column = temp2; column < temp; column = column + 2) {
                addPiece(column, row, 4);
                arrayOfTiles[column][row].setStroke(Color.web("#C85250"));
                arrayOfTiles[column][row].setBase(1);
                secondPlayer.add(arrayOfTiles[column][row]);
            }
            temp--;
            temp2++;
        }
    }

    private void setSixthPlayer() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for (int row = sideLength - 1; row < (sideLength - 1) * 2; row++) {
            for (int column = temp2; column < temp; column = column + 2) {
                addPiece(column, row, 5);
                arrayOfTiles[column][row].setStroke(Color.web("#FD7F20"));
                if (numberOfPlayers != 3) {
                    arrayOfTiles[column][row].setBase(2);
                    thirdPlayer.add(arrayOfTiles[column][row]);
                }
            }
            temp--;
            temp2++;
        }

        if (numberOfPlayers == 3) {
            int x = width - ((sideLength - 1) * 2 + 1);
            int y = width - 1;
            for (int r = height - sideLength; r > (sideLength - 1) * 2; r--) {
                for (int c = y; c > x; c = c - 2) {
                    arrayOfTiles[c][r].setBase(5);
                    sixthPlayer.add(arrayOfTiles[c][r]);
                }
                x++;
                y--;
            }
        }
    }
}