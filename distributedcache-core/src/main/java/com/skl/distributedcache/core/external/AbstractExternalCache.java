package com.skl.distributedcache.core.external;

import com.skl.distributedcache.core.AbstractCache;
import com.skl.distributedcache.core.exception.CacheException;
import com.skl.distributedcache.core.support.ExternalKeyUtil;

import java.io.IOException;
import java.util.function.Function;

public abstract class AbstractExternalCache<K,V> extends AbstractCache<K,V> {
    private ExternalCacheConfig cacheConfig;
    public AbstractExternalCache(ExternalCacheConfig cacheConfig){
        this.cacheConfig = cacheConfig;
    }

    @Override
    public ExternalCacheConfig getCacheConfig() {
        return this.cacheConfig;
    }

    protected byte[] buildKey(String prefix,Object key){
        try {
            if (key == null) {
                return null;
            }
            Object newKey;
            if (key instanceof String) {
                newKey = key;
            } else if (key instanceof byte[]) {
                newKey = key;
            } else {
                Function<Object, Object> keyConvertor = getCacheConfig().getKeyConvertor();
                newKey = key;
                if (keyConvertor != null) {
                    newKey = keyConvertor.apply(key);
                }
            }
            return ExternalKeyUtil.buildKeyAfterConvert(prefix, newKey);
        }catch (IOException e){
            throw new CacheException(e);
        }
    }
}
