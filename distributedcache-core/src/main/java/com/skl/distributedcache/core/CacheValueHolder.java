package com.skl.distributedcache.core;

import java.io.Serializable;

public class CacheValueHolder<V> implements Serializable {
    private V value;

    public CacheValueHolder(V value){
        this.value =value;
    }

    public static final CacheValueHolder createCacheValueHolder(Object value){
        CacheValueHolder cacheValueHolder = new CacheValueHolder(value);
        return cacheValueHolder;
    }
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
