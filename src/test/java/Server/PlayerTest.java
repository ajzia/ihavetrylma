package Server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PlayerTest {

    @Test
    public void setGetTest() {
        Game game = new Game();
        game.setGoalPlayers(6);

        Socket socket = Mockito.mock(Socket.class);

        Player player = new Player(socket, game);

        player.setColor(5);
        Assertions.assertEquals(5, player.getColor());

        player.setState(3);
        Assertions.assertEquals(3, player.getState());

        player.setTurn(true);
        Assertions.assertTrue(player.turn);
    }

    @Test
    public void runTest() throws IOException {
        Game game = new Game();
        game.setGoalPlayers(6);

        // GOAL
        Socket socket = Mockito.mock(Socket.class);
        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("GOAL 3".getBytes(StandardCharsets.UTF_8)));
        Mockito.when(socket.getOutputStream()).thenReturn(OutputStream.nullOutputStream());

        Player player = new Player(socket, game);

        player.run();
        Assertions.assertEquals(3, game.getGoalPlayers());

        // START
        game = Mockito.mock(Game.class);
        socket = Mockito.mock(Socket.class);

        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("START".getBytes(StandardCharsets.UTF_8)));
        Mockito.when(socket.getOutputStream()).thenReturn(OutputStream.nullOutputStream());
        Mockito.when(game.currentPlayers()).thenReturn(5);
        Mockito.when(game.getGoalPlayers()).thenReturn(4);

        player = new Player(socket, game);
        player.run();
        Mockito.verify(game).sendToAll("COLOR");

        // MOVE
        game = Mockito.mock(Game.class);
        socket = Mockito.mock(Socket.class);

        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("MOVE 3 4 5 6".getBytes(StandardCharsets.UTF_8)));
        Mockito.when(socket.getOutputStream()).thenReturn(OutputStream.nullOutputStream());
        Mockito.when(game.moveValidation("MOVE 3 4 5 6")).thenReturn(1);
        Mockito.when(game.currentPlayers()).thenReturn(5);
        Mockito.when(game.getGoalPlayers()).thenReturn(4);
        player.setColor(3);
        Mockito.when(game.playerVictory(player.getColor())).thenReturn(0);
        Mockito.when(game.playersBlocked(player.getColor())).thenReturn(3);

        player = new Player(socket, game);
        player.run();
        Mockito.verify(game).checkForEnd();
    }

    @Test
    public void sendMessageTest() throws IOException {
        Game game = Mockito.mock(Game.class);
        Socket socket = Mockito.mock(Socket.class);

        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));
        Mockito.when(socket.getOutputStream()).thenReturn(OutputStream.nullOutputStream());
        Mockito.when(game.currentPlayers()).thenReturn(5);
        Mockito.when(game.getGoalPlayers()).thenReturn(4);

        Player player = new Player(socket, game);

        player.run();
        player.sendMessage("COLOR");
        Mockito.verify(game, Mockito.times(2)).getGoalPlayers();

    }

}
