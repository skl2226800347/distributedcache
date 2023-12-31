package com.skl.distributedcache.core.config;

import com.skl.distributedcache.anno.api.CacheConstants;

import java.io.Serializable;
import java.util.function.Function;

public class CacheConfig<K,V> implements Serializable,Cloneable {

    private Function<K,Object> keyConvertor;

    private long expireAfterWriteInMillis = CacheConstants.DEFAULT_EXPIRE * 1000;
    private long expireAfterAccessInMillis = 0;

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

    public boolean isExpireAfterAccess(){
        return expireAfterAccessInMillis>0;
    }
    public long getExpireAfterAccessInMillis() {
        return expireAfterAccessInMillis;
    }

    public void setExpireAfterAccessInMillis(long expireAfterAccessInMillis) {
        this.expireAfterAccessInMillis = expireAfterAccessInMillis;
    }
}
