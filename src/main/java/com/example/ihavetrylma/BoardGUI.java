package com.example.ihavetrylma;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoardGUI extends Application{

    int numberOfPlayers = 6;

    public static final int sideLength = 5;
    public static final int radius = 15;

    // Maximum width and height of the board
    public static final int height = 17;
    public static final int width = 25;

    // Width of each row of the board
    private static final int[] WIDTHS = {
            1, 2, 3, 4, 13, 12, 11, 10, 9, 10, 11, 12, 13, 4, 3, 2, 1
    };

    public Parent createBoard() {

        //Setting a layout
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setVgap(10);

        Tile[][] arrayOfTiles = new Tile[height][width];

        Board board = new Board(height, width, numberOfPlayers, sideLength);

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                int temp = (width - (WIDTHS[i] + WIDTHS[i] - 1)) / 2;
                int temp2 = temp + WIDTHS[i] + WIDTHS[i] - 1;

                Tile tile;
                if (j < temp) {
                    tile = new Tile(Color.WHITE, radius);
                    arrayOfTiles[i][j] = tile;
                    gameBoard.add(tile, j, i, 1, 1);
                } else if (j > temp2) {
                    tile = new Tile(Color.WHITE, radius);
                    arrayOfTiles[i][j] = tile;
                    gameBoard.add(tile, j, i, 1, 1);
                } else if (i % 2 == 0 && j % 2 == 0) {
                    tile = new Tile(Color.SILVER, radius);
                    arrayOfTiles[i][j] = tile;
                    gameBoard.add(tile, j, i, 1, 1);
                } else if (i % 2 == 0) {
                    tile = new Tile(Color.WHITE, radius);
                    arrayOfTiles[i][j] = tile;
                    gameBoard.add(tile, j, i, 1, 1);
                } else if (j % 2 == 1) {
                    tile = new Tile(Color.SILVER, radius);
                    arrayOfTiles[i][j] = tile;
                    gameBoard.add(tile, j, i, 1, 1);
                } else {
                    tile = new Tile(Color.WHITE, radius);
                    arrayOfTiles[i][j] = tile;
                    gameBoard.add(tile, j, i, 1, 1);
                }
            }
        }

        board.makePieces(arrayOfTiles);

        return gameBoard;
    }

    @Override
    public void start(Stage primaryStage) {

        //Width and height of decoration of a window
        double decorationWidth;
        double decorationHeight;

        //Width and height of a scene
        final double initialSceneWidth = radius*2* height;
        final double initialSceneHeight = radius*2* width;

        decorationWidth = primaryStage.getWidth() - initialSceneWidth;
        decorationHeight = primaryStage.getHeight() - initialSceneHeight;

        Scene scene = new Scene(createBoard(),
                initialSceneHeight - decorationHeight,initialSceneWidth - decorationWidth);

        primaryStage.setTitle("ChineseCheckers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void launchWindow() {
        launch();
    }
}