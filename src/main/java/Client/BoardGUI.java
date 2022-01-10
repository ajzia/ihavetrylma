package Client;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardGUI extends Application {

    private static volatile BoardGUI instance;
    static int numberOfPlayers;
    Board board;

    public static final int sideLength = 4;

    // Maximum width and height of the board
    public int height = 0;
    public int width = 0;

    public void calculateHeight(int sideLength) {
        height = sideLength + 3 * (sideLength - 1);
    }

    public void calculateWidth(int sideLength) {
        width = 2 * (sideLength + 2 * (sideLength - 1)) - 1;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        BoardGUI.numberOfPlayers = numberOfPlayers;
    }

    public Parent createBoard() {
        calculateHeight(sideLength);
        calculateWidth(sideLength);
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

    @Override
    public void stop() throws IOException {
        Client.socket.close();
    }

    public void makeBoard(Tile[][] arrayOfTiles, GridPane gameBoard) {
        Board board1 = new Board(height, width, arrayOfTiles, numberOfPlayers, sideLength, gameBoard);
        board1.createTiles();
        board1.makePieces();
        board1.createSkipButton();
        board1.createYourTurnText();
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