package smartcache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {

    private final int capacity;

    private final LinkedHashMap<String, CacheEntry> cache;

    public LRUCache(int capacity) {

        this.capacity = capacity;

        cache = new LinkedHashMap<>(
                capacity,
                0.75f,
                true
        );
    }

    public CacheEntry get(String url) {

        return cache.get(url);
    }

    public void put(String url, CacheEntry entry) {

        cache.put(url, entry);

        if (cache.size() > capacity) {

            String leastRecentlyUsed =
                    cache.keySet().iterator().next();

            cache.remove(leastRecentlyUsed);

            Logger.info(
                    "LRU EVICTED: " + leastRecentlyUsed
            );
        }
    }

    public int size() {

        return cache.size();
    }

    public void remove(String url) {

        cache.remove(url);
    }

    public void clear() {

        cache.clear();
    }
}