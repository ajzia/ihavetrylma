package Server;

import java.util.ArrayList;

public class Game {

    int goalPlayers = 0;

    private static final int side = 5;
    private static final int height = 17;
    private static final int width = 26;

    GameBoard gameBoard;

    ArrayList<Player> players = new ArrayList<>();

    public void setGoalPlayers(int goalPlayers) {
        this.goalPlayers = goalPlayers;
    }

    public int getGoalPlayers() {
        return goalPlayers;
    }

    public int currentPlayers() {
        return players.size();
    }

    public void makeBoard() {
        GameTile[][] tile = new GameTile[width][height];
        gameBoard = new GameBoard(width, height, tile, goalPlayers, side);
        gameBoard.createTiles();
        gameBoard.makePieces();
    }

    public void sendToAll(String command) {
        for (Player p : players) {
            p.sendMessage(command);
        }
    }
}