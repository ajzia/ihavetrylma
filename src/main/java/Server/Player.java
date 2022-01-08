package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player implements Runnable {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Game game;

    public Player(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
        game.players.add(this);
        System.out.println(game.currentPlayers());
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
                game.sendToAll("START " + game.currentPlayers());

            } else if (command.startsWith("MOVE")) {
                if (game.moveValidation(command)) {
                    game.sendToAll(command);
                } else sendMessage("INVALID_MOVE");

            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

}
