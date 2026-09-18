package smartcache;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachingServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {

        CacheManager cacheManager =
                new CacheManager(300_000,3);

        OriginServerClient originClient =
                new OriginServerClient();

        CacheStatistics statistics =
                new CacheStatistics();

        ExecutorService threadPool =
                Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket =
                     new ServerSocket(PORT)) {

            Logger.info(
                    "SmartCache started on port "
                            + PORT
            );

            while (true) {

                Socket clientSocket =
                        serverSocket.accept();

                Logger.info(
                        "Client connected: "
                                + clientSocket.getInetAddress()
                );

                ClientHandler handler =
                        new ClientHandler(
                                clientSocket,
                                cacheManager,
                                originClient,
                                statistics
                        );

                threadPool.submit(handler);
            }

        } catch (Exception e) {

            Logger.error(
                    "Server failed: "
                            + e.getMessage()
            );

        } finally {

            threadPool.shutdown();
        }
    }
}