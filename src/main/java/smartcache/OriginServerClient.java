package smartcache;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class OriginServerClient {

    private final HttpClient client;

    public OriginServerClient() {

        client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public smartcache.HttpResponse fetch(String url)
            throws Exception {

        if (!url.startsWith("http://")
                && !url.startsWith("https://")) {

            throw new IllegalArgumentException(
                    "Only HTTP and HTTPS URLs are supported"
            );
        }

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        java.net.http.HttpResponse<byte[]> response =
                client.send(
                        request,
                        BodyHandlers.ofByteArray()
                );

        String contentType =
                response.headers()
                        .firstValue("Content-Type")
                        .orElse("text/plain");

        return new smartcache.HttpResponse(
                response.statusCode(),
                contentType,
                response.body()
        );
    }
}