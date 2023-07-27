package com.skl.test.distributedcache.core.local;

import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.local.LinkedHashMapCacheBuilder;
import org.junit.Test;

public class LinkedHashMapLocalCacheBuilderTest<K,V> {
    @Test
    public void buildCache(){
        LinkedHashMapCacheBuilder linkedHashMapCacheBuilder = LinkedHashMapCacheBuilder.createCacheBuilder();
        Cache<K,V> cache = linkedHashMapCacheBuilder.buildCache();
        cache.get((K)"1");
    }
}
