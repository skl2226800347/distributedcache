package com.skl.distributedcache.core.loader;

import com.skl.distributedcache.core.exception.CacheInvokeException;

import java.util.function.Function;

public interface CacheLoader<K,V> extends Function<K,V> {
    V load(K key) throws Throwable;

    @Override
    default V apply(K k){
        try {
            return load(k);
        }catch (Throwable e){
            e.printStackTrace();
            throw new CacheInvokeException(e.getMessage(),e);
        }
    }
}
