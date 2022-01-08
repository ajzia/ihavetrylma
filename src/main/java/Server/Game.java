package Server;

import java.util.ArrayList;

public class Game {

    public int goalPlayers = 0;

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

    public boolean moveValidation(String command) {
        String[] move = command.split(" ");
        int x1 = Integer.parseInt(move[1]);
        int y1 = Integer.parseInt(move[2]);
        int x2 = Integer.parseInt(move[3]);
        int y2 = Integer.parseInt(move[4]);

        return gameBoard.makeMove(x1, y1, x2, y2);
    }

    public void sendToAll(String command) {
        for (Player p : players) {
            p.sendMessage(command);
        }
    }
}
