package com.skl.test.distributedcache.core.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.skl.distributedcache.core.CacheValueHolder;
import com.skl.distributedcache.core.local.InnerMap;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CaffeineTest {

    @Test
    public void putCache(){
        Caffeine<Object,Object> caffeine = Caffeine.newBuilder();
        Cache cache = caffeine.build();
        cache.put("skl","is hero");
        Object value = cache.getIfPresent("hero");
        System.out.println(value);
    }

    @Test
    public void putCache_V2(){
        Caffeine<Object,Object> builder = Caffeine.newBuilder();
        builder.maximumSize(100);
        final boolean isExpireAfterAccess = true;
        final long expireAfterAccess = 10000;
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

        Cache cache = builder.build();


        InnerMap innerMap =  new InnerMap() {
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

        innerMap.putObject("skl",new CacheValueHolder("value123456",10000));
        CacheValueHolder value =(CacheValueHolder) innerMap.getObject("skl");
        System.out.println(value);
        System.out.println(value.getValue());
    }
}
