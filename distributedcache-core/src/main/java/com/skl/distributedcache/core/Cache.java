package com.skl.distributedcache.core;

import com.skl.distributedcache.core.config.CacheConfig;
import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface Cache<K,V> {

    default V get(K key){
        CacheGetResult<V> result = GET(key);
        if(result.isSuccess()){
            return result.getValue();
        }else{
            return null;
        }
    }


    default void put(K key,V value){
        PUT(key,value,false);
    }

    default void put(K key,V value,boolean onlyPutLocal){
        PUT(key,value,onlyPutLocal);
    }

    default CacheResult PUT(K key,V value,boolean onlyPutLocal){
        if(key == null){
            return CacheResult.FAIL_ERROR_PARAM;
        }
        return PUT(key,value,getCacheConfig().getExpireAfterWriteInMillis(),TimeUnit.MILLISECONDS,onlyPutLocal);
    }

    CacheResult PUT(K key, V value , long expireAfterWrite, TimeUnit timeUnit,boolean onlyPutLocal);

    CacheConfig getCacheConfig();

    V computeIfAbsent(K key, Function<K,V> loaderFunc);

    V computeIfAbsent(K key,Function<K,V> loaderFunc,boolean cacheNullWhenLoaderReturnNull);

    CacheGetResult<V> GET(K key);



    default boolean remove(K key,boolean onlyRemoveLocal){
        return REMOVE(key,onlyRemoveLocal).isSuccess();
    }
    default boolean remove(K key){
        return REMOVE(key,false).isSuccess();
    }
    CacheResult REMOVE(K key,boolean onlyRemoveLocal);
}
