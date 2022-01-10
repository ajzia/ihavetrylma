package Server;

import Client.Board;
import Server.GameRules.Blockade;
import Server.GameRules.MoveValidation;
import Server.GameRules.WinningCondition;

import java.util.ArrayList;

public class GameBoard extends Board {

    protected ArrayList<GameTile> baseForFourth = new ArrayList<>();
    protected ArrayList<GameTile> baseForFifth = new ArrayList<>();
    protected ArrayList<GameTile> baseForSixth = new ArrayList<>();
    protected ArrayList<GameTile> baseForFirst = new ArrayList<>();
    protected ArrayList<GameTile> baseForSecond = new ArrayList<>();
    protected ArrayList<GameTile> baseForThird = new ArrayList<>();

    protected int jumpColumn = 0;
    protected int jumpRow = 0;
    protected boolean previousJump = false;
    private Blockade blockades;
    private final GameTile[][] arrayOfTiles;

    public GameBoard(int height, int width, GameTile[][] arrayOfTiles, int numberOfPlayers, int sideLength) {
        super(height, width, arrayOfTiles, numberOfPlayers, sideLength, null);
        this.arrayOfTiles = arrayOfTiles;
        createTiles();
        makePieces();
        makeBases();
        makeBlockades();
    }

    protected void createTiles() {
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

    private int howManyPieces() {
        int pieces = 0;
        for (int i = 1; i < sideLength; i++) {
            pieces += i;
        }
        return pieces;
    }

    protected int makeMoves(int oldColumn, int oldRow, int newColumn, int newRow) {
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
                WinningCondition win = new WinningCondition(howManyPieces(), baseForFourth);
                return win.ifWon() ? 1 : 0;
            }
            case 1 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), baseForFifth);
                return win.ifWon() ? 1 : 0;
            }
            case 2 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), baseForSixth);
                return win.ifWon() ? 1 : 0;
            }
            case 3 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), baseForFirst);
                return win.ifWon() ? 1 : 0;
            }
            case 4 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), baseForSecond);
                return win.ifWon() ? 1 : 0;
            }
            case 5 -> {
                WinningCondition win = new WinningCondition(howManyPieces(), baseForThird);
                return win.ifWon() ? 1 : 0;
            }
            default -> {
                return 0;
            }
        }
    }

    protected void makeBlockades() {
        blockades = new Blockade(arrayOfTiles, width, height, sideLength, howManyPieces(), numberOfPlayers);
        blockades.setBlockades();
    }

    protected boolean checkBlockade(int owner) {
        switch (owner) {
            case 0 -> {
                return blockades.isBlockade(baseForFourth, owner);
            }
            case 1 -> {
                return blockades.isBlockade(baseForFifth, owner);
            }
            case 2 -> {
                return blockades.isBlockade(baseForSixth, owner);
            }
            case 3 -> {
                return blockades.isBlockade(baseForFirst, owner);
            }
            case 4 -> {
                return blockades.isBlockade(baseForSecond, owner);
            }
            case 5 -> {
                return blockades.isBlockade(baseForThird, owner);
            }
            default -> {
                return false;
            }
        }
    }

    private void makeBases() {
        setFirstBase();
        setSecondBase();
        setThirdBase();
        setFourthBase();
        setFifthBase();
        setSixthBase();
    }

    private void setFirstBase() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = height - 1; row > height - 1 - (sideLength - 1); row--) {
            for (int column = temp2; column <= temp; column = column + 2) {
                arrayOfTiles[column][row].setBase(0);
                baseForFirst.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    private void setSecondBase() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for (int row = height - sideLength; row > (sideLength - 1) * 2; row--) {
            for (int column = temp2; column < temp; column = column + 2) {
                arrayOfTiles[column][row].setBase(1);
                baseForSecond.add(arrayOfTiles[column][row]);
            }
            temp--;
            temp2++;
        }
    }

    private void setThirdBase() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for (int row = sideLength - 1; row < (sideLength - 1) * 2; row++) {
            for (int column = temp2; column < temp; column = column + 2) {
                arrayOfTiles[column][row].setBase(2);
                baseForThird.add(arrayOfTiles[column][row]);

            }
            temp--;
            temp2++;
        }
    }

    private void setFourthBase() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = 0; row < sideLength - 1; row++) {
            for (int column = temp2; column <= temp; column = column + 2) {
                arrayOfTiles[column][row].setBase(3);
                baseForFourth.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    private void setFifthBase() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = sideLength - 1; row < (sideLength - 1) * 2; row++) {
            for (int column = temp2; column > temp; column = column - 2) {
                arrayOfTiles[column][row].setBase(4);
                baseForFifth.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    private void setSixthBase() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = height - sideLength; row > (sideLength - 1) * 2; row--) {
            for (int column = temp2; column > temp; column = column - 2) {
                arrayOfTiles[column][row].setBase(5);
                baseForSixth.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    private ArrayList<GameTile> getPlayerBases(int owner) {
        switch (owner) {
            case 0 -> {
                return baseForFirst;
            }
            case 1 -> {
                return baseForSecond;
            }
            case 2 -> {
                return baseForThird;
            }
            case 3 -> {
                return baseForFourth;
            }
            case 4 -> {
                return baseForFifth;
            }
            case 5 -> {
                return baseForSixth;
            }
            default -> {
                return null;
            }
        }
    }

}