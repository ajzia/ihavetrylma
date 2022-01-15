package Client;

import javax.swing.*;
import java.awt.*;

import static Client.Client.makeAction;

/**
 * Class responsible for lobby
 */
public class Lobby extends JFrame {

    /**
     * Waiting room object
     */
    protected WaitingRoom waitingRoom;
    /**
     * Number of players
     */
    protected int players;

    /**
     * Lobby constructor
     * Creates lobby for the first player. For every other player creates waiting room
     * @param active - if lobby is active
     */
    Lobby(int active) {
        if (active == 1) {
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.setLayout(null);
            this.setSize(300, 350);
            this.setLocationRelativeTo(null);
            this.setTitle("Lobby");

            addTextArea();
            addButtons();

            this.setVisible(true);
        } else makeWaitingRoom();

    }

    /**
     * Method adding text "Choose number of players: "
     */
    private void addTextArea() {
        JTextArea textArea = new JTextArea("Choose number of players: ");
        textArea.setBounds(42, 20, 200, 30);
        textArea.setBackground(null);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        textArea.setMargin(new Insets(4, 4, 0, 0));

        this.add(textArea);
    }

    /**
     * Method creating buttons, adding them to the lobby window and giving them actions
     */
    private void addButtons() {
        JButton two = new JButton("two");
        JButton three = new JButton("three");
        JButton four = new JButton("four");
        JButton six = new JButton("six");

        two.setBounds(90, 60, 100, 50);
        three.setBounds(90, 120, 100, 50);
        four.setBounds(90, 180, 100, 50);
        six.setBounds(90, 240, 100, 50);

        two.addActionListener(e -> {
            players = 2;
            clicked();
        });

        three.addActionListener(e -> {
            players = 3;
            clicked();
        });

        four.addActionListener(e -> {
            players = 4;
            clicked();
        });

        six.addActionListener(e -> {
            players = 6;
            clicked();
        });

        this.add(two);
        this.add(three);
        this.add(four);
        this.add(six);
    }

    /**
     * Method setting number of players
     */
    private void clicked() {
        makeAction("GOAL" + " " + players);
        this.dispose();
        makeWaitingRoom();
    }

    /**
     * Method making waiting room for players
     */
    protected void makeWaitingRoom() {
        waitingRoom = new WaitingRoom();
    }

    /**
     * Method returning waiting room
     * @return waiting room
     */
    protected WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

}
