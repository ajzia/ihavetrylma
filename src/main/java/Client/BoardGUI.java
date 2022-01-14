package Client;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class responsible for creating a window
 */
public class BoardGUI extends Application {
    /**
     * Instance of a BoardGUI class
     */
    private static volatile BoardGUI instance;
    /**
     * Length of a side of a hexagon
     */
    private static final int sideLength = 2;
    /**
     * Variable containing number of players
     */
    private static int numberOfPlayers;
    /**
     * Variable containing maximum number of rows
     */
    private int height;
    /**
     * Variable containing maximum number of columns
     */
    private int width;
    /**
     * Object of Board class
     */
    private Board board;

    /**
     * Method calculating the maximum number of rows depending on length of a side of a hexagon
     * @param sideLength - length of a side of a hexagon
     */
    protected void calculateHeight(int sideLength) {
        height = sideLength + 3 * (sideLength - 1);
    }

    /**
     * Method calculating the maximum number of columns depending on length of a side of a hexagon
     * @param sideLength - length of a side of a hexagon
     */
    protected void calculateWidth(int sideLength) {
        width = 2 * (sideLength + 2 * (sideLength - 1)) - 1;
    }

    /**
     * Method returning maximum number of rows
     * @return maximum number of rows
     */
    protected int getHeight() {
        return height;
    }

    /**
     * Method returning maximum number of columns
     * @return maximum number of columns
     */
    protected int getWidth() {
        return width;
    }

    /**
     * Method returning length of a side of a hexagon
     * @return length of a side of a hexagon
     */
    public static int getSide() {
        return sideLength;
    }

    /**
     * Method setting number of players
     * @param numberOfPlayers - number of players
     */
    protected static void setNumberOfPlayers(int numberOfPlayers) {
        BoardGUI.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Method setting a layout of a window
     * @return layout of a window
     */
    protected Parent createBoard() {
        calculateHeight(sideLength);
        calculateWidth(sideLength);
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setVgap(10);

        Tile[][] arrayOfTiles = new Tile[width][height];

        makeBoard(arrayOfTiles, gameBoard);

        return gameBoard;
    }

    /**
     * Method creating a window with the game
     * @param primaryStage - window
     */
    @Override
    public void start(Stage primaryStage) {
        instance = this;

        Scene scene = new Scene(createBoard());
        primaryStage.setTitle("ChineseCheckers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    /**
     * Method closing a socket
     * @throws IOException exception
     */
    @Override
    public void stop() throws IOException {
        Client.socket.close();
    }

    /**
     * Method creating a board
     * @param arrayOfTiles - array containing tiles
     * @param gameBoard - layout of a board
     */
    protected void makeBoard(Tile[][] arrayOfTiles, GridPane gameBoard) {
        Board board1 = new Board(height, width, arrayOfTiles, numberOfPlayers, sideLength, gameBoard);
        board1.createTiles();
        board1.makePieces();
        board1.createSkipButton();
        board1.createYourTurnText();
        setBoard(board1);
    }

    /**
     * Method setting the board
     * @param board - board
     */
    protected void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Method returning the bord
     * @return board
     */
    protected Board getBoard() {
        return board;
    }

    /**
     * Method returning instance of BoardGUI
     * @return instance of BoardGUI
     */
    protected static BoardGUI getInstance() {
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