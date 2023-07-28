package com.skl.distributedcache.core.local;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

public class GuavaCache<K,V> extends AbstractLocalCache<K,V>{
    private Cache<Object,Object> cache;
    public GuavaCache(LocalCacheConfig cacheConfig){
        super(cacheConfig);
    }

    @Override
    protected InnerMap createAreaCache() {
        CacheBuilder builder= CacheBuilder.newBuilder();
        builder.maximumSize(cacheConfig.getLimit());
        if(cacheConfig.isExpireAfterAccess()) {
            builder.expireAfterAccess(cacheConfig.getExpireAfterAccessInMillis(), TimeUnit.MILLISECONDS);
        }
        cache=builder.build();

        return new InnerMap() {
            @Override
            public Object getObject(Object key) {
                return cache.getIfPresent(key);
            }

            @Override
            public void putObject(Object key, Object value) {
                cache.put(key,value);
            }

            @Override
            public void removeObject(Object key) {
                cache.asMap().remove(key);
            }
        };
    }
}
