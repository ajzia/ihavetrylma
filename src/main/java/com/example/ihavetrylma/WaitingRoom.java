package com.example.ihavetrylma;
import javax.swing.*;
import java.awt.*;

public class WaitingRoom extends JFrame {

    WaitingRoom() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(370, 100);
        this.setLocationRelativeTo(null);
        this.setTitle("Waiting room");

        addTextArea();

        this.setVisible(true);
    }

    public void addTextArea() {
        JTextArea textArea = new JTextArea("Please wait for the other players to connect");
        textArea.setBounds(22, 20, 320, 30);
        textArea.setBackground(null);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        textArea.setMargin(new Insets(4, 4, 0, 0));

        this.add(textArea);
    }
}
