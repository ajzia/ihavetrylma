package Server;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {

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
