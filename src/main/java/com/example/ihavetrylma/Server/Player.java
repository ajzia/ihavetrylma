package com.example.ihavetrylma.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Player class
 */
public class Player implements Runnable {

    /**
     * Player's socket
     */
    private final Socket socket;
    /**
     * Input Client
     */
    private Scanner in;
    /**
     * Output from Player
     */
    private PrintWriter out;
    /**
     * Game
     */
    private final Game game;

    /**
     * Player number
     */
    private final int number;
    /**
     * Color / player id
     */
    private int color;
    /**
     * Field for player's turn
     */
    protected boolean turn = false;
    /**
     * Player state: 0 - active, 1 - won
     */
    private int state = 0;

    /**
     * Player constructor
     * @param socket player's socket
     * @param game main game
     */
    protected Player(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;

        if (game.currentPlayers() <= game.getGoalPlayers()) {
            game.players.add(this);
        }

        this.number = game.currentPlayers();
    }

    /**
     * Setting player's color
     * @param color player's color
     */
    protected void setColor(int color) {
        this.color = color;
    }

    /**
     * Getting player's color
     * @return color
     */
    protected int getColor() {
        return color;
    }

    /**
     * Setting player's state
     * @param state player's state
     */
    protected void setState(int state) {
        this.state = state;
    }

    /**
     * Getting player's state
     * @return state
     */
    protected int getState() {
        return state;
    }

    /**
     * Setting player's turn
     * @param turn player's turn
     */
    protected void setTurn(boolean turn) {
        this.turn = turn;
    }

    /**
     * Starts player's thread
     */
    @Override
    public void run() {
        try {
            setup();
            processCommands();
        } catch (Exception ignored) {}
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Establishes communication with the Client class
     * @throws IOException ioexception
     */
    private void setup() throws IOException {
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Processing commands sent from the Client class
     */
    private void processCommands() {
        while (in.hasNextLine()) {
            String command = in.nextLine();
            if (command.startsWith("PLAYER")) {
                out.println("PLAYER" + " " + game.currentPlayers() + " " + game.getGoalPlayers());

            } else if (command.startsWith("GOAL")) {
                String[] goal = command.split(" ");
                game.setGoalPlayers(Integer.parseInt(goal[1]));

            } else if (command.startsWith("START")) {
                game.makeBoard();
                game.assignColors();
                game.sendToAll("COLOR");

                if (number == game.getGoalPlayers()) {
                    game.randomPlayer();
                }

                game.sendToAll("START " + game.currentPlayers());

            } else if (command.startsWith("MOVE")) {
                int move = game.moveValidation(command);

                if (move > 0) {
                    int win = game.playerVictory(color);
                    int block = game.playersBlocked(color);

                    if (win == 1) {
                        setState(win);
                        out.println("VICTORY!");
                        game.nextPlayer();

                    } else if (block == 2) {
                        setState(block);
                        game.sendToAll(command);
                        game.sendToAll("BLOCK");
                        game.sendToAll("ENDGAME");

                    } else if (move == 2) {
                        game.nextPlayer();
                    }

                    game.sendToAll(command);

                    game.checkForEnd();

                } else sendMessage("INVALID_MOVE");

            } else if (command.startsWith("SET_TURN")) {
                if (turn) {
                    sendMessage("YOUR_TURN");
                }
            } else if (command.startsWith("SKIP")) {
                game.nextPlayer();
            }
        }
    }

    /**
     * Sending message back to the Client
     * @param message message
     */
    protected void sendMessage(String message) {
        if (message.startsWith("COLOR")) {
            out.println("COLOR" + " " + getColor() + " " + game.getGoalPlayers());
        } else out.println(message);
    }

}