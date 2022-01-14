package Client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Class responsible for everything visible on the board: tiles, pieces, skip button, "Your turn!" text
 */
public class Board {
    /**
     * Variable containing number of players
     */
    protected final int numberOfPlayers;
    /**
     * Variable containing length of a side of a hexagon
     */
    protected final int sideLength;
    /**
     * Variable containing maximum number of rows
     */
    protected final int height;
    /**
     * Variable containing maximum number of columns
     */
    protected final int width;
    /**
     * Array containing tiles
     */
    private final Tile[][] arrayOfTiles;

    /**
     * Layout of a board
     */
    private final GridPane gameBoard;
    /**
     * Button responsible for skipping a turn
     */
    private Button skip;
    /**
     * Label containing "Your turn!" text
     */
    private Label yourTurn;

    protected int movingRow = -1;
    protected int movingColumn = -1;

    /**
     * Array containing widths of every row of a big triangle
     */
    protected int[] WIDTHS;

    /**
     * Board constructor
     * @param height - maximum number of tiles in a column
     * @param width - maximum number of tiles in a row
     * @param arrayOfTiles - array containing every tile
     * @param numberOfPlayers - number of players that play the game
     * @param sideLength - side length of a hexagon
     * @param gameBoard - layout of the board
     */
    public Board(int height, int width, Tile[][] arrayOfTiles, int numberOfPlayers, int sideLength, GridPane gameBoard) {
        this.height = height;
        this.width = width;
        this.arrayOfTiles = arrayOfTiles;
        this.numberOfPlayers = numberOfPlayers;
        this.sideLength = sideLength;
        this.gameBoard = gameBoard;

        countWidths(sideLength);
    }

    /**
     * Method counting the lengths of rows of the biggest triangle
     * @param sideLength - length of a side of a hexagon
     */
    protected void countWidths(int sideLength) {
        WIDTHS = new int[7 + 3 * (sideLength - 3)];

        for (int i = 1; i <= 7 + 3 * (sideLength - 3); i++) {
            WIDTHS[i - 1] = i;
        }
    }

    /**
     * Method creating skip button,
     * giving it action after clicking on it
     * and adding it to the board
     */
    protected void createSkipButton() {
        skip = new Button("Skip");
        changeButtonVisibility();
        skip.setOnMouseClicked(e -> Client.makeAction("SKIP"));

        gameBoard.add(skip, width - 2 - ((sideLength - 1) + (sideLength - 3)) / 2, height - sideLength / 2, 3, 1);
    }

    /**
     * Method changing button visibility
     */
    protected void changeButtonVisibility() {
        skip.setVisible(Client.turn);
    }

    /**
     * Method creating "Your turn!" text,
     * giving it a color of player's pieces
     * and adding to the board
     */
    protected void createYourTurnText() {
        Pane pane = new Pane();
        yourTurn = new Label("Your turn!");
        pane.getChildren().add(yourTurn);

        changeTextVisibility();

        gameBoard.add(yourTurn, (((sideLength - 1) + (sideLength - 3)) / 2), sideLength / 2 - 1, 3, 1);
    }

    /**
     * Method changing visibility of "Your turn!" text
     */
    protected void changeTextVisibility() {
        if (Client.turn) {
            yourTurn.setVisible(true);
            yourTurn.setTextFill(Colour.getTileColor(Client.colour));
        } else {
            yourTurn.setVisible(false);
        }
    }

    /**
     * Method creating tiles
     */
    protected void createTiles() {
        int temp = WIDTHS[WIDTHS.length - 2];
        Tile tile;

        //create triangle of tiles
        for (int i = 0; i < WIDTHS[WIDTHS.length - 1]; i++) {
            temp = temp - i;
            for (int j = 0; j < WIDTHS[i]; j++) {
                tile = new Tile(temp, i, this, sideLength);
                arrayOfTiles[temp][i] = tile;

                gameBoard.add(tile, temp, i, 1, 1);

                temp = temp + 2;
            }
            temp = WIDTHS[WIDTHS.length - 2];
        }

        //create inverted triangle of tiles
        int temp2 = 0;
        for (int i = height - 1; i > height - 1 - WIDTHS[WIDTHS.length - 1]; i--) {
            temp = temp - temp2;
            for (int j = 0; j < WIDTHS[temp2]; j++) {
                //check if tiles already exists
                if (arrayOfTiles[temp][i] == null) {
                    tile = new Tile(temp, i, this, sideLength);
                    arrayOfTiles[temp][i] = tile;

                    gameBoard.add(tile, temp, i, 1, 1);

                }
                temp = temp + 2;
            }
            temp = WIDTHS[WIDTHS.length - 2];
            temp2++;
        }
    }

