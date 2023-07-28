package com.skl.test.distributedcache.core.local;

import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.local.GuavaCacheBuilder;
import org.junit.Test;

public class GuavaCacheBuilderTest {
    @Test
    public void buildCache(){
        GuavaCacheBuilder guavaCacheBuilder = GuavaCacheBuilder.createGuavaCacheBuilder();
        Cache cache = guavaCacheBuilder.buildCache();
        cache.put("skl","aaa");
        Object value = cache.get("skl");
        System.out.println(value);
    }
}
