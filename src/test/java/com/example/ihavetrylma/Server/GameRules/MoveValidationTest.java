package com.example.ihavetrylma.Server.GameRules;

import com.example.ihavetrylma.Server.GameBoard;
import com.example.ihavetrylma.Server.GameTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveValidationTest {

    MoveValidation mov;
    GameBoard gameBoard;
    GameTile[][] tile;

    @BeforeEach
    void setUp() {
        int side = 5;
        int height = side + 3 * (side - 1);
        int width = 2 * (side + 2 * (side - 1)) - 1;
        int goalPlayers = 3;

        tile = new GameTile[width][height];
        gameBoard = new GameBoard(width, height, tile, goalPlayers, side);
    }

    @Test
    public void isValidTest() {
        mov = new MoveValidation(5, 5, 7, 5, 5, -1, tile);
        Assertions.assertEquals(2, mov.isValid());
    }

}
