package com.skl.distributedcache.core.multi;

import com.skl.distributedcache.core.AbstractCacheBuilder;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.config.CacheConfig;

public class MultiCacheBuilder<T extends AbstractCacheBuilder<T>> extends AbstractCacheBuilder<T> {
    private MutliCacheConfig cacheConfig;
    public static class MultiCacheBuiderImpl extends MultiCacheBuilder<MultiCacheBuiderImpl>{

    }

    public static final MultiCacheBuilder createMultiCacheBuider(){
        MultiCacheBuilder multiCacheBuilder = new MultiCacheBuilder();
        multiCacheBuilder.buildFunc((x)->new MultiCache<>((MutliCacheConfig)x));
        return multiCacheBuilder;
    }

    @Override
    public CacheConfig getCacheConfig() {
        if (cacheConfig == null){
            cacheConfig = new MutliCacheConfig();
        }
        return cacheConfig;
    }

    public T addCaches(Cache...caches){
        getCacheConfig();
        this.cacheConfig.addCache(caches);
        return self();
    }

}
