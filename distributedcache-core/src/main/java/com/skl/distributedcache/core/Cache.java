package com.skl.distributedcache.core;

import com.skl.distributedcache.core.config.CacheConfig;
import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface Cache<K,V> {

    V get(K key);

    default void put(K key,V value){
        PUT(key,value);
    }

    default CacheResult PUT(K key,V value){
        if(key == null){
            return CacheResult.FAIL_ERROR_PARAM;
        }
        return PUT(key,value,getCacheConfig().getExpireAfterWriteInMillis(),TimeUnit.MILLISECONDS);
    }

    CacheConfig getCacheConfig();

    V computeIfAbsent(K key, Function<K,V> loaderFunc);

    V computeIfAbsent(K key,Function<K,V> loaderFunc,boolean cacheNullWhenLoaderReturnNull);

    CacheGetResult<V> GET(K key);

    CacheResult PUT(K key, V value , long expireAfterWrite, TimeUnit timeUnit);

    default boolean remove(K key){
        return REMOVE(key).isSuccess();
    }
    CacheResult REMOVE(K key);
}
