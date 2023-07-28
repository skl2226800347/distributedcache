package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.anno.api.CacheConstants;
import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.local.AbstractLocalCacheBuilder;
import com.skl.distributedcache.core.utils.StringUtils;

public abstract class AbstractLocalCacheAutoInit extends AbstractCacheAutoInit{

    public AbstractLocalCacheAutoInit(String... cacheTypes){
        super(cacheTypes);
    }
    @Override
    protected void parseGenernalCacheConfig(ConfigTree configTree, CacheBuilder cacheBuilder) {
        super.parseGenernalCacheConfig(configTree, cacheBuilder);
        AbstractLocalCacheBuilder abstractLocalCacheBuilder=(AbstractLocalCacheBuilder) cacheBuilder;
        abstractLocalCacheBuilder.setLimit(Integer.parseInt(configTree.getProperty("limit",String.valueOf(CacheConstants.DEFAULT_LOCAL_LIMIT))));
    }
}
