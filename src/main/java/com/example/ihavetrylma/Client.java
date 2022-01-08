package com.example.ihavetrylma;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner in;
    private static PrintWriter out;

    BoardGUI boardGUI;
    private Lobby lobby;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        Client client = new Client(args[0]);
        client.play();
    }

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 15375);

        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        makeAction("PLAYER");
    }

    public void play() throws Exception {
        try {
            while (in.hasNextLine()) {
                String response = in.nextLine();
                if (response.startsWith("MOVE")) {
                    String[] move = response.split(" ");
                    int x1 = Integer.parseInt(move[1]);
                    int y1 = Integer.parseInt(move[2]);
                    int x2 = Integer.parseInt(move[3]);
                    int y2 = Integer.parseInt(move[4]);

                    BoardGUI.getInstance().getBoard().makeMove(x1, y1, x2, y2);

                } else if (response.startsWith("PLAYER")) {
                    String[] move = response.split(" "); // PLAYER + active players + goal for players
                    int active = Integer.parseInt(move[1]);
                    int goal = Integer.parseInt(move[2]);

                    if (active == 1 || active < goal) {
                        lobby = new Lobby(active);

                    } else if (active == goal) {            // start game
                        lobby = new Lobby(active);

                        out.println("START");

                    } else System.out.println("The game already started!");   // do nothing

                } else if (response.startsWith("START")) {
                    String[] move = response.split(" ");
                    int active = Integer.parseInt(move[1]);

                    lobby.getWaitingRoom().dispose();

                    BoardGUI.setNumberOfPlayers(active);
                    Thread thed = new Thread(() -> boardGUI = BoardGUI.getInstance());
                    thed.start();

                } else if (response.startsWith("INVALID_MOVE")) {
                    System.out.println("Invalid move, try again!");

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
