package Server;

import Client.Tile;

public class GameTile extends Tile {

    int base = -1;

    public GameTile(int column, int row, GameBoard board, int sideLength) {
        super(column, row, board, sideLength);
    }

    public void setBase(int owner){
        this.base = owner;
    }

    public int getBase(){
        return base;
    }

}