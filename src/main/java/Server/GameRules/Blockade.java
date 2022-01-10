package Server.GameRules;

import Server.GameTile;

import java.util.ArrayList;

public class Blockade {

    protected final GameTile[][] tiles;
    protected final int width;
    protected final int height;
    protected final int side;
    protected final int pieces;
    protected final int players;
    protected ArrayList<GameTile> blockades = new ArrayList<>();

    public Blockade(GameTile[][] tiles, int width, int height, int side, int pieces, int players) {
        this.tiles = tiles;
        this.width = width;
        this.height = height;
        this.side = side;
        this.pieces = pieces;
        this.players = players;
    }

    public void setBlockades() {
        firstBlockade();
        secondBlockade();
        thirdBlockade();
        fourthBlockade();
        fifthBlockade();
        sixthBlockade();
    }

    private void firstBlockade() {
        int temp = width / 2 - (side - 3);
        for (int i = side - 3; i <= side - 2; i++) {
            for (int j = 0; j <= i; j++) {
                blockades.add(tiles[temp + 2 * j][i]);
            }
            temp--;
        }
    }

    private void secondBlockade() {
        int x = width - 1 - 2 * (side - 2);
        int y = side - 1;
        for (int i = 0; i < side - 1; i++) {
            blockades.add(tiles[x][y]);
            x++;
            y++;
        }

        x = width - 1 - 2 * (side - 3);
        y = side - 1;
        for (int i = 0; i < side - 2; i++) {
            blockades.add(tiles[x][y]);
            x++;
            y++;
        }
    }

    private void thirdBlockade() {
        int x = width - 1 - 2 * (side - 2);
        int y = height - side;
        for (int i = 0; i < side - 1; i++) {
            blockades.add(tiles[x][y]);
            x++;
            y--;
        }

        x = width - 1 - 2 * (side - 3);
        y = height - side;
        for (int i = 0; i < side - 2; i++) {
            blockades.add(tiles[x][y]);
            x++;
            y--;
        }

    }

    private void fourthBlockade() {
        int temp = width / 2 - (side - 3);
        for (int i = height - (side - 2); i > height - side; i--) {
            for (int j = 0; j < height - i; j++) {
                blockades.add(tiles[temp + 2 * j][i]);
            }
            temp--;
        }
    }

    private void fifthBlockade() {
        int x = 2 * (side - 2);
        int y = height - side;
        for (int i = 0; i < side - 1; i++) {
            blockades.add(tiles[x][y]);
            x--;
            y--;
        }

        x = 2 * (side - 3);
        y = height - side;
        for (int i = 0; i < side - 2; i++) {
            blockades.add(tiles[x][y]);
            x--;
            y--;
        }
    }

    private void sixthBlockade() {
        int x = 2 * (side - 2);
        int y = side - 1;
        for (int i = 0; i < side - 1; i++) {
            blockades.add(tiles[x][y]);
            x--;
            y++;
        }

        x = 2 * (side - 3);
        y = side - 1;
        for (int i = 0; i < side - 2; i++) {
            blockades.add(tiles[x][y]);
            x--;
            y++;
        }
    }

    public boolean isBlockade(ArrayList<GameTile> triangle, int owner) {
        int blocks = 2 * side - 3;
        int fields = (owner + 3) % 6;
        int blocked = 0;

        for (int i = blocks * fields; i < blocks * (fields + 1); i++) {
            if (blockades.get(i).getOwner() == blockades.get(i).getBase()) {
                blocked++;
            }
        }

        if (blocked == blocks) {
            int occupied = 0;
            for (GameTile tile : triangle) {
                if (tile.hasOwner()) {
                    occupied++;
                }
            }

            return occupied == pieces;
        }

        return false;
    }

}
