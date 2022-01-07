package com.example.ihavetrylma;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// slucha i wyswietla
public class Client {
    private Socket socket;
    private Scanner in;
    private static PrintWriter out;

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

        // Zdefiniowanie Skanera opartego na streamie wejściowym (cos przychodzi z serwera)
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        boardGUI = new BoardGUI();
        boardGUI.launchWindow();
    }

    public void play() throws Exception {
        try {
            // komunikat z serwera
            var response = in.nextLine();
            // W zaleznosci jaki komunikat przyszedl
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("MOVE")) {
                    // TODO: moving pieces
                    String[] move = response.split(" ");
                    //boardGUI.getBoard().makeMove(Integer.parseInt(move[1]), Integer.parseInt(move[2]), Integer.parseInt(move[3]), Integer.parseInt(move[4]));
                }
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void action(String action) {
        if (action.startsWith("Siema")) {
            System.out.println("elo");

        } else if (action.startsWith("MOVE")) {
            out.println(action);
        }
    }

}
