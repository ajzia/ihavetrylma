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
    private boolean won = false;

    protected Player(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;

        if (game.currentPlayers() <= game.getGoalPlayers()) {
            game.players.add(this);
        }

        this.id = game.currentPlayers();
        System.out.println(game.currentPlayers());
    }

    protected void setColor(int color) {
        this.color = color;
    }

    private int getColor() {
        return color;
    }

    protected void setWon() {
        won = true;
    }

    protected boolean getWon() {
        return won;
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
                game.sendToAll("START " + game.currentPlayers());

                if(id == game.goalPlayers) {
                    game.randomPlayer();
                }

            } else if (command.startsWith("MOVE")) {
                if (game.moveValidation(command)) {
                    game.nextPlayer();
                    game.sendToAll(command);
                } else sendMessage("INVALID_MOVE");
            } else if (command.startsWith("SET_TURN")) {
                if(turn) {
                    sendMessage("YOUR_TURN");
                }
            }
        }
    }

    protected void sendMessage(String message) {
        if(message.startsWith("COLOR")) {
            System.out.println("kolor gracza" + " " + getColor() + " jego id to " + id);
            out.println("COLOR" + " " + getColor() + " " + game.getGoalPlayers());
        } else out.println(message);
    }

}