package com.skl.distributedcache.anno.method;

public class CacheInvokeConfig {
    private static final CacheInvokeConfig noCacheInvokeConfigInstance = new CacheInvokeConfig();
    private CachedAnnoConfig cachedAnnoConfig;

    private CacheUpdateAnnoConfig cacheUpdateAnnoConfig;

    private CacheInvalidateAnnoConfig cacheInvalidateAnnoConfig;


    public CachedAnnoConfig getCachedAnnoConfig() {
        return cachedAnnoConfig;
    }

    public void setCachedAnnoConfig(CachedAnnoConfig cachedAnnoConfig) {
        this.cachedAnnoConfig = cachedAnnoConfig;
    }


    public CacheUpdateAnnoConfig getCacheUpdateAnnoConfig() {
        return cacheUpdateAnnoConfig;
    }

    public void setCacheUpdateAnnoConfig(CacheUpdateAnnoConfig cacheUpdateAnnoConfig) {
        this.cacheUpdateAnnoConfig = cacheUpdateAnnoConfig;
    }

    public CacheInvalidateAnnoConfig getCacheInvalidateAnnoConfig() {
        return cacheInvalidateAnnoConfig;
    }

    public void setCacheInvalidateAnnoConfig(CacheInvalidateAnnoConfig cacheInvalidateAnnoConfig) {
        this.cacheInvalidateAnnoConfig = cacheInvalidateAnnoConfig;
    }

    public static CacheInvokeConfig getNoCacheInvokeConfigInstance() {
        return noCacheInvokeConfigInstance;
    }
}
