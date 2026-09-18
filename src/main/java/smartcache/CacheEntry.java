package smartcache;

public class CacheEntry {

    private final HttpResponse response;
    private final long createdAt;
    private final long ttlMillis;

    public CacheEntry(HttpResponse response, long ttlMillis) {
        this.response = response;
        this.createdAt = System.currentTimeMillis();
        this.ttlMillis = ttlMillis;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - createdAt >= ttlMillis;
    }
}