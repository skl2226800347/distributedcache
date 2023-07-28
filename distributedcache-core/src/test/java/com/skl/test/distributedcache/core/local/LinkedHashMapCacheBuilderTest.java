package com.skl.test.distributedcache.core.local;

import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.local.LinkedHashMapCacheBuilder;
import com.skl.distributedcache.core.local.LocalCacheConfig;
import org.junit.Test;

public class LinkedHashMapCacheBuilderTest<K,V> {
    @Test
    public void buildCache(){
        LinkedHashMapCacheBuilder linkedHashMapCacheBuilder = LinkedHashMapCacheBuilder.createCacheBuilder();
        LocalCacheConfig localCacheConfig = new LocalCacheConfig();
        localCacheConfig.setLimit(10);
        localCacheConfig.setExpireAfterAccessInMillis(100000);
        linkedHashMapCacheBuilder.setLimit(10);
        //linkedHashMapCacheBuilder.getCacheConfig()

        Cache<Object,Object> cache = linkedHashMapCacheBuilder.buildCache();
        cache.put("123","hero");
        Object value =cache.get("123");
        System.out.println(value);
    }
}
