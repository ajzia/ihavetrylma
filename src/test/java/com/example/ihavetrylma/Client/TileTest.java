package com.example.ihavetrylma.Client;

import javafx.scene.paint.Color;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TileTest {

    @Test
    public void correctColor() {
        Tile tile = new Tile(13, 1, null, 4);
        tile.setOwner(0);
        Assertions.assertEquals(Color.web("#FCC6F6"), tile.getColor());
    }

    @Test
    public void correctRadiusTest() {
        Tile tile = new Tile(12, 0, null, 4);
        Assertions.assertEquals(20, tile.getRadius());

        tile = new Tile(12, 0, null, 5);
        Assertions.assertEquals(15, tile.getRadius());

        tile = new Tile(12, 0, null, 6);
        Assertions.assertEquals(12, tile.getRadius());
    }

    @Test
    public void hasOwnerTest() {
        Tile tile = new Tile(12, 0, null, 4);
        Assertions.assertFalse(tile.hasOwner());
    }

}