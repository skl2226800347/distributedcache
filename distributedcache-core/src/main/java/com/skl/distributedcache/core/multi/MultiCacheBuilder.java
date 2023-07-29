package com.skl.distributedcache.core.multi;

import com.skl.distributedcache.core.AbstractCacheBuilder;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.config.CacheConfig;

import java.util.Objects;

public class MultiCacheBuilder<T extends AbstractCacheBuilder<T>> extends AbstractCacheBuilder<T> {

    public static class MultiCacheBuiderImpl extends MultiCacheBuilder<MultiCacheBuiderImpl>{

    }

    public static final MultiCacheBuilder createMultiCacheBuider(){
        MultiCacheBuilder multiCacheBuilder = new MultiCacheBuilder();
        multiCacheBuilder.buildFunc((x)->new MultiCache<>((MutliCacheConfig)x));
        return multiCacheBuilder;
    }

    @Override
    public MutliCacheConfig getCacheConfig() {
        if (cacheConfig == null){
            cacheConfig = new MutliCacheConfig();
        }
        return (MutliCacheConfig)cacheConfig;
    }

    public T addCaches(Cache...caches){
        Objects.requireNonNull(caches);

        for(Cache cache : caches){
            getCacheConfig().getCaches().add(cache);
        }
        return self();
    }

}
