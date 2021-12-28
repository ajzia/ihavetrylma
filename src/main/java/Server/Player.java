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
        game.players.add(this); // ??????
    }

    @Override
    public void run() {
        try {
            setup();
            processCommands();
        } catch (Exception e) {
            e.printStackTrace();
        } try {
            socket.close();
        } catch (IOException e) {}
    }

    private void setup() throws IOException {
        // Poczatek gry
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("WELCOME");
    }

    private void processCommands() {
        while (in.hasNextLine()) {
            // command from server
            var command = in.nextLine();
            if (command.startsWith("QUIT")) {
                // do : quit
            }
            else if (command.startsWith("COLOR")) {
                game.communication(command);
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

}



