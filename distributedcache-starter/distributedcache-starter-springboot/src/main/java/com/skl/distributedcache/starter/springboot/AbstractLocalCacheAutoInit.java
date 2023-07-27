package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.core.CacheBuilder;

public abstract class AbstractLocalCacheAutoInit extends AbstractCacheAutoInit{

    public AbstractLocalCacheAutoInit(String... cacheTypes){
        super(cacheTypes);
    }
    @Override
    protected void parseGenernalCacheConfig(ConfigTree configTree, CacheBuilder cacheBuilder) {
        super.parseGenernalCacheConfig(configTree, cacheBuilder);
    }
}
