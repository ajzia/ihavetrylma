package Server.GameRules;

import Server.GameTile;

import java.util.ArrayList;

/**
 * Class responsible for checking whether there is a blockade
 */
public class Blockade {

    /**
     * Array containing every tile
     */
    protected final GameTile[][] tiles;
    /**
     * Maximum number of tiles in a column
     */
    protected final int width;
    /**
     * Maximum number of tiles in a column
     */
    protected final int height;
    /**
     * Side length of a hexagon
     */
    private final int side;
    /**
     * How many pieces each player has
     */
    private final int pieces;
    /**
     * Number of players in the game
     */
    protected final int players;
    /**
     * List of every blocking fields
     */
    protected ArrayList<GameTile> blockades = new ArrayList<>();

    /**
     * Blockade constructor
     * @param tiles array containing every tile
     * @param width maximum number of tiles in a row
     * @param height maximum number of tiles in a column
     * @param side side length of a hexagon
     * @param pieces how many pieces each player has
     * @param players number of players in the game
     */
    public Blockade(GameTile[][] tiles, int width, int height, int side, int pieces, int players) {
        this.tiles = tiles;
        this.width = width;
        this.height = height;
        this.side = side;
        this.pieces = pieces;
        this.players = players;
    }

    /**
     * Calls methods responsible for making blockades
     */
    public void setBlockades() {
        firstBlockade();
        secondBlockade();
        thirdBlockade();
        fourthBlockade();
        fifthBlockade();
        sixthBlockade();
    }

    /**
     * Adds blocking fields from the top triangle to the blockades list
     */
    private void firstBlockade() {
        int temp = width / 2 - (side - 3);
        for (int i = side - 3; i <= side - 2; i++) {
            for (int j = 0; j <= i; j++) {
                blockades.add(tiles[temp + 2 * j][i]);
            }
            temp--;
        }
    }

    /**
     * Adds blocking fields from the top right to the blockades list
     */
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

    /**
     * Adds blocking fields from the bottom right triangle to the blockades list
     */
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

    /**
     * Adds blocking fields from the bottom triangle to the blockades list
     */
    private void fourthBlockade() {
        int temp = width / 2 - (side - 3);
        for (int i = height - (side - 2); i > height - side; i--) {
            for (int j = 0; j < height - i; j++) {
                blockades.add(tiles[temp + 2 * j][i]);
            }
            temp--;
        }
    }

    /**
     * Adds blocking fields from the bottom left triangle to the blockades list
     */
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

    /**
     * Adds blocking fields from the top left triangle to the blockades list
     */
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

    /**
     * Checking if there is a blockade on the board
     * @return true / false
     */
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