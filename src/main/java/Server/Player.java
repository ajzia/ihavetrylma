package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player implements Runnable {

    private final Socket socket;
    private Scanner in;
    private PrintWriter out;
    private final Game game;

    private final int id;
    private int color;
    private boolean turn = false;
    private int state = 0;

    protected Player(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;

        if (game.currentPlayers() <= game.getGoalPlayers()) {
            game.players.add(this);
        }

        this.id = game.currentPlayers() - 1;
        System.out.println(game.currentPlayers());
    }

    protected void setColor(int color) {
        this.color = color;
    }

    private int getColor() {
        return color;
    }

    protected void setState(int state) {
        this.state = state;
    }

    protected int getState() {
        return state;
    }

    protected int getId() {
        return id;
    }

    protected void setTurn(boolean turn) {
        this.turn = turn;
    }

    @Override
    public void run() {
        try {
            setup();
            processCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    private void setup() throws IOException {
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

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

                if (id == game.goalPlayers - 1) {
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
                        out.println("BLOCK");
                        out.println("ENDGAME");

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

    protected void sendMessage(String message) {
        if (message.startsWith("COLOR")) {
            out.println("COLOR" + " " + getColor() + " " + game.getGoalPlayers());
        } else out.println(message);
    }

}