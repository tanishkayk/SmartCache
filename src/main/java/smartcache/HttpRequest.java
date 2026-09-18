package smartcache;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpRequest {

    private final String method;
    private final String path;
    private final String url;

    public HttpRequest(String requestLine) {

        String[] parts = requestLine.split(" ");

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid HTTP request");
        }

        this.method = parts[0];
        this.path = parts[1];
        this.url = extractUrl(path);
    }

    private String extractUrl(String path) {

        if (!path.startsWith("/cache")) {
            return null;
        }

        int questionMark = path.indexOf("?");

        if (questionMark == -1) {
            return null;
        }

        String query = path.substring(questionMark + 1);

        for (String parameter : query.split("&")) {

            String[] pair = parameter.split("=", 2);

            if (pair.length == 2 && pair[0].equals("url")) {

                return URLDecoder.decode(
                        pair[1],
                        StandardCharsets.UTF_8
                );
            }
        }

        return null;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }
}