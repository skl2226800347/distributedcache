package com.skl.distributedcache.core.local;

import com.skl.distributedcache.core.AbstractCache;
import com.skl.distributedcache.core.CacheValueHolder;
import com.skl.distributedcache.core.config.CacheConfig;
import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;

import java.util.function.Function;

public abstract class AbstractLocalCache<K,V> extends AbstractCache<K,V> {
    protected InnerMap innerMap;
    private LocalCacheConfig cacheConfig;


    public AbstractLocalCache(LocalCacheConfig cacheConfig){
        this.cacheConfig = cacheConfig;
        innerMap = createAreaCache();
    }

    protected abstract InnerMap createAreaCache();

    @Override
    protected CacheGetResult<V> doGET(K key) {
        CacheValueHolder<V> cacheValueHolder = (CacheValueHolder<V>)innerMap.getObject(buildKey(key));
        if(cacheValueHolder == null) {
            return CacheGetResult.NOT_EXIST;
        }else {
            return CacheGetResult.createSuccess(cacheValueHolder);
        }
    }

    public Object buildKey(Object key){
        if(key == null){
            return key;
        }
        Function<Object,Object> keyConvertor = getCacheConfig().getKeyConvertor();
        if (keyConvertor != null){
            return keyConvertor.apply(key);
        }
        return key;
    }

    @Override
    protected CacheResult doPut(K key, V value) {
        CacheValueHolder holder = CacheValueHolder.createCacheValueHolder(value);
        innerMap.putObject(buildKey(key),holder);
        return CacheResult.createSuccess();
    }

    @Override
    protected CacheResult doRemove(K key) {
        innerMap.removeObject(buildKey(key));
        return CacheResult.createSuccess();
    }

    @Override
    public CacheConfig getCacheConfig() {
        return this.cacheConfig;
    }
}
