package Server;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(15371)) {
            System.out.println("Server is running...");
            var pool = Executors.newFixedThreadPool(10);
            while(true) {
                Game game = new Game(); // list of players, adding board to every one of them?
                pool.execute(new Player(listener.accept(), game));
                System.out.println("siema1");
                pool.execute(new Player(listener.accept(), game));
                System.out.println("siema2");
                pool.execute(new Player(listener.accept(), game));
                System.out.println("siema3");
                pool.execute(new Player(listener.accept(), game));
                System.out.println("siema4");
                pool.execute(new Player(listener.accept(), game));
                System.out.println("siema5");
                pool.execute(new Player(listener.accept(), game));
                System.out.println("siema6");
            }

        }
    }

}
