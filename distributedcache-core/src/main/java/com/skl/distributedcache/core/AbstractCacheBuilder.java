package com.skl.distributedcache.core;

import com.skl.distributedcache.core.config.CacheConfig;
import com.skl.distributedcache.core.exception.CacheConfigException;
import com.skl.distributedcache.core.external.ExternalCacheConfig;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public abstract class AbstractCacheBuilder<T extends AbstractCacheBuilder<T>> implements CacheBuilder{
    protected CacheConfig cacheConfig;
    private Function<CacheConfig,Cache> buildFunc;


    public void buildFunc(Function<CacheConfig,Cache> buildFunc) {
        this.buildFunc = buildFunc;
    }

    public abstract CacheConfig getCacheConfig();

    public T self(){
        return (T)this;
    }
    @Override
    public final <K,V> Cache<K,V> buildCache() {
        if (buildFunc == null){
            throw new CacheConfigException("buildFun is null");
        }
        CacheConfig cacheConfig = getCacheConfig();
        Cache cache = buildFunc.apply(cacheConfig);
        return cache;
    }

    public T keyConvertor(Function<Object,Object> keyConvertor){
        getCacheConfig().setKeyConvertor(keyConvertor);
        return self();
    }

    public T expireAfterWriteInMillis(long expireAfterWriteInMillis){
        getCacheConfig().setExpireAfterWriteInMillis(expireAfterWriteInMillis);
        return self();
    }

    public T expireAfterWriteInMillis(long expireAfterWriteInMillis, TimeUnit timeUnit){
        getCacheConfig().setExpireAfterWriteInMillis(timeUnit.toMillis(expireAfterWriteInMillis));
        return self();
    }

    public T expireAfterAccessInMillis(long expireAfterAccessInMillis){
        getCacheConfig().setExpireAfterAccessInMillis(expireAfterAccessInMillis);
        return self();
    }
}
