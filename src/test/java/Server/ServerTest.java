/*
This test only exists to have 100% in tests in method coverage,
but it disrupts other tests as it exits the application :)

package Server;

import org.junit.jupiter.api.Test;

public class ServerTest {

    @Test
    public void ServerTest() throws Exception {
        new Thread(() -> {
            for (int i = 0; i < 10000; i++);
            System.exit(0);
        }).start();

        Server.main(null);

    }

}*/
