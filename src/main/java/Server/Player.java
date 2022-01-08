package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// slucha i mowi
public class Player implements Runnable {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Game game;

    public Player(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
        game.players.add(this);
        System.out.println(game.players.size());
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
        // Poczatek gry
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private void processCommands() {
        while (in.hasNextLine()) {
            // command from server
            var command = in.nextLine();

            if (command.startsWith("MOVE")) {
                game.sendToAll(command);

            } else if (command.startsWith("PLAYER")) {
                out.println("PLAYER" + " " + game.currentPlayers() + " " + game.getGoalPlayers());

            } else if (command.startsWith("GOAL")) {
                String[] goal = command.split(" ");
                game.setGoalPlayers(Integer.parseInt(goal[1]));

            } else if (command.startsWith("START")) {
                game.sendToAll("START " + game.currentPlayers());

            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

}



