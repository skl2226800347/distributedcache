package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.anno.api.CacheConstants;
import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.external.AbstractExternalCacheBuilder;

public abstract class AbstractExternalCacheAutoInit extends AbstractCacheAutoInit{

    public AbstractExternalCacheAutoInit(String ...cacheTypes){
        super(cacheTypes);

    }
    @Override
    protected void parseGenernalCacheConfig(ConfigTree configTree, CacheBuilder cacheBuilder) {
        super.parseGenernalCacheConfig(configTree, cacheBuilder);
        AbstractExternalCacheBuilder aecb = (AbstractExternalCacheBuilder)cacheBuilder;
        aecb.valueEncoder(configProvider.parseValueEncoder(configTree.getProperty("valueEncoder", CacheConstants.DEFAULT_SERIAL_POLICY)));
        aecb.valueDecoder(configProvider.parseValueDecoder(configTree.getProperty("valueDecoder",CacheConstants.DEFAULT_SERIAL_POLICY)));
        aecb.keyPrefix(configTree.getProperty("keyPrefix"));
    }
}
