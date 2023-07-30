package com.skl.distributedcache.core.multi;

import com.skl.distributedcache.anno.api.CacheConstants;
import com.skl.distributedcache.core.AbstractCache;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.CacheValueHolder;
import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;

import java.util.Objects;
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
        for(int i=0;i<caches.length;i++){
            Cache cache = caches[i];
            CacheGetResult result = cache.GET(key);
            if(result.isSuccess()){
                CacheValueHolder<V> holder=result.getHolder();
                checkResultAndFillUpperCache(key,i,holder);
                return result;
            }
        }
        return CacheGetResult.NOT_EXIST;
    }

    private void checkResultAndFillUpperCache(K key,int i,CacheValueHolder<V> holder){
        Objects.requireNonNull(holder);
        long currentExpire = holder.getExpireTime();
        long now = System.currentTimeMillis();
        if(now<=currentExpire){
            long internal =currentExpire - now;
            if(internal>0){
                PUT_caches(i,key,holder.getValue(),internal,TimeUnit.MILLISECONDS);
            }
        }
    }

    private void PUT_caches(int lastIndex,K key, V value,long expire,TimeUnit timeUnit){
        for(int i=0;i<lastIndex;i++){
            Cache cache = caches[i];
            if(timeUnit == null){
                cache.PUT(key,value,false);
            }else{
                cache.PUT(key,value,expire,timeUnit,false);
            }
        }
    }

    @Override
    protected CacheResult doPut(K key, V value, long expire, TimeUnit timeUnit,boolean onlyPutLocal) {
        for(int i=0;i<caches.length;i++) {
            if(onlyPutLocal && i>CacheConstants.ZERO){
                break;
            }
            Cache cache = caches[i];
            CacheResult result;
            if(timeUnit == null) {
                result = cache.PUT(key, value,onlyPutLocal);
            }else{
                result = cache.PUT(key,value,expire,timeUnit,onlyPutLocal);
            }
            if(!result.isSuccess()){
                return result;
            }
        }
        return CacheResult.SUCCESS_WITHOUT_MSG;
    }

    @Override
    protected CacheResult doRemove(K key,boolean onlyRemoveLocal) {
        for(int i=0;i<caches.length;i++) {
            if(onlyRemoveLocal && i>CacheConstants.ZERO){
                break;
            }
            Cache cache = caches[i];
            CacheResult result = cache.REMOVE(key,onlyRemoveLocal);
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
