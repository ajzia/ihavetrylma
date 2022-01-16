package com.example.ihavetrylma.Server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTest {

    GameBoard gameBoard;
    GameTile[][] tile;

    @BeforeEach
    void setUp() {
        int side = 5;
        int height = side + 3 * (side - 1);
        int width = 2 * (side + 2 * (side - 1)) - 1;
        int goalPlayers = 2;

        tile = new GameTile[width][height];

        gameBoard = new GameBoard(width, height, tile, goalPlayers, side);
    }

    @Test
    public void resetJumpTest() {
        gameBoard.resetJump();
        Assertions.assertEquals(0, gameBoard.jumpColumn);
        Assertions.assertEquals(0, gameBoard.jumpRow);
    }

    @Test
    public void makeMoveTest() {
        gameBoard.makeMoves(13, 3, 14, 4);

        Assertions.assertEquals(0, tile[14][4].getOwner());

        gameBoard.makeMoves(11, 1, 13, 3);
        gameBoard.makeMoves(13, 3, 15, 5);

        Assertions.assertEquals(0, tile[15][5].getOwner());

        gameBoard.makeMoves(15, 5, 16, 6);
        Assertions.assertEquals(-1, tile[16][6].getOwner());
    }
}
