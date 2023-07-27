package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.anno.api.CacheConstants;
import com.skl.distributedcache.anno.method.CachedAnnoConfig;
import com.skl.distributedcache.anno.method.CacheInvokeConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CacheConfigMap {
    private static final String SPLIT ="_";
    public  ConcurrentMap<String, CacheInvokeConfig> methodInfoMap = new ConcurrentHashMap<>();
    public  ConcurrentMap<String,CacheInvokeConfig> cacheNameMap = new ConcurrentHashMap<>();

    public  CacheInvokeConfig getByMethodInfo(String key){
        return methodInfoMap.get(key);
    }

    public void putByMethodInfo(String key,CacheInvokeConfig cacheInvokeConfig) {
        methodInfoMap.put(key,cacheInvokeConfig);
        CachedAnnoConfig cacheAnnoConfig = cacheInvokeConfig.getCachedAnnoConfig();
        if(cacheAnnoConfig != null && !CacheConstants.isUndefined(cacheAnnoConfig.getName())) {
            cacheNameMap.put(getKey(cacheAnnoConfig.getArea(),cacheAnnoConfig.getName()),cacheInvokeConfig);
        }
    }
    public CacheInvokeConfig getByCacheName(String area,String name){
        return cacheNameMap.get(getKey(area,name));
    }

    private String getKey(String area,String name){
        StringBuilder key = new StringBuilder();
        key.append(area).append(SPLIT).append(name);
        return key.toString();
    }
}
