package Server;

import java.util.ArrayList;

public class Blockade {

    protected final GameTile[][] tiles;
    protected final int width;
    protected final int height;
    protected final int side;
    protected final int players;

    protected Blockade(GameTile[][] tiles, int width, int height, int side, int players) {
        this.tiles = tiles;
        this.width = width;
        this.height = height;
        this.side = side;
        this.players = players;
    }
    
    protected ArrayList<GameTile> setBlockades(ArrayList<GameTile> blockades) {

        switch (players) {
            case 2 -> {
                firstBlockade(blockades);
                fourthBlockade(blockades);
            }
            case 3 -> {
                secondBlockade(blockades);
                fourthBlockade(blockades);
                sixthBlockade(blockades);
            }
            case 4 -> {
                secondBlockade(blockades);
                thirdBlockade(blockades);
                fifthBlockade(blockades);
                sixthBlockade(blockades);
            }
            case 6 -> {
                firstBlockade(blockades);
                secondBlockade(blockades);
                thirdBlockade(blockades);
                fourthBlockade(blockades);
                fifthBlockade(blockades);
                sixthBlockade(blockades);
            }
        }

        return blockades;
    }

    private void firstBlockade(ArrayList<GameTile> blockades) {
        int temp = width / 2 - (side - 3);
        for (int i = side - 3; i <= side - 2; i++) {
            for (int j = 0; j <= i; j ++) {
                blockades.add(tiles[temp + 2 * j][i]);
            }
            temp--;
        }
    }

    private void secondBlockade(ArrayList<GameTile> blockades) {
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

    private void thirdBlockade(ArrayList<GameTile> blockades) {
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

    private void fourthBlockade(ArrayList<GameTile> blockades) {
        int temp = width / 2 - (side - 3);
        for (int i = height - (side - 2); i > height - side; i--) {
            for (int j = 0; j < height - i; j ++) {
                blockades.add(tiles[temp + 2 * j][i]);
            }
            temp--;
        }
    }

    private void fifthBlockade(ArrayList<GameTile> blockades) {
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

    private void sixthBlockade(ArrayList<GameTile> blockades) {
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

}
