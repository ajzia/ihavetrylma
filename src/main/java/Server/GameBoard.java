package Server;

import Client.Board;
import Server.GameRules.Blockade;
import Server.GameRules.MoveValidation;
import Server.GameRules.WinningCondition;

import java.util.ArrayList;

/**
 * Server's imitation of clients' boards, it's used for rule-checking
 */
public class GameBoard extends Board {

    /**
     * List of players' target bases
     */
    protected ArrayList<GameTile> bases = new ArrayList<>();

    /**
     * Array of board's tiles
     */
    protected GameTile[][] arrayOfTiles;

    /**
     * Column of the piece that has previously jumped in the same turn
     */
    protected int jumpColumn = 0;
    /**
     * Row of the piece that has previously jumped in the same turn
     */
    protected int jumpRow = 0;

    /**
     * Field holding an information about whether previous move was a jump
     */
    protected boolean previousJump = false;

    /**
     * Object holding information about blockade fields
     */
    private Blockade blockades;

    /**
     * GameBoard constructor, makes fields and pieces, sets blockade and target base tiles
     * @param height - maximum number of tiles in a column
     * @param width - maximum number of tiles in a row
     * @param arrayOfTiles - array containing every tile
     * @param numberOfPlayers - number of players that play the game
     * @param sideLength - side length of a hexagon
     */
    public GameBoard(int width, int height, GameTile[][] arrayOfTiles, int numberOfPlayers, int sideLength) {
        super(height, width, arrayOfTiles, numberOfPlayers, sideLength, null);
        this.arrayOfTiles = arrayOfTiles;
        createTiles();
        makePieces();
        makeBlockades();
        makeBases();
    }

    /**
     * Creating fields on the board
     */
    protected void createTiles() {
        int temp = WIDTHS[WIDTHS.length - 2];
        GameTile tile;

        // creating triangle of tiles
        for (int i = 0; i < WIDTHS[WIDTHS.length - 1]; i++) {
            temp = temp - i;
            for (int j = 0; j < WIDTHS[i]; j++) {
                tile = new GameTile(temp, i, this, sideLength);
                arrayOfTiles[temp][i] = tile;
                temp = temp + 2;
            }
            temp = WIDTHS[WIDTHS.length - 2];
        }

        // creating inverted triangle of tiles
        int temp2 = 0;
        for (int i = height - 1; i > height - 1 - WIDTHS[WIDTHS.length - 1]; i--) {
            temp = temp - temp2;
            for (int j = 0; j < WIDTHS[temp2]; j++) {
                // checking whether tile already exists
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

    /**
     * Counting how many pieces each player has
     * @return sum of all pieces
     */
    private int howManyPieces() {
        int pieces = 0;
        for (int i = 1; i < sideLength; i++) {
            pieces += i;
        }
        return pieces;
    }

    /**
     * Making a move if it is valid
     * @param oldColumn column of player's first click
     * @param oldRow row of player's first click
     * @param newColumn column of player's second click
     * @param newRow row of player's second click
     * @return what move player made
     */
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

    /**
     * Resetting jumpColumn and jumpRow
     */
    protected void resetJump() {
        jumpColumn = 0;
        jumpRow = 0;
    }

    /**
     * Returns whether the player won the game
     * @param owner player id
     * @return 0 - the player did not win, 1 - the player won
     */
    protected int isThisTheEnd(int owner) {
        WinningCondition win = new WinningCondition(howManyPieces(), getBase(owner));
        return win.ifWon() ? 1 : 0;
    }

    /**
     * Method calling to set blockade fields
     */
    protected void makeBlockades() {
        blockades = new Blockade(arrayOfTiles, width, height, sideLength, howManyPieces(), numberOfPlayers);
        blockades.setBlockades();
    }

    /**
     * Returns whether there is a blockade on the board
     * @param owner player's id
     * @return true / false
     */
    protected boolean checkBlockade(int owner) {
        return blockades.isBlockade(getBase(owner), owner);
    }

    /**
     * Calls methods responsible for making target bases
     */
    private void makeBases() {
        setFirstBase();
        setSecondBase();
        setThirdBase();
        setFourthBase();
        setFifthBase();
        setSixthBase();
    }

    /**
     * Setting base for the first player
     */
    private void setFirstBase() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = height - 1; row > height - 1 - (sideLength - 1); row--) {
            for (int column = temp2; column <= temp; column = column + 2) {
                arrayOfTiles[column][row].setBase(0);
                bases.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    /**
     * Setting base for the second player
     */
    private void setSecondBase() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for (int row = height - sideLength; row > (sideLength - 1) * 2; row--) {
            for (int column = temp2; column < temp; column = column + 2) {
                arrayOfTiles[column][row].setBase(1);
                bases.add(arrayOfTiles[column][row]);
            }
            temp--;
            temp2++;
        }
    }

    /**
     * Setting base for the third player
     */
    private void setThirdBase() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for (int row = sideLength - 1; row < (sideLength - 1) * 2; row++) {
            for (int column = temp2; column < temp; column = column + 2) {
                arrayOfTiles[column][row].setBase(2);
                bases.add(arrayOfTiles[column][row]);
            }
            temp--;
            temp2++;
        }
    }

    /**
     * Setting base for the fourth player
     */
    private void setFourthBase() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = 0; row < sideLength - 1; row++) {
            for (int column = temp2; column <= temp; column = column + 2) {
                arrayOfTiles[column][row].setBase(3);
                bases.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    /**
     * Setting base for the fifth player
     */
    private void setFifthBase() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = sideLength - 1; row < (sideLength - 1) * 2; row++) {
            for (int column = temp2; column > temp; column = column - 2) {
                arrayOfTiles[column][row].setBase(4);
                bases.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    /**
     * Setting base for the sixth player
     */
    private void setSixthBase() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = height - sideLength; row > (sideLength - 1) * 2; row--) {
            for (int column = temp2; column > temp; column = column - 2) {
                arrayOfTiles[column][row].setBase(5);
                bases.add(arrayOfTiles[column][row]);
            }
            temp++;
            temp2--;
        }
    }

    /**
     * Method returning target base for a specific player
     * @param owner player id
     * @return target base
     */
    private ArrayList<GameTile> getBase(int owner) {
        ArrayList<GameTile> base = new ArrayList<>();
        for (int i = owner * howManyPieces(); i < (owner + 1) * howManyPieces(); i++) {
            base.add(bases.get(i));
        }
        return base;
    }

}