package com.skl.distributedcache.core;

import java.io.Serializable;

public class CacheValueHolder<V> implements Serializable {

    private long accessTime;
    private long expireTime;
    private static final long serialVersionUID = -5596199778888920791L;
    private V value;

    public CacheValueHolder(){

    }
    public CacheValueHolder(V value,long expireAfterWrite){
        this.value =value;
        this.accessTime = System.currentTimeMillis();
        this.expireTime = accessTime+expireAfterWrite;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
