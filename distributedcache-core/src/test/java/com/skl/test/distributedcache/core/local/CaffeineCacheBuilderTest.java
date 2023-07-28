package com.skl.test.distributedcache.core.local;

import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.local.CaffeineCacheBuilder;
import org.junit.Test;

public class CaffeineCacheBuilderTest {

    @Test
    public void buildCache(){
        CaffeineCacheBuilder caffeineCacheBuilder =CaffeineCacheBuilder.createCaffeineCacheBuilder();
        caffeineCacheBuilder.expireAfterWriteInMillis(100000000);
        Cache<Object,Object> cache = caffeineCacheBuilder.buildCache();
        cache.put("hero123","shi");
        Object value = cache.get("hero123");
        System.out.println(value);
    }
}
