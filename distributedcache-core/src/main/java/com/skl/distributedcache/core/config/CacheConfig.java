package com.skl.distributedcache.core.config;

import java.io.Serializable;
import java.util.function.Function;

public class CacheConfig<K,V> implements Serializable,Cloneable {

    private Function<K,Object> keyConvertor;

    private long expireAfterWriteInMillis;

    boolean cacheNullValue = false;

    public boolean isCacheNullValue(){
        return cacheNullValue;
    }

    public Function<K, Object> getKeyConvertor() {
        return keyConvertor;
    }

    public void setKeyConvertor(Function<K, Object> keyConvertor) {
        this.keyConvertor = keyConvertor;
    }

    public long getExpireAfterWriteInMillis() {
        return expireAfterWriteInMillis;
    }

    public void setExpireAfterWriteInMillis(long expireAfterWriteInMillis) {
        this.expireAfterWriteInMillis = expireAfterWriteInMillis;
    }
}
