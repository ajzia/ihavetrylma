package Server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

public class GameTest {

    @Test
    public void assignColorTest() {
        Game game = new Game();

        // two players
        Player p1 = Mockito.mock(Player.class);
        Player p2 = Mockito.mock(Player.class);

        game.players.addAll(Arrays.stream(new Player[]{p1, p2}).toList());
        game.setGoalPlayers(2);
        game.assignColors();
        Mockito.verify(p1).setColor(0);
        Mockito.verify(p2).setColor(3);

        // three players
        p1 = Mockito.mock(Player.class);
        p2 = Mockito.mock(Player.class);
        Player p3 = Mockito.mock(Player.class);

        game.players.clear();
        game.players.addAll(Arrays.stream(new Player[]{p1, p2, p3}).toList());
        game.setGoalPlayers(3);
        game.assignColors();
        Mockito.verify(p1).setColor(1);
        Mockito.verify(p2).setColor(3);
        Mockito.verify(p3).setColor(5);

        // four players
        p1 = Mockito.mock(Player.class);
        p2 = Mockito.mock(Player.class);
        p3 = Mockito.mock(Player.class);
        Player p4 = Mockito.mock(Player.class);

        game.players.clear();
        game.players.addAll(Arrays.stream(new Player[]{p1, p2, p3, p4}).toList());
        game.setGoalPlayers(4);
        game.assignColors();
        Mockito.verify(p1).setColor(1);
        Mockito.verify(p2).setColor(2);
        Mockito.verify(p3).setColor(4);
        Mockito.verify(p4).setColor(5);

        // six players
        p1 = Mockito.mock(Player.class);
        p2 = Mockito.mock(Player.class);
        p3 = Mockito.mock(Player.class);
        p4 = Mockito.mock(Player.class);
        Player p5 = Mockito.mock(Player.class);
        Player p6 = Mockito.mock(Player.class);

        game.players.clear();
        game.players.addAll(Arrays.stream(new Player[]{p1, p2, p3, p4, p5, p6}).toList());
        game.setGoalPlayers(6);
        game.assignColors();
        Mockito.verify(p1).setColor(0);
        Mockito.verify(p2).setColor(1);
        Mockito.verify(p3).setColor(2);
        Mockito.verify(p4).setColor(3);
        Mockito.verify(p5).setColor(4);
        Mockito.verify(p6).setColor(5);
    }

    @Test
    public void randomPlayerTest() {
        Game game = new Game();

        Player p1 = Mockito.mock(Player.class);

        game.players.addAll(Arrays.stream(new Player[]{p1, p1, p1}).toList());
        game.setGoalPlayers(3);

        game.randomPlayer();

        Mockito.verify(p1, Mockito.times(1)).setTurn(true);
    }

    @Test
    public void nextPlayerTest() {
        Game game = new Game();
        game.current = 0;

        Player p1 = Mockito.mock(Player.class);
        Player p2 = Mockito.mock(Player.class);
        Player p3 = Mockito.mock(Player.class);
        game.gameBoard = Mockito.mock(GameBoard.class);

        game.players.addAll(Arrays.stream(new Player[]{p1, p2, p3}).toList());
        game.setGoalPlayers(3);

        Assertions.assertEquals(3, game.getGoalPlayers());

        Mockito.when(p1.getState()).thenReturn(0);
        Mockito.when(p2.getState()).thenReturn(0);
        Mockito.when(p3.getState()).thenReturn(0);

        game.nextPlayer();

        Mockito.verify(game.gameBoard).resetJump();
        Mockito.verify(p1).setTurn(false);
        Mockito.verify(p1).sendMessage("END_OF_TURN");
        Mockito.verify(p1).sendMessage("TEXT");
        Mockito.verify(p1).sendMessage("BUTTON");

        Mockito.verify(p2).setTurn(true);
        Mockito.verify(p2).sendMessage("YOUR_TURN");
        Mockito.verify(p2).sendMessage("TEXT");
        Mockito.verify(p2).sendMessage("BUTTON");
    }

    @Test
    public void makeBoardTest() {
        Game game = new Game();
        game.makeBoard();

        Assertions.assertNotNull(game.gameBoard);
    }

    @Test
    public void moveValidationTest() {
        Game game = new Game();
        game.gameBoard = Mockito.mock(GameBoard.class);

        game.moveValidation("move 1 1 2 3");
        Mockito.verify(game.gameBoard).makeMoves(1, 1, 2, 3);
    }

    @Test
    public void playerVictoryTest() {
        Game game = new Game();
        game.gameBoard = Mockito.mock(GameBoard.class);

        game.playerVictory(3);
        Mockito.verify(game.gameBoard).isThisTheEnd(3);
    }

    @Test
    public void playersBlockedTest() {
        Game game = new Game();
        game.gameBoard = Mockito.mock(GameBoard.class);

        Mockito.when(game.gameBoard.checkBlockade(3)).thenReturn(true);

        Assertions.assertEquals(2, game.playersBlocked(3));
    }

    @Test
    public void checkForEndTest() {
        Game game = new Game();
        game.current = 0;

        Player p1 = Mockito.mock(Player.class);
        Player p2 = Mockito.mock(Player.class);
        Player p3 = Mockito.mock(Player.class);
        game.gameBoard = Mockito.mock(GameBoard.class);

        game.players.addAll(Arrays.stream(new Player[]{p1, p2, p3}).toList());
        game.setGoalPlayers(3);

        Mockito.when(p1.getState()).thenReturn(0);
        Mockito.when(p2.getState()).thenReturn(1);
        Mockito.when(p3.getState()).thenReturn(1);

        game.checkForEnd();

        Mockito.verify(p1).sendMessage("ENDGAME");
        Mockito.verify(p2).sendMessage("ENDGAME");
        Mockito.verify(p3).sendMessage("ENDGAME");
    }

}
