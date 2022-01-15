package Server;

import Client.BoardGUI;

import java.util.ArrayList;

/**
 * Main game class.
 */
public class Game {

    /**
     * Number of players in the game.
     */
    private int goalPlayers = 0;

    /**
     * Current player.
     */
    protected int current;

    /**
     * Side length of the hexagon.
     */
    private static final int side = BoardGUI.getSide();
    /**
     * maximum number of tiles in a column.
     */
    private static final int height = side + 3 * (side - 1);
    /**
     * Maximum number of tiles in a row.
     */
    private static final int width = 2 * (side + 2 * (side - 1)) - 1;

    /**
     * Imitation of the players' boards.
     */
    protected GameBoard gameBoard;

    /**
     * List of every player in the game.
     */
    protected ArrayList<Player> players = new ArrayList<>();

    /**
     * Setting number of players in the game.
     * @param goalPlayers number of players in the game
     */
    protected void setGoalPlayers(int goalPlayers) {
        this.goalPlayers = goalPlayers;
    }

    /**
     * Getting number of players in the game.
     * @return number of players in the game
     */
    protected int getGoalPlayers() {
        return goalPlayers;
    }

    /**
     * Getting number of players.
     * @return number of players
     */
    protected int currentPlayers() {
        return players.size();
    }

    /**
     * Getting a player from the list based on his/hers id.
     * @param id player's id
     * @return player with the specific id
     */
    private Player getPlayer(int id) {
        return players.get(id);
    }

    /**
     * Assigning colors to the players according to the goal for the player number.
     */
    protected void assignColors() {
        int size = currentPlayers();
        switch (goalPlayers) {
            case 2 -> {
                for (int i = 0; i < size; i++) {
                    getPlayer(i).setColor(i * 3);
                }
            }
            case 3 -> {
                for (int i = 0; i < size; i++) {
                    getPlayer(i).setColor(i + i + 1);
                }
            }
            case 4 -> {
                for (int i = 0; i < size / 2; i++) {
                    getPlayer(i).setColor(i + 1);
                    getPlayer(size - i - 1).setColor(5 - i);
                }
            }
            case 6 -> {
                for (int i = 0; i < size; i++) {
                    getPlayer(i).setColor(i);
                }
            }
        }
    }

    /**
     * Method for choosing a random player to start.
     */
    protected void randomPlayer() {
        this.current = (int) Math.floor(Math.random() * (goalPlayers));
        getPlayer(current).setTurn(true);
    }

    /**
     * Method changing current player for the next player in the array.
     */
    protected void nextPlayer() {
        getPlayer(current).setTurn(false);
        getPlayer(current).sendMessage("END_OF_TURN");
        getPlayer(current).sendMessage("TEXT");
        getPlayer(current).sendMessage("BUTTON");

        int active = 0;
        for (Player p : players) {
            if (p.getState() == 0) {
                active++;
            }
        }

        do {
            current = (current + 1) % goalPlayers;
        } while (getPlayer(current).getState() > 0);

        gameBoard.resetJump();
        getPlayer(current).setTurn(true);
        getPlayer(current).sendMessage("YOUR_TURN");
        getPlayer(current).sendMessage("TEXT");
        getPlayer(current).sendMessage("BUTTON");
    }

    /**
     * Making game board.
     */
    protected void makeBoard() {
        GameTile[][] tile = new GameTile[width][height];
        gameBoard = new GameBoard(width, height, tile, goalPlayers, side);
    }

    /**
     * Returns which move player has made.
     * @param command move message
     * @return 0 - invalid move, 1 - jump move, 2 - valid move
     */
    protected int moveValidation(String command) {
        String[] move = command.split(" ");
        int x1 = Integer.parseInt(move[1]);
        int y1 = Integer.parseInt(move[2]);
        int x2 = Integer.parseInt(move[3]);
        int y2 = Integer.parseInt(move[4]);

        return gameBoard.makeMoves(x1, y1, x2, y2);
    }

    /**
     * Method returning if the player won.
     * @param owner player's id
     * @return 0 - player did not win, 1 - player won
     */
    protected int playerVictory(int owner) {
        return gameBoard.isThisTheEnd(owner);
    }

    /**
     * Method returning whether there is a blockade.
     * @param owner player's id
     * @return 0 - there isn't a blockade, 2 - there was a blockade
     */
    protected int playersBlocked(int owner) {
        return gameBoard.checkBlockade(owner) ? 2 : 0;
    }

    /**
     * Method to check for the end of the gem - if there is 1 or 0 active players.
     */
    protected void checkForEnd() {
        int active = 0;
        for (Player p : players) {
            if (p.getState() == 0) {
                active++;
            }
        }

        if (active == 0 || active == 1) {
            sendToAll("ENDGAME");
        }

    }

    /**
     * Method sending information for all the players.
     * @param command message to send
     */
    protected void sendToAll(String command) {
        for (Player p : players) {
            p.sendMessage(command);
        }
    }
}
