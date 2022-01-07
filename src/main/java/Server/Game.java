package Server;

import java.util.ArrayList;

public class Game {

    int goalPlayers = 0;

    ArrayList<Player> players = new ArrayList<>();

    public void setGoalPlayers(int goalPlayers) {   // game for how many
        this.goalPlayers = goalPlayers;
        System.out.println();
    }

    public int getGoalPlayers() {
        return goalPlayers;
    }

    public int currentPlayers() {
        return players.size();
    }

    // TODO: dummy board with validation

    public void communication(String command) {

    }

    public void sendToAll(String command) {
        for (Player p : players) {
            p.sendMessage(command);
        }
    }
}


