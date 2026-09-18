package smartcache;

public class CacheManager {

    private final LRUCache cache;
    private final long ttlMillis;

    public CacheManager(long ttlMillis, int capacity) {

        this.ttlMillis = ttlMillis;
        this.cache = new LRUCache(capacity);
    }

    public synchronized CacheEntry get(String url) {

        CacheEntry entry = cache.get(url);

        if (entry == null) {
            return null;
        }

        // Check TTL
        if (entry.isExpired()) {

            cache.remove(url);

            Logger.info("CACHE EXPIRED: " + url);

            return null;
        }

        return entry;
    }

    public synchronized void put(
            String url,
            HttpResponse response) {

        CacheEntry entry =
                new CacheEntry(response, ttlMillis);

        cache.put(url, entry);
    }

    public synchronized int size() {

        return cache.size();
    }

    public synchronized void clear() {

        cache.clear();
    }
}