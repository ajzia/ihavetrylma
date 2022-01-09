package Server;

import java.util.ArrayList;

public class WinningCondition {

    private final int side;
    private final ArrayList<GameTile> pawns;

    protected WinningCondition(int side, ArrayList<GameTile> pawns) {
        this.side = side;
        this.pawns = pawns;

        for (GameTile p: pawns) {
            System.out.println(p);
        }
    }

    protected int howManyPawns() {
        int pieces = 0;
        for (int i = 1; i < side; i++) {
            pieces += i;
        }
        return pieces;
    }

    protected boolean ifWon() {
        int inBase = 0;

        for (GameTile p : pawns) {
            if (p.getOwner() == p.getBase()) {
                inBase++;
            }
        }

        System.out.println("sprawdzamy");
        return inBase == howManyPawns();
    }
}
