package Server.GameRules;

import Server.GameTile;

import java.util.ArrayList;

/**
 * Class responsible for checking whether a player has won
 */
public class WinningCondition {

    /**
     * Number of player pieces on the board
     */
    private final int pieces;
    /**
     * List of fields of a player's target base
     */
    private final ArrayList<GameTile> pawns;

    /**
     * WinningCondition constructor, setting number of pieces on the board and the player's target base
     * @param pieces number of pieces
     * @param pawns list of fields in the player's target base
     */
    public WinningCondition(int pieces, ArrayList<GameTile> pawns) {
        this.pawns = pawns;
        this.pieces = pieces;
    }

    /**
     * Method checking if the player won the game
     * @return 0 - the player did not win, 1 - the player won
     */
    public boolean ifWon() {
        int inBase = 0;

        for (GameTile p : pawns) {
            if (p.getOwner() == p.getBase()) {
                inBase++;
            }
        }

        return inBase == pieces;
    }

}
