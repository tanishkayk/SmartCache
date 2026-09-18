package smartcache;

public class CacheStatistics {

    private int totalRequests;
    private int cacheHits;
    private int cacheMisses;

    public synchronized void recordRequest() {
        totalRequests++;
    }

    public synchronized void recordHit() {
        cacheHits++;
    }

    public synchronized void recordMiss() {
        cacheMisses++;
    }

    public synchronized double getHitRatio() {

        if (totalRequests == 0) {
            return 0.0;
        }

        return (cacheHits * 100.0) / totalRequests;
    }

    public synchronized String getStatistics() {

        return "\n===== SMARTCACHE STATISTICS =====\n"
                + "Total Requests : " + totalRequests + "\n"
                + "Cache Hits     : " + cacheHits + "\n"
                + "Cache Misses   : " + cacheMisses + "\n"
                + String.format(
                "Hit Ratio      : %.2f%%\n",
                getHitRatio()
        );
    }
}