package Client;

import javax.swing.*;
import java.awt.*;

public class Lobby extends JFrame {

    WaitingRoom waitingRoom;
    int players;

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

    public void addTextArea() {
        JTextArea textArea = new JTextArea("Choose number of players: ");
        textArea.setBounds(42, 20, 200, 30);
        textArea.setBackground(null);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        textArea.setMargin(new Insets(4, 4, 0, 0));

        this.add(textArea);
    }

    public void addButtons() {
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

    public void clicked() {
        Client.makeAction("GOAL" + " " + players);
        this.dispose();
        makeWaitingRoom();
    }

    public void makeWaitingRoom() {
        waitingRoom = new WaitingRoom();
    }

    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

}
