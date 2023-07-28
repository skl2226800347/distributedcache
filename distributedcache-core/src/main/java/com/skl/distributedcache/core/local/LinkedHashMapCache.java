package com.skl.distributedcache.core.local;

import java.util.LinkedHashMap;

public class LinkedHashMapCache<K,V>  extends AbstractLocalCache<K,V> {

    public LinkedHashMapCache(LocalCacheConfig cacheConfig){
        super(cacheConfig);
    }

    @Override
    protected InnerMap createAreaCache() {
        return new LRUMap(cacheConfig.getLimit(),this);
    }

    class LRUMap  extends LinkedHashMap implements InnerMap{
        private int max;
        private Object lock;
        public LRUMap(int max,Object lock){
            super((int)(max * 1.4f),0.75f,true);
            this.max = max;
            this.lock = lock;
        }
        @Override
        public Object getObject(Object key) {
            synchronized (lock){
                return get(key);
            }
        }

        @Override
        public void putObject(Object key, Object value) {
            synchronized (lock){
                put(key,value);
            }
        }

        @Override
        public void removeObject(Object key) {
            synchronized (lock){
                remove(key);
            }
        }
    }


}
