package com.example.ihavetrylma;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner in;
    private static PrintWriter out;

    private Lobby lobby;

    BoardGUI boardGUI;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        Client client = new Client(args[0]);
        client.play();
    }

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 15371);

        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        makeAction("PLAYER");
    }

    public void play() throws Exception {
        try {
            while (in.hasNextLine()) {
                var response = in.nextLine();
                System.out.println(response);
                if (response.startsWith("MOVE")) {
                    // TODO: moving pieces
                    // String[] move = response.split(" ");
                    //boardGUI.getBoard().makeMove(Integer.parseInt(move[1]), Integer.parseInt(move[2]), Integer.parseInt(move[3]), Integer.parseInt(move[4]));

                } else if (response.startsWith("PLAYER")) {
                    System.out.println("Client2");
                    String[] move = response.split(" "); // PLAYER + active players + goal for players
                    int active = Integer.parseInt(move[1]);
                    int goal = Integer.parseInt(move[2]);

                    if (active == 1 || active < goal) {
                        lobby = new Lobby(active);

                    } else if (active == goal) {            // start game
                        lobby = new Lobby(active);
                        out.println("START");
                    } else System.out.println("default");   // do nothing

                } else if (response.startsWith("START")) {
                    String[] move = response.split(" ");
                    int active = Integer.parseInt(move[1]);

                    lobby.getWaitingRoom().dispose();

                    boardGUI = new BoardGUI();
                    boardGUI.setNumberOfPlayers(active);
                    boardGUI.launchWindow();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void makeAction(String action) {
        out.println(action);
    }

}
