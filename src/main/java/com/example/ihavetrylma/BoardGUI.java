package com.example.ihavetrylma;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BoardGUI extends Application {

    private static volatile BoardGUI instance;
    static int numberOfPlayers;
    Board board;

    public static final int sideLength = 5;

    // Maximum width and height of the board
    public static final int height = 17;
    public static final int width = 25;

    public static void setNumberOfPlayers(int numberOfPlayers) {
        BoardGUI.numberOfPlayers = numberOfPlayers;
    }

    public Parent createBoard() {
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setVgap(10);

        Tile[][] arrayOfTiles = new Tile[width][height];

        makeBoard(arrayOfTiles, gameBoard);

        return gameBoard;
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;

        Scene scene = new Scene(createBoard());
        primaryStage.setTitle("ChineseCheckers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public void makeBoard(Tile[][] arrayOfTiles, GridPane gameBoard) {
        Board board1 = new Board(height, width, arrayOfTiles, numberOfPlayers, sideLength, gameBoard);
        board1.createTiles();
        board1.makePieces();
        setBoard(board1);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public static BoardGUI getInstance() {
        if (instance == null) {
            synchronized (BoardGUI.class) {
                if (instance == null) {
                    launch();
                }
            }
        }
        return instance;
    }

}
