package com.example.ihavetrylma.Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client class
 */
public class Client {

    /**
     * Player's socket
     */
    protected static Socket socket;
    /**
     * Input
     */
    private final Scanner in;
    /**
     * Output
     */
    private static PrintWriter out;

    /**
     * Player's turn
     */
    protected static boolean turn = false;
    /**
     * Player's colour
     */
    protected static int colour = -1;

    /**
     * BoardGUI
     */
    protected BoardGUI boardGUI;
    /**
     * Lobby
     */
    private Lobby lobby;

    /**
     * Connecting new clients and starting listening for commands
     * @param args args
     * @throws Exception problem with connection
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost");
        client.play();
    }

    /**
     * Client constructor
     * @param serverAddress "localhost"
     * @throws Exception exception
     */
    private Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 15373);

        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        makeAction("PLAYER");
    }

    /**
     * Communication with the server, processing server messages
     * @throws Exception exception
     */
    private void play() throws Exception {
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

                    } else {
                        System.out.println("The game already started!");   // do nothing
                        socket.close();
                    }
                } else if (response.startsWith("START")) {
                    String[] move = response.split(" ");
                    int active = Integer.parseInt(move[1]);

                    lobby.getWaitingRoom().dispose();

                    BoardGUI.setNumberOfPlayers(active);
                    Thread thed = new Thread(() -> boardGUI = BoardGUI.getInstance());
                    thed.start();

                    out.println("SET_TURN");

                } else if (response.startsWith("INVALID_MOVE")) {
                    System.out.println("Invalid move, try again!");

                } else if (response.startsWith("YOUR_TURN")) {
                    turn = true;

                } else if (response.startsWith("END_OF_TURN")) {
                    turn = false;

                } else if (response.startsWith("COLOR")) {
                    String[] color = response.split(" ");
                    colour = Integer.parseInt(color[1]);

                } else if (response.startsWith("TEXT")) {
                    BoardGUI.getInstance().getBoard().changeTextVisibility();

                } else if (response.startsWith("BUTTON")) {
                    BoardGUI.getInstance().getBoard().changeButtonVisibility();

                } else if (response.startsWith("VICTORY!")) {
                    System.out.println("FINALLY! A WORTHY OPPONENT!");

                } else if (response.startsWith("BLOCK")) {
                    System.out.println("You are blocked :((");

                } else if (response.startsWith("ENDGAME")) {
                    System.out.println("The end! Thank you for playing");
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    /**
     * Sending messages to the server
     * @param action message
     */
    public static void makeAction(String action) {
        out.println(action);
    }

}