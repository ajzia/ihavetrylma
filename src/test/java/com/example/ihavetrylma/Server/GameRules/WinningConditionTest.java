package com.example.ihavetrylma.Server.GameRules;

import com.example.ihavetrylma.Client.Tile;
import com.example.ihavetrylma.Server.GameBoard;
import com.example.ihavetrylma.Server.GameTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class WinningConditionTest {

  @Test
  public void ifWonTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    int side = 5;
    int height = side + 3 * (side - 1);
    int width = 2 * (side + 2 * (side - 1)) - 1;
    int goalPlayers = 2;

    GameTile[][] tile = new GameTile[width][height];

    GameBoard gameBoard = new GameBoard(width, height, tile, goalPlayers, side);

    Method method = GameBoard.class.getDeclaredMethod("isThisTheEnd", int.class);
    method.setAccessible(true);

    Method method1 = Tile.class.getDeclaredMethod("setOwner", int.class);
    method1.setAccessible(true);

    Method method3 = GameBoard.class.getDeclaredMethod("getBase", int.class);
    method3.setAccessible(true);

    ArrayList<GameTile> first = (ArrayList<GameTile>) method3.invoke(gameBoard, 0);

    for (GameTile f: first) {
      method1.invoke(f, 0);
    }

    int result = (int) method.invoke(gameBoard, 0);
    Assertions.assertEquals(result, 1);
  }
}
