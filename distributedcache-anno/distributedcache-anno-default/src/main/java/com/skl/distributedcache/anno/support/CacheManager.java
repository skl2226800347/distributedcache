package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.core.Cache;

public interface CacheManager {
    Cache getCache(String area, String name);

    void putCache(String area,String name,Cache cache);

    static  CacheManager getDefaultCacheManager(){
        return DefaultCacheManager.defaultCacheManager;
    }


}
