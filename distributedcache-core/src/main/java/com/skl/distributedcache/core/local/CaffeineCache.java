package com.skl.distributedcache.core.local;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.skl.distributedcache.core.CacheValueHolder;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

public class CaffeineCache<K,V> extends AbstractLocalCache<K,V> {
    private com.github.benmanes.caffeine.cache.Cache cache;
    public CaffeineCache(LocalCacheConfig cacheConfig){
        super(cacheConfig);
        this.cacheConfig = cacheConfig;
    }

    @Override
    protected InnerMap createAreaCache() {
        Caffeine<Object,Object> builder = Caffeine.newBuilder();
        builder.maximumSize(cacheConfig.getLimit());
        final boolean isExpireAfterAccess = cacheConfig.isExpireAfterAccess();
        final long expireAfterAccess = cacheConfig.getExpireAfterAccessInMillis();
        builder.expireAfter(new Expiry<Object, CacheValueHolder>() {
            private long getTimeInNanos(CacheValueHolder cacheValueHolder){
                long currentTime = System.currentTimeMillis();
                long internal =  cacheValueHolder.getExpireTime()-currentTime;
                if(isExpireAfterAccess){
                    internal = Math.min(internal,expireAfterAccess);
                }
                return TimeUnit.MILLISECONDS.toNanos(internal);
            }
            @Override
            public long expireAfterCreate(@NonNull Object o, @NonNull CacheValueHolder cacheValueHolder, long currentTime) {
                return getTimeInNanos(cacheValueHolder);
            }

            @Override
            public long expireAfterUpdate(@NonNull Object o, @NonNull CacheValueHolder cacheValueHolder, long l, @NonNegative long currentDuration) {
                return currentDuration;
            }

            @Override
            public long expireAfterRead(@NonNull Object o, @NonNull CacheValueHolder cacheValueHolder, long currentTime, @NonNegative long currentDuration) {
                return getTimeInNanos(cacheValueHolder);
            }
        });

        cache = builder.build();


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
