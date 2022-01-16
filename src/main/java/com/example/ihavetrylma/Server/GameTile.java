package com.example.ihavetrylma.Server;

import com.example.ihavetrylma.Client.Tile;

/**
 * Class containing information about tiles
 */
public class GameTile extends Tile {

    /**
     * Field informing about whether it is part of some player's target base
     */
    private int base = -1;

    /**
     * Creating a tile
     * @param column column in an array
     * @param row row in an array
     * @param board game board
     * @param sideLength side length of a hexagon
     */
    public GameTile(int column, int row, GameBoard board, int sideLength) {
        super(column, row, board, sideLength);
    }

    /**
     * Setting field as a base for some player
     * @param owner player's id
     */
    public void setBase(int owner){
        this.base = owner;
    }

    /**
     * Getting information about whether the field is a part of player's target base
     * @return base
     */
    public int getBase(){
        return base;
    }

}