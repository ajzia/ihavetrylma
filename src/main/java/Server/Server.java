package Server;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

/**
 * Server class
 */
public class Server {

    /**
     * Creates new server and connects new players to the game
     * @param args arguments from the command line if needed
     * @throws Exception problem with connection
     */
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(15373)) {
            System.out.println("Server is running...");
            var pool = Executors.newFixedThreadPool(10);

            Game game = new Game();
            pool.execute(new Player(listener.accept(), game));

            while (true) {
                pool.execute(new Player(listener.accept(), game));
                pool.execute(new Player(listener.accept(), game));
                pool.execute(new Player(listener.accept(), game));
                pool.execute(new Player(listener.accept(), game));
                pool.execute(new Player(listener.accept(), game));
            }

        }
    }

}
