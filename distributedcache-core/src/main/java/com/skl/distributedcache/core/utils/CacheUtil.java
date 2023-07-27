package com.skl.distributedcache.core.utils;

import com.skl.distributedcache.core.AbstractCache;
import com.skl.distributedcache.core.Cache;

import java.util.function.Function;

public class CacheUtil<K,V> {
    public static final AbstractCache getAbstractCache(Cache cache){
        if (cache instanceof AbstractCache){
            return (AbstractCache) cache;
        }
        return null;
    }

    public static final <K,V> Function<K,V> createProxyLoader(Function<K,V> loader){
        return (k)->{
            V v = loader.apply(k);
            return v;
        };
    }
}
