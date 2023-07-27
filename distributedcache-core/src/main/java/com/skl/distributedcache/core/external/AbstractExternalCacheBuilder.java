package com.skl.distributedcache.core.external;

import com.skl.distributedcache.core.AbstractCacheBuilder;
import com.skl.distributedcache.core.config.CacheConfig;

import java.util.function.Function;

public abstract class AbstractExternalCacheBuilder<T extends AbstractExternalCacheBuilder<T>> extends AbstractCacheBuilder<T> {

    @Override
    public ExternalCacheConfig getCacheConfig() {
        if (cacheConfig == null){
            cacheConfig = new ExternalCacheConfig();
        }
        return (ExternalCacheConfig)cacheConfig;
    }

    public T keyPrefix(String keyPrefix){
        getCacheConfig().setKeyPrefix(keyPrefix);
        return self();
    }

    public T valueEncoder(Function<Object,byte[]> valueEncoder){
        getCacheConfig().setValueEncoder(valueEncoder);
        return self();
    }

    public T valueDecoder(Function<byte[],Object> valueDecoder){
        getCacheConfig().setValueDecoder(valueDecoder);
        return self();
    }



}
