package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.core.Cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultCacheManager implements CacheManager{
    private ConcurrentMap<String,ConcurrentMap<String,Cache>> cacheMap = new ConcurrentHashMap<>();

    public static CacheManager defaultCacheManager = new DefaultCacheManager();

    public DefaultCacheManager(){

    }
    @Override
    public Cache getCache(String area, String name) {
        ConcurrentMap<String,Cache> caches =  cacheMap.computeIfAbsent(area,x-> new ConcurrentHashMap<>());
        return caches.get(name);
    }

    @Override
    public void putCache(String area, String name, Cache cache) {
        ConcurrentMap<String,Cache> caches =  cacheMap.computeIfAbsent(area,x-> new ConcurrentHashMap<>());
        caches.put(name,cache);
    }
}
