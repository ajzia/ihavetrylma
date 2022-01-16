package com.example.ihavetrylma.Client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LobbyTest {

    @Test
    public void makeLobbyTest() {
        final Lobby lobby = new Lobby(1);
        Assertions.assertNotNull(lobby);

        final Lobby lobby2 = new Lobby(3);
        Assertions.assertNotNull(lobby2.waitingRoom);
    }

    @Test
    public void getWaitingRoomTest() {
        final Lobby lobby = new Lobby(1);
        lobby.makeWaitingRoom();
        Assertions.assertNotNull(lobby.getWaitingRoom());
    }


}