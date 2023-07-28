package com.skl.distributedcache.core;

import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;
import com.skl.distributedcache.core.utils.CacheUtil;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractCache<K,V> implements Cache<K,V>{

    @Override
    public V get(K key) {
        CacheGetResult<V> cacheGetResult = GET(key);
        if(cacheGetResult != null){
            return cacheGetResult.getValue();
        }else{
            return null;
        }
    }

    @Override
    public void put(K key, V value) {
        PUT(key,value);
    }

    @Override
    public V computeIfAbsent(K key, Function<K, V> loaderFunc) {
        return computeIfAbsent(key,loaderFunc,getCacheConfig().isCacheNullValue());
    }

    @Override
    public V computeIfAbsent(K key, Function<K, V> loaderFunc, boolean cacheNullWhenLoaderReturnNull) {
        return computeIfAbsentImpl(key,loaderFunc,cacheNullWhenLoaderReturnNull,0,null,this);
    }


    public V computeIfAbsentImpl(K key, Function<K,V> loader, boolean cacheNullWhenLoaderReturnNull, long expireAfterWrite, TimeUnit
                                 timeUnit,Cache cache){
        AbstractCache abstractCache = CacheUtil.getAbstractCache(cache);
        Function<K,V> newLoader = CacheUtil.createProxyLoader(loader);
        CacheGetResult<V> result = GET(key);
        if (result.isSuccess()){
            return result.getValue();
        }
        Consumer updateConsumer = (newValue)->{
            if(timeUnit != null){
                cache.PUT(key, (V) newValue, expireAfterWrite, timeUnit);
            }else {
                cache.PUT(key,newValue);
            }
        };
        V newValue = newLoader.apply(key);
        updateConsumer.accept(newValue);
        return newValue;
    }

    protected abstract CacheGetResult<V> doGET(K key);
    protected abstract CacheResult  doPut(K key,V value,long expireAfter,TimeUnit timeUnit);

    @Override
    public CacheGetResult<V> GET(K key) {
        CacheGetResult result = doGET(key);
         return result;
    }

    @Override
    public CacheResult PUT(K key, V value,long expireAfterWrite,TimeUnit timeUnit) {
        if(key == null){
            return CacheResult.FAIL_WITHOUT_MSG;
        }
        CacheResult result = doPut(key,value,expireAfterWrite,timeUnit);
        return result;
    }

    @Override
    public CacheResult REMOVE(K key) {
        if(key == null){
            return CacheResult.FAIL_ERROR_PARAM;
        }
        return doRemove(key);
    }
    protected abstract CacheResult doRemove(K key);
}
