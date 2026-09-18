package smartcache;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final CacheManager cacheManager;
    private final OriginServerClient originClient;
    private final CacheStatistics statistics;

    public ClientHandler(
            Socket clientSocket,
            CacheManager cacheManager,
            OriginServerClient originClient,
            CacheStatistics statistics) {

        this.clientSocket = clientSocket;
        this.cacheManager = cacheManager;
        this.originClient = originClient;
        this.statistics = statistics;
    }

    @Override
    public void run() {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream()
                                )
                        );

                OutputStream output =
                        clientSocket.getOutputStream()
        ) {

            String requestLine = reader.readLine();

            if (requestLine == null) {
                return;
            }

            Logger.info("Request: " + requestLine);

            HttpRequest request =
                    new HttpRequest(requestLine);

            statistics.recordRequest();

            if (!request.getMethod().equals("GET")) {

                sendError(
                        output,
                        405,
                        "Method Not Allowed"
                );

                return;
            }

            String url = request.getUrl();

            if (url == null || url.isEmpty()) {

                sendError(
                        output,
                        400,
                        "URL is required"
                );

                return;
            }

            CacheEntry cachedEntry =
                    cacheManager.get(url);

            if (cachedEntry != null) {

                Logger.info("CACHE HIT: " + url);

                statistics.recordHit();

                sendResponse(
                        output,
                        cachedEntry.getResponse()
                );

                return;
            }

            Logger.info("CACHE MISS: " + url);

            statistics.recordMiss();

            HttpResponse response =
                    originClient.fetch(url);

            cacheManager.put(url, response);


            Logger.info(
                    "Stored response in cache: " + url
            );

            sendResponse(output, response);

        } catch (Exception e) {

            Logger.error(
                    "Request handling failed: "
                            + e.getMessage()
            );

        } finally {

            try {
                clientSocket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void sendResponse(
            OutputStream output,
            HttpResponse response)
            throws IOException {

        String headers =
                "HTTP/1.1 "
                        + response.getStatusCode()
                        + " OK\r\n"
                        + "Content-Type: "
                        + response.getContentType()
                        + "\r\n"
                        + "Content-Length: "
                        + response.getBody().length
                        + "\r\n"
                        + "Connection: close\r\n"
                        + "\r\n";

        output.write(headers.getBytes());
        output.write(response.getBody());
        output.flush();
    }

    private void sendError(
            OutputStream output,
            int statusCode,
            String message)
            throws IOException {

        byte[] body = message.getBytes();

        String headers =
                "HTTP/1.1 "
                        + statusCode
                        + " Error\r\n"
                        + "Content-Type: text/plain\r\n"
                        + "Content-Length: "
                        + body.length
                        + "\r\n"
                        + "Connection: close\r\n"
                        + "\r\n";

        output.write(headers.getBytes());
        output.write(body);
        output.flush();
    }
}