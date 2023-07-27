package com.skl.distributedcache.external.redis.test;

import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.external.redis.RedisCacheBuilder;
import org.junit.Test;

public class RedisCacheBuilderTest {
    @Test
    public void buildCache(){
        RedisCacheBuilder redisCacheBuilder = RedisCacheBuilder.createRedisCacheBuilder();
        Cache cache = redisCacheBuilder.buildCache();
        System.out.println(cache);
        cache.get("x");
    }
}
