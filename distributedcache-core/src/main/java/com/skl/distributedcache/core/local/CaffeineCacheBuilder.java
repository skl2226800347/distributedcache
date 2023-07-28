package com.skl.distributedcache.core.local;

public class CaffeineCacheBuilder<T extends AbstractLocalCacheBuilder<T>> extends AbstractLocalCacheBuilder<T> {
    public static class CaffeineCacheBuilderImpl extends CaffeineCacheBuilder<CaffeineCacheBuilderImpl>{

    }
    public static final CaffeineCacheBuilder createCaffeineCacheBuilder(){
        CaffeineCacheBuilderImpl cacheBuilder = new CaffeineCacheBuilderImpl();
        cacheBuilder.buildFunc(c->{
            return new CaffeineCache((LocalCacheConfig)c);
        });
        return cacheBuilder;
    }
}
