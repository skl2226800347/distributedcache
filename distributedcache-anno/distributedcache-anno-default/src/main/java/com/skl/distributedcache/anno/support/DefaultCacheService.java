package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.anno.api.CacheType;
import com.skl.distributedcache.anno.method.CacheAnnoConfig;
import com.skl.distributedcache.anno.method.CacheInvokeConfig;
import com.skl.distributedcache.anno.method.CachedAnnoConfig;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.exception.CacheConfigException;
import com.skl.distributedcache.core.multi.MultiCacheBuilder;

import java.util.Map;
import java.util.Objects;

public class DefaultCacheService {
    static DefaultCacheService DEFAULT_CACHE_SERVICE;
    private CacheManager cacheManager = CacheManager.getDefaultCacheManager();
    private CacheConfigMap cacheConfigMap;
    private Map<String,CacheBuilder> localCacheBuilders;
    private Map<String,CacheBuilder> externalCacheBuilders;

    public static final DefaultCacheService getDefaultCacheService(CacheConfigMap cacheConfigMap,Map<String,CacheBuilder> localCacheBuilders,Map<String,CacheBuilder> externalCacheBuilders){
        if(DEFAULT_CACHE_SERVICE != null){
            return null;
        }
        synchronized (DefaultCacheService.class){
            if(DEFAULT_CACHE_SERVICE != null){
                return null;
            }
            DEFAULT_CACHE_SERVICE = new DefaultCacheService(cacheConfigMap,localCacheBuilders,externalCacheBuilders);
            return DEFAULT_CACHE_SERVICE;
        }
    }

    public DefaultCacheService(CacheConfigMap cacheConfigMap,Map<String,CacheBuilder> localCacheBuilders,Map<String,CacheBuilder> externalCacheBuilders){
        this.cacheConfigMap = cacheConfigMap;
        this.localCacheBuilders = localCacheBuilders;
        this.externalCacheBuilders = externalCacheBuilders;
    }

    public Cache getCache(String area,String name){
        Objects.requireNonNull(area,"area not null");
        Objects.requireNonNull(name,"name not null");
        Cache cache= cacheManager.getCache(area,name);
        if(cache != null){
            return cache;
        }
        Objects.requireNonNull(cacheConfigMap,"cacheConfigMap is not null");
        CacheInvokeConfig cacheInvokeConfig = cacheConfigMap.getByCacheName(area, name);
        if (cacheInvokeConfig == null) {
            String message = "can't found @Cached definited with area=" + area + "   name=" + name;
            throw new CacheConfigException(message);
        }
        CachedAnnoConfig cachedAnnoConfig = cacheInvokeConfig.getCachedAnnoConfig();
        if(cachedAnnoConfig ==null){
            String message = "can't found @Cached definited with area=" + area + "   name=" + name;
            throw new CacheConfigException(message);
        }
        synchronized (this) {
            cache= cacheManager.getCache(area,name);
            if(cache != null){
                return cache;
            }
            return createCacheByCacheConfig(cachedAnnoConfig);
        }
    }

    private Cache createCacheByCacheConfig( CacheAnnoConfig cacheAnnoConfig){
        String area = cacheAnnoConfig.getArea();
        String cacheName = cacheAnnoConfig.getName();
        Cache cache;
        if(CacheType.LOCAL == cacheAnnoConfig.getCacheType()){
            cache = buildLocal(cacheAnnoConfig);
        } else if(CacheType.EXTERNAL == cacheAnnoConfig.getCacheType()){
            cache = buildExternal(cacheAnnoConfig);
        }else {
            cache = buildBoth(cacheAnnoConfig);
        }
        cacheManager.putCache(area,cacheName ,cache);
        return cache;
    }
    private Cache buildLocal(CacheAnnoConfig cacheAnnoConfig){
        String area = cacheAnnoConfig.getArea();
        Objects.requireNonNull(localCacheBuilders,"localCacheBuilders is null");
        CacheBuilder cacheBuilder = localCacheBuilders.get(area);
        Cache cache = cacheBuilder.buildCache();
        return cache;
    }
    private Cache buildExternal(CacheAnnoConfig cacheAnnoConfig){
        Objects.requireNonNull(externalCacheBuilders,"externalCacheBuilders is null");
        CacheBuilder cacheBuilder = externalCacheBuilders.get(cacheAnnoConfig.getArea());
        Cache cache = cacheBuilder.buildCache();
        return cache;
    }
    private Cache buildBoth(CacheAnnoConfig cacheAnnoConfig){
        Cache localCache = buildLocal(cacheAnnoConfig);
        Cache remoteCache = buildExternal(cacheAnnoConfig);
        MultiCacheBuilder multiCacheBuilder = MultiCacheBuilder.createMultiCacheBuider();
        Cache cache = multiCacheBuilder.addCaches(localCache,remoteCache).buildCache();
        return cache;
    }


}
