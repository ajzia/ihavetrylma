package Client;

import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

    Tile[][] tile;
    Board board;
    GridPane gridPane = new GridPane();

    @BeforeEach
    void setUp() {
        int side = 5;
        int height = side + 3 * (side - 1);
        int width = 2 * (side + 2 * (side - 1)) - 1;
        int numberOfPlayers = 6;

        tile = new Tile[width][height];
        board = new Board(height, width, tile, numberOfPlayers, side, gridPane);
    }

    @Test
    public void countWidthsTest() {
        board.createTiles();
        board.makePieces();

        Assertions.assertNotNull(board.WIDTHS);
        Assertions.assertEquals(13, board.WIDTHS[12]);
    }


    @Test
    public void makeBoardTest() {
        board.createTiles();
        board.makePieces();

        Assertions.assertNotNull(tile[2][10]);

    }

    @Test
    public void makeMoveTest() {
        board.createTiles();
        board.makePieces();

        board.makeMove(4, 10, 6, 10);
        Assertions.assertEquals(4, tile[6][10].getOwner());
    }

}