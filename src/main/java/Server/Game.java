package Server;

import Server.Player;

import java.util.ArrayList;

public class Game {

    ArrayList<Player> players = new ArrayList<>();

    public void communication(String command) {
        for (Player p : players) {
            p.sendMessage(command);
        }

    }
}

// komunikacja? // ruszanie pionów??

