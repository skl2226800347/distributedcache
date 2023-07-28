package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.local.CaffeineCacheBuilder;

public class CaffeineAutoConfiguration extends AbstractLocalCacheAutoInit{
    private static final String CACHE_TYPE ="caffeine";
    public CaffeineAutoConfiguration(){
        super(CACHE_TYPE);
    }
    @Override
    protected CacheBuilder initCacheBuilder(ConfigTree configTree, String cacheAreaWithPrefix) {
        CaffeineCacheBuilder caffeineCacheBuilder = CaffeineCacheBuilder.createCaffeineCacheBuilder();
        parseGenernalCacheConfig(configTree,caffeineCacheBuilder);
        return caffeineCacheBuilder;
    }

    public static class CaffeineCondition extends DistributecacheCondition{
        public CaffeineCondition(){
            super(CACHE_TYPE);
        }
    }
}
