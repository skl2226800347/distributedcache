package com.skl.distributedcache.core.multi;

import com.skl.distributedcache.core.AbstractCache;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;

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
    protected CacheResult doPut(K key, V value) {
        for(Cache cache : caches) {
            CacheResult result = cache.PUT(key,value);
            if(!result.isSuccess()){
                return result;
            }
        }
        return CacheResult.SUCCESS;
    }

    @Override
    protected CacheResult doRemove(K key) {
        for(Cache cache : caches) {
            CacheResult result = cache.REMOVE(key);
            if(!result.isSuccess()) {
                return result;
            }
        }
        return CacheResult.SUCCESS;
    }

    @Override
    public MutliCacheConfig getCacheConfig() {
        return this.cacheConfig;
    }
}
