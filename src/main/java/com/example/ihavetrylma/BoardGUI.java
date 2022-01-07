package com.example.ihavetrylma;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BoardGUI extends Application{

    private static int numberOfPlayers;

    public static final int sideLength = 5;

    // Maximum width and height of the board
    public static final int height = 17;
    public static final int width = 26;

    public void setNumberOfPlayers(int numberOfPlayers) {
        BoardGUI.numberOfPlayers = numberOfPlayers;
    }

    public Parent createBoard() {

        //Setting a layout
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setVgap(10);

        Tile[][] arrayOfTiles = new Tile[width][height];

        Board board = new Board(height, width, arrayOfTiles, numberOfPlayers, sideLength, gameBoard);
        board.createTiles();
        board.makePieces();

        return gameBoard;
    }

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(createBoard());
        primaryStage.setTitle("ChineseCheckers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void launchWindow() {
        launch();
    }
}
