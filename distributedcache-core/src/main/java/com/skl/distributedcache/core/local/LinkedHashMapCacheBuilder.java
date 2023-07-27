package com.skl.distributedcache.core.local;

public class LinkedHashMapCacheBuilder<T extends AbstractLocalCacheBuilder<T>> extends AbstractLocalCacheBuilder<T>{
    public static class LinkedHashMapCacheBuilderImpl extends LinkedHashMapCacheBuilder<LinkedHashMapCacheBuilderImpl>{

    }

    public static final LinkedHashMapCacheBuilder createCacheBuilder(){
        LinkedHashMapCacheBuilderImpl cacheBuilder = new LinkedHashMapCacheBuilderImpl();
        cacheBuilder.buildFunc(c->{
            return new LinkedHashMapCache((LocalCacheConfig)c);
        });
        return cacheBuilder;
    }
}
