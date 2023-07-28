package com.skl.distributedcache.core.multi;

import com.skl.distributedcache.core.AbstractCache;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;

import java.util.concurrent.TimeUnit;

/**
 * multi cache class
 * @author skl
 */
public class MultiCache<K,V> extends AbstractCache<K,V> {
    Cache<K,V>[] caches ;
    MutliCacheConfig cacheConfig;


    public MultiCache(MutliCacheConfig cacheConfig){
        this.cacheConfig = cacheConfig;
        this.caches = cacheConfig.getCaches().toArray(new Cache[]{});
    }

    @Override
    protected CacheGetResult<V> doGET(K key) {
        for(Cache cache : caches){
            CacheGetResult result = cache.GET(key);
            if(result.isSuccess()){
                return result;
            }
        }
        return CacheGetResult.NOT_EXIST;
    }

    @Override
    protected CacheResult doPut(K key, V value, long expire, TimeUnit timeUnit) {
        for(Cache cache : caches) {
            CacheResult result;
            if(timeUnit == null) {
                result = cache.PUT(key, value);
            }else{
                result = cache.PUT(key,value,expire,timeUnit);
            }
            if(!result.isSuccess()){
                return result;
            }
        }
        return CacheResult.SUCCESS_WITHOUT_MSG;
    }

    @Override
    protected CacheResult doRemove(K key) {
        for(Cache cache : caches) {
            CacheResult result = cache.REMOVE(key);
            if(!result.isSuccess()) {
                return result;
            }
        }
        return CacheResult.SUCCESS_WITHOUT_MSG;
    }

    @Override
    public MutliCacheConfig getCacheConfig() {
        return this.cacheConfig;
    }
}