    /**
     * Method creating pieces for players depending on number of players
     */
    protected void makePieces() {
        switch (numberOfPlayers) {
            case 2 -> {
                setFirstPlayer();
                setFourthPlayer();
            }
            case 3 -> {
                setSecondPlayer();
                setFourthPlayer();
                setSixthPlayer();
            }
            case 4 -> {
                setSecondPlayer();
                setThirdPlayer();
                setFifthPlayer();
                setSixthPlayer();
            }
            case 6 -> {
                setFirstPlayer();
                setSecondPlayer();
                setThirdPlayer();
                setFourthPlayer();
                setFifthPlayer();
                setSixthPlayer();
            }
            default -> System.out.println("Wrong number of players");
        }
    }

    /**
     *  Method adding piece in a new place
     * @param column - column number
     * @param row - row number
     * @param owner - owner number
     */
    protected void addPiece(int column, int row, int owner) {
        arrayOfTiles[column][row].setOwner(owner);
    }

    /**
     * Method removing piece from a previous place
     * @param column - column number
     * @param row - row number
     */
    protected void removePiece(int column, int row) {
        arrayOfTiles[column][row].setOwner(-1);
    }

    /**
     * Method that sends information about the move to the client
     * @param oldColumn - previous column number
     * @param oldRow - previous row number
     * @param newColumn - new column number
     * @param newRow - new row number
     */
    protected void movePiece(int oldColumn, int oldRow, int newColumn, int newRow) {
        Client.makeAction("MOVE " + oldColumn + " " + oldRow + " " + newColumn + " " + newRow);    // Move x1 y1 x2 y2
    }

    /**
     * Method that moves piece
     * @param oldColumn - previous column number
     * @param oldRow - previous row number
     * @param newColumn - new column number
     * @param newRow - new row number
     */
    protected void makeMove(int oldColumn, int oldRow, int newColumn, int newRow) {
        addPiece(newColumn, newRow, arrayOfTiles[oldColumn][oldRow].getOwner());
        removePiece(oldColumn, oldRow);
    }

    /**
     * Creating pieces for first player ( upper star arm )
     */
    private void setFirstPlayer() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = 0; row < sideLength - 1; row++) {
            for (int column = temp2; column <= temp; column = column + 2) {
                addPiece(column, row, 0);
                arrayOfTiles[column][row].setStroke(Color.web("#FA26A0"));
            }
            temp++;
            temp2--;
        }
    }

    /**
     * Creating pieces for second player ( upper right star arm )
     */
    private void setSecondPlayer() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = sideLength - 1; row < (sideLength - 1) * 2; row++) {
            for (int column = temp2; column > temp; column = column - 2) {
                addPiece(column, row, 1);
                arrayOfTiles[column][row].setStroke(Color.web("#7954A1"));
            }
            temp++;
            temp2--;
        }
    }

    /**
     * Creating pieces for third player ( lower right star arm )
     */
    private void setThirdPlayer() {
        int temp = width - ((sideLength - 1) * 2 + 1);
        int temp2 = width - 1;
        for (int row = height - sideLength; row > (sideLength - 1) * 2; row--) {
            for (int column = temp2; column > temp; column = column - 2) {
                addPiece(column, row, 2);
                arrayOfTiles[column][row].setStroke(Color.web("#0E86D4"));
            }
            temp++;
            temp2--;
        }
    }

    /**
     * Creating pieces for fourth player ( lower star arm )
     */
    private void setFourthPlayer() {
        int temp = width / 2;
        int temp2 = width / 2;
        for (int row = height - 1; row > height - 1 - (sideLength - 1); row--) {
            for (int column = temp2; column <= temp; column = column + 2) {
                addPiece(column, row, 3);
                arrayOfTiles[column][row].setStroke(Color.web("#76B947"));
            }
            temp++;
            temp2--;
        }
    }

    /**
     * Creating pieces for fifth player ( lower left star arm )
     */
    private void setFifthPlayer() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for (int row = height - sideLength; row > (sideLength - 1) * 2; row--) {
            for (int column = temp2; column < temp; column = column + 2) {
                addPiece(column, row, 4);
                arrayOfTiles[column][row].setStroke(Color.web("#C85250"));
            }
            temp--;
            temp2++;
        }
    }

    /**
     * Creating pieces for sixth player ( upper left star arm )
     */
    private void setSixthPlayer() {
        int temp = (sideLength - 1) * 2;
        int temp2 = 0;
        for (int row = sideLength - 1; row < (sideLength - 1) * 2; row++) {
            for (int column = temp2; column < temp; column = column + 2) {
                addPiece(column, row, 5);
                arrayOfTiles[column][row].setStroke(Color.web("#FD7F20"));
            }
            temp--;
            temp2++;
        }
    }
}