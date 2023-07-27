package com.skl.test.distributedcache.test.core;

import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.loader.CacheLoader;
import com.skl.distributedcache.core.local.LinkedHashMapCacheBuilder;
import com.skl.distributedcache.core.multi.MultiCache;
import com.skl.distributedcache.core.multi.MultiCacheBuilder;
import com.skl.distributedcache.external.redis.RedisCacheBuilder;
import org.junit.Test;

import java.util.Map;

public class MutliCacheBuilderTest {
    @Test
    public void buildCache(){
        MultiCacheBuilder multiCacheBuilder = MultiCacheBuilder.createMultiCacheBuider();
        MultiCache multiCache = (MultiCache)multiCacheBuilder.buildCache();
    }
    @Test
    public void buidCacheBoth(){
        LinkedHashMapCacheBuilder linkedHashMapCacheBuilder = LinkedHashMapCacheBuilder.createCacheBuilder();
        Cache localCache = linkedHashMapCacheBuilder.buildCache();

        RedisCacheBuilder redisCacheBuilder = RedisCacheBuilder.createRedisCacheBuilder();
        Cache externalCache = redisCacheBuilder.buildCache();

        MultiCacheBuilder multiCacheBuilder = MultiCacheBuilder.createMultiCacheBuider();
        MultiCache multiCache = (MultiCache)multiCacheBuilder.addCaches(localCache,externalCache).buildCache();

        String key="123";
        CacheLoader cacheLoader = new CacheLoader(){
            @Override
            public Object load(Object key) throws Throwable {
                return "你好";
            }
        };
        Object value = multiCache.computeIfAbsent(key,cacheLoader);
        System.out.println(value);
        value = multiCache.computeIfAbsent(key,cacheLoader);
        System.out.println(value);

    }
}
