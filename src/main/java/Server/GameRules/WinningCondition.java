package Server.GameRules;

import Server.GameTile;

import java.util.ArrayList;

public class WinningCondition {

    private final int pieces;
    private final ArrayList<GameTile> pawns;

    public WinningCondition(int pieces, ArrayList<GameTile> pawns) {
        this.pawns = pawns;
        this.pieces = pieces;
    }

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
