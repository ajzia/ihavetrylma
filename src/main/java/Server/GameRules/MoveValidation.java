package Server.GameRules;
import Server.GameTile;

public class MoveValidation {

    private final int oldColumn;
    private final int oldRow;
    private final int newColumn;
    private final int newRow;
    private final int oldOwner;
    private final int newOwner;
    private final GameTile[][] arrayOfTiles;

    public MoveValidation(int oldColumn, int oldRow, int newColumn, int newRow, int oldOwner, int newOwner, GameTile[][] arrayOfTiles) {
        this.oldColumn = oldColumn;
        this.oldRow = oldRow;
        this.newColumn = newColumn;
        this.newRow = newRow;
        this.oldOwner = oldOwner;
        this.newOwner = newOwner;
        this.arrayOfTiles = arrayOfTiles;
    }

    // 0 - invalid move
    // 1 - jump
    // 2 - valid move
    public int isValid() {
        if (yourPiece()) {               // is it your piece? if yes - it's not a valid move
            return 0;
        } else if (!emptyTile()) {      // is this tile empty? if not - it's not a valid move
            return 0;
        } else if (jump()) { // is this move to one of the neighbouring tiles? if not - it's not a valid move
            return 1;
        } else if (isGood()) {
            return 2;
        } else return 0;
    }

    private boolean yourPiece() {
        return oldOwner == newOwner;
    }

    private boolean emptyTile() {
        return newOwner == -1;
    }

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

    private boolean ifInBase() {
        int oldBase = arrayOfTiles[oldColumn][oldRow].getBase();
        int newBase = arrayOfTiles[newColumn][newRow].getBase();

        return oldOwner == oldBase && oldBase != newBase;
    }

}