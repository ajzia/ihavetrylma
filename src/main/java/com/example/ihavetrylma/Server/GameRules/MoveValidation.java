package com.example.ihavetrylma.Server.GameRules;
import com.example.ihavetrylma.Server.GameTile;

/**
 * Class responsible for validation every players' move
 */
public class MoveValidation {

    /**
     * column of player's first click
     */
    private final int oldColumn;
    /**
     * row of player's first click
     */
    private final int oldRow;
    /**
     * column of player's second click
     */
    private final int newColumn;
    /**
     * row of player's second click
     */
    private final int newRow;
    /**
     * tile owner of the player's first click
     */
    private final int oldOwner;
    /**
     * tile owner of the player's second click
     */
    private final int newOwner;
    /**
     * Array of gameboard's tiles
     */
    private final GameTile[][] arrayOfTiles;

    /**
     * MoveValidation constructor, setting coordinates and tile owners of the player's possible move
     * @param oldColumn column of player's first click
     * @param oldRow row of player's first click
     * @param newColumn column of player's second click
     * @param newRow row of player's second click
     * @param oldOwner tile owner of the player's first click
     * @param newOwner tile owner of the player's second click
     * @param arrayOfTiles - array containing every tile
     */
    public MoveValidation(int oldColumn, int oldRow, int newColumn, int newRow, int oldOwner, int newOwner, GameTile[][] arrayOfTiles) {
        this.oldColumn = oldColumn;
        this.oldRow = oldRow;
        this.newColumn = newColumn;
        this.newRow = newRow;
        this.oldOwner = oldOwner;
        this.newOwner = newOwner;
        this.arrayOfTiles = arrayOfTiles;
    }

    /**
     * Method checking if player's move is valid
     * @return 0 - invalid move, 1 - jump, 2 - valid move
     */
    public int isValid() {
        if (yourPiece()) {
            return 0;
        } else if (!emptyTile()) {
            return 0;
        } else if (jump()) {
            return 1;
        } else if (isGood()) {
            return 2;
        } else return 0;
    }

    /**
     * Method checking if a player's trying to move his/hers piece to the place where he/she already has a piece
     * @return true / false
     */
    private boolean yourPiece() {
        return oldOwner == newOwner;
    }

    /**
     * Method checking if the tile, that player wants to move his/hers piece to, is empty
     * @return true / false
     */
    private boolean emptyTile() {
        return newOwner == -1;
    }

    /**
     * Method checking if the move was a jump
     * @return true / false
     */
    private boolean jump() {
        if(ifInBase()) {
            return false;
        }

        int column = newColumn - oldColumn;
        int row = newRow - oldRow;

        if ((Math.abs(column) == 4 && Math.abs(row) == 0) || (Math.abs(column) == 2 && Math.abs(row) == 2)) {
            if (column == -4 && row == 0 && arrayOfTiles[oldColumn - 2][oldRow].hasOwner()) {
                return true;
            } else if (column == 4 && row == 0 && arrayOfTiles[oldColumn + 2][oldRow].hasOwner()) {
                return true;
            } else if (column == -2 && row == -2 && arrayOfTiles[oldColumn - 1][oldRow - 1].hasOwner()) {
                return true;
            } else if (column == 2 && row == -2 && arrayOfTiles[oldColumn + 1][oldRow - 1].hasOwner()) {
                return true;
            } else if (column == -2 && row == 2 && arrayOfTiles[oldColumn - 1][oldRow + 1].hasOwner()) {
                return true;
            } else return column == 2 && row == 2 && arrayOfTiles[oldColumn + 1][oldRow + 1].hasOwner();
        }

        return false;
    }

    /**
     * Method checking if it was a normal move
     * @return true / false
     */
    private boolean isGood() {
        if(ifInBase()) {
            return false;
        }

        int column = newColumn - oldColumn;
        int row = newRow - oldRow;
        if (Math.abs(column) == 2 && row == 0) {
            return true;
        } else return Math.abs(row) == 1 && Math.abs(column) == 1;
    }

    /**
     * Method checking if the piece is in its target base
     * @return true / false
     */
    private boolean ifInBase() {
        int oldBase = arrayOfTiles[oldColumn][oldRow].getBase();
        int newBase = arrayOfTiles[newColumn][newRow].getBase();

        return oldOwner == oldBase && oldBase != newBase;
    }

}