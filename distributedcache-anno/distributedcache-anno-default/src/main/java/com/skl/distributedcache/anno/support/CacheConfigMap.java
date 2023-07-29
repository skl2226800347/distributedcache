package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.anno.api.CacheConstants;
import com.skl.distributedcache.anno.api.CacheType;
import com.skl.distributedcache.anno.method.CachedAnnoConfig;
import com.skl.distributedcache.anno.method.CacheInvokeConfig;
import com.skl.distributedcache.core.exception.CacheConfigException;
import com.skl.distributedcache.core.utils.CollectionUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
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

    public boolean isContainLocaclCache(){
        if(CollectionUtil.isEmpty(cacheNameMap)){
            return false;
        }
        Iterator<Map.Entry<String,CacheInvokeConfig>> iterator = this.cacheNameMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String,CacheInvokeConfig> entry = iterator.next();
            CacheInvokeConfig cacheInvokeConfig = entry.getValue();
            if (cacheInvokeConfig == null) {
                continue;
            }
            CachedAnnoConfig cachedAnnoConfig = cacheInvokeConfig.getCachedAnnoConfig();
            if (cachedAnnoConfig == null) {
                continue;
            }
            if (CacheType.LOCAL == cachedAnnoConfig.getCacheType() || CacheType.BOTH == cachedAnnoConfig.getCacheType()) {
                return true;
            }
        }
        return false;
    }
}
