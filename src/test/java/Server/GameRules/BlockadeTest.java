package Server.GameRules;

import Client.Tile;
import Server.GameBoard;
import Server.GameTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.ArrayList;

public class BlockadeTest {

    GameBoard gameBoard;

    @Test
    public void isBlockadeTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int side = 5;
        int height = side + 3 * (side - 1);
        int width = 2 * (side + 2 * (side - 1)) - 1;
        int goalPlayers = 3;

        GameTile[][] tile = new GameTile[width][height];

        gameBoard = new GameBoard(height, width, tile, goalPlayers, side);

        Method method1 = GameBoard.class.getDeclaredMethod("checkBlockade", int.class);
        method1.setAccessible(true);
        boolean result = (boolean) method1.invoke(gameBoard, 3);
        Assertions.assertFalse(result);

        Method method2 = Tile.class.getDeclaredMethod("setOwner", int.class);
        method2.setAccessible(true);

        Method method3 = GameBoard.class.getDeclaredMethod("getPlayerBases", int.class);
        method3.setAccessible(true);
        ArrayList<GameTile> fourth = (ArrayList<GameTile>) method3.invoke(gameBoard, 3);

        for (GameTile f: fourth) {
            method2.invoke(f, 3);
        }

        for (int i = 0; i < 3; i++) {
            method2.invoke(fourth.get(i), 2);
        }

        result = (boolean) method1.invoke(gameBoard, 3);
        Assertions.assertTrue(result);
    }

}
