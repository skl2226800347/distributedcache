package com.skl.distributedcache.core;

import com.skl.distributedcache.core.config.CacheConfig;
import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;

import java.util.function.Function;

public interface Cache<K,V> {

    V get(K key);

    void put(K key,V value);

    CacheConfig getCacheConfig();

    V computeIfAbsent(K key, Function<K,V> loaderFunc);

    V computeIfAbsent(K key,Function<K,V> loaderFunc,boolean cacheNullWhenLoaderReturnNull);

    CacheGetResult<V> GET(K key);

    CacheResult PUT(K key, V value);

    default boolean remove(K key){
        return REMOVE(key).isSuccess();
    }
    CacheResult REMOVE(K key);
}
