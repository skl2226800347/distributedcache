package com.skl.distributedcache.core.local;

import com.skl.distributedcache.core.AbstractCacheBuilder;
import com.skl.distributedcache.core.config.CacheConfig;

public abstract class AbstractLocalCacheBuilder<T extends AbstractCacheBuilder<T>> extends AbstractCacheBuilder<T> {
    @Override
    public CacheConfig getCacheConfig() {
        if(cacheConfig == null){
            cacheConfig = new LocalCacheConfig();
        }
        return cacheConfig;
    }
}
