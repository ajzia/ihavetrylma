package Server;

import Server.Player;

import java.util.ArrayList;

public class Game {

    ArrayList<Player> players = new ArrayList<>();

    // TODO: dummy board with validation
    public void communication(String command) {

    }

    public void sendToAll(String command) {
        for (Player p : players) {
            p.sendMessage(command);
        }
    }
}


