package Server;

import Server.GameRules.Blockade;
import Server.GameRules.MoveValidation;
import Server.GameRules.WinningCondition;
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
    private final int side;
    private final int height, width;
    private final GameTile[][] arrayOfTiles;

    protected int jumpColumn = 0;
    protected int jumpRow = 0;
    protected boolean previousJump = false;
    private Blockade blockades;

    protected GameBoard(int width, int height, GameTile[][] arrayOfTiles, int numberOfPlayers, int side) {
        this.height = height;
        this.width = width;
        this.arrayOfTiles = arrayOfTiles;
        this.numberOfPlayers = numberOfPlayers;
        this.side = side;

        createTiles();
        makePieces();
    }

    // Width of each row of the triangle
    private static final int[] WIDTHS = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10//, 11, 12, 13
    };

    private void createTiles() {
        int temp = WIDTHS[WIDTHS.length - 2];
        GameTile tile;

        //create triangle of tiles
        for (int i = 0; i < WIDTHS[WIDTHS.length - 1]; i++) {
            temp = temp - i;
            for (int j = 0; j < WIDTHS[i]; j++) {
                tile = new GameTile(temp, i, this, side);
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
                    tile = new GameTile(temp, i, this, side);
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
            case 2 -> {
                setFirstPlayer();
                setFourthPlayer();
            }
            case 3 -> {
                setSecondPlayer();
                setFourthPlayer();
                setSixthPlayer();
            }
            case 4 -> {
                setSecondPlayer();
                setThirdPlayer();
                setFifthPlayer();
                setSixthPlayer();
            }
            case 6 -> {
                setFirstPlayer();
                setSecondPlayer();
                setThirdPlayer();
                setFourthPlayer();
                setFifthPlayer();
                setSixthPlayer();
            }
            default -> System.out.println("Wrong number of players");
        }
    }

    private int howManyPieces() {
        int pieces = 0;
        for (int i = 1; i < side; i++) {
            pieces += i;
        }
        return pieces;
    }

    private void addPiece(int column, int row, int owner) {
        arrayOfTiles[column][row].setOwner(owner);
    }

    private void removePiece(int column, int row) {
        arrayOfTiles[column][row].setOwner(-1);
    }

    protected int makeMove(int oldColumn, int oldRow, int newColumn, int newRow) {
        MoveValidation moveVal;

        int oldOwner = arrayOfTiles[oldColumn][oldRow].getOwner();
        int newOwner = arrayOfTiles[newColumn][newRow].getOwner();

        if (jumpColumn == 0 && jumpRow == 0) {
            moveVal = new MoveValidation(oldColumn, oldRow, newColumn, newRow, oldOwner, newOwner, arrayOfTiles);
            previousJump = false;
        } else {
            moveVal = new MoveValidation(jumpColumn, jumpRow, newColumn, newRow, oldOwner, newOwner, arrayOfTiles);
        }

        int move = moveVal.isValid();
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
            return moveVal.isValid() == 1 ? 1 : 2;
        }
        return 0;
    }

    protected void resetJump() {
        jumpColumn = 0;
        jumpRow = 0;
    }

    protected int isThisTheEnd(int color) {
        switch (color) {
            case 0 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), firstPlayer);
                return win.ifWon() ? 1 : 0;
            }
            case 1 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), secondPlayer);
                return win.ifWon() ? 1 : 0;
            }
            case 2 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), thirdPlayer);
                return win.ifWon() ? 1 : 0;
            }
            case 3 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), fourthPlayer);
                return win.ifWon() ? 1 : 0;
            }
            case 4 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), fifthPlayer);
                return win.ifWon() ? 1 : 0;
            }
            case 5 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), sixthPlayer);
                return win.ifWon() ? 1 : 0;
            }
            default -> {
                return 0;
            }
        }
    }

    protected void makeBlockades() {
        blockades = new Blockade(arrayOfTiles, width, height, side, howManyPieces(), numberOfPlayers);
        blockades.setBlockades();
    }

    protected boolean checkBlockade(int owner) {
        switch (owner) {
            case 0 -> {
                return blockades.isBlockade(firstPlayer, owner);
            }
            case 1 -> {
                return blockades.isBlockade(secondPlayer, owner);
            }
            case 2 -> {
                return blockades.isBlockade(thirdPlayer, owner);
            }
            case 3 -> {
                return blockades.isBlockade(fourthPlayer, owner);
            }
            case 4 -> {
                return blockades.isBlockade(fifthPlayer, owner);
            }
            case 5 -> {
                return blockades.isBlockade(sixthPlayer, owner);
            }
            default -> {
                return false;
            }
        }
    }

    private void setFirstPlayer() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = 0; row < side - 1; row++) {
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
        int temp = width - ((side - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = side - 1; row < (side - 1) * 2; row++) {
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
            int x = (side - 1) * 2;
            int y = 0;
            for (int r = height - side; r > (side - 1) * 2; r--) {
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
        int temp = width - ((side - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = height - side; row > (side - 1) * 2; row--) {
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
        for (int row = height - 1; row > height - 1 - (side - 1); row--) {
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
            for (int r = 0; r < side - 1; r++) {
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
        int temp = (side - 1) * 2;
        int temp2 = 0;
        for (int row = height - side; row > (side - 1) * 2; row--) {
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
        int temp = (side - 1) * 2;
        int temp2 = 0;
        for (int row = side - 1; row < (side - 1) * 2; row++) {
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
            int x = width - ((side - 1) * 2 + 1);
            int y = width - 1;
            for (int r = height - side; r > (side - 1) * 2; r--) {
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