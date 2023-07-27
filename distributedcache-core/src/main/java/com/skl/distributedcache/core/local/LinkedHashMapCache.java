package com.skl.distributedcache.core.local;

import java.util.LinkedHashMap;

public class LinkedHashMapCache<K,V>  extends AbstractLocalCache<K,V> {
    private LocalCacheConfig cacheConfig;

    public LinkedHashMapCache(LocalCacheConfig cacheConfig){
        super(cacheConfig);
        this.cacheConfig = cacheConfig;
    }

    @Override
    protected InnerMap createAreaCache() {
        LinkedHashMap linkedHashMap = new LinkedHashMap<>();
        return new InnerMap() {
            @Override
            public Object getObject(Object key) {
                return linkedHashMap.get(key);
            }

            @Override
            public void putObject(Object key, Object value) {
                linkedHashMap.put(key,value);
            }

            @Override
            public void removeObject(Object key) {
                linkedHashMap.remove(key);
            }
        };
    }


}
