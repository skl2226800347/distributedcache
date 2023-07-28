package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.local.GuavaCacheBuilder;

public class GuavaAutoConfiguration extends AbstractLocalCacheAutoInit{
    private static final String CACHE_TYPE="guava";

    public GuavaAutoConfiguration(){
        super(CACHE_TYPE);
    }
    @Override
    protected CacheBuilder initCacheBuilder(ConfigTree configTree, String cacheAreaWithPrefix) {
        GuavaCacheBuilder guavaCacheBuilder = GuavaCacheBuilder.createGuavaCacheBuilder();
        parseGenernalCacheConfig(configTree,guavaCacheBuilder);
        return guavaCacheBuilder;
    }

    public static class GuavaCondition extends DistributecacheCondition{
        public GuavaCondition(){
            super(CACHE_TYPE);
        }
    }
}
