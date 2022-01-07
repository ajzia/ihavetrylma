package com.example.ihavetrylma;

import javax.swing.*;
import java.awt.*;

public class Lobby extends JFrame {

    int players;

    Lobby() {
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setResizable(true);
            this.setLayout(null);
            this.setSize(300, 350);
            this.setLocationRelativeTo(null);
            this.setTitle("Lobby");

            addTextArea();
            addButtons();

            this.setVisible(true);
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
            this.dispose();
        });

        three.addActionListener(e -> {
            players = 3;
            this.dispose();
        });

        four.addActionListener(e -> {
            players = 4;
            this.dispose();
        });

        six.addActionListener(e -> {
            players = 6;
            this.dispose();
        });

        this.add(two);
        this.add(three);
        this.add(four);
        this.add(six);
    }

}
