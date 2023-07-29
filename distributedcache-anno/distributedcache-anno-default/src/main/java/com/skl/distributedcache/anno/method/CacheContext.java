package com.skl.distributedcache.anno.method;

import com.skl.distributedcache.anno.api.CacheConstants;
import com.skl.distributedcache.anno.api.CacheType;
import com.skl.distributedcache.anno.support.CacheConfigMap;
import com.skl.distributedcache.anno.support.CacheManager;
import com.skl.distributedcache.anno.support.GlobalCacheConfig;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.exception.CacheConfigException;
import com.skl.distributedcache.core.external.AbstractExternalCacheBuilder;
import com.skl.distributedcache.core.multi.MultiCacheBuilder;
import com.skl.distributedcache.core.utils.StringUtils;

import java.util.concurrent.TimeUnit;

public class CacheContext {
    private CacheManager cacheManager = CacheManager.getDefaultCacheManager();
    private GlobalCacheConfig globalCacheConfig;
    private boolean init;


    public void init(){
        if(!init){
            init = true;
        }
    }

    public CacheContext(GlobalCacheConfig globalCacheConfig){
        this.globalCacheConfig = globalCacheConfig;
    }

    public CacheInvokeContext createCacheInvokeContext(CacheConfigMap cacheConfigMap){
        CacheInvokeContext resultCacheInvokeContext = newCacheInvokeContext();
        resultCacheInvokeContext.setCacheFunction((cacheInvokeContext,cacheAnnoConfig)->{
            if(cacheAnnoConfig.getCache() != null){
                return cacheAnnoConfig.getCache();
            }
            Cache cache = null;
            if(cacheAnnoConfig instanceof CachedAnnoConfig){
                cache = createCacheByCacheConfig(cacheInvokeContext,(CachedAnnoConfig)cacheAnnoConfig);
            } else if ((cacheAnnoConfig instanceof CacheUpdateAnnoConfig) || (cacheAnnoConfig instanceof CacheInvalidateAnnoConfig)){
                CacheInvokeConfig cacheInvokeConfig = cacheConfigMap.getByCacheName(cacheAnnoConfig.getArea(),cacheAnnoConfig.getName());
                if(cacheInvokeConfig == null){
                    String message="can't found @Cached definited with area="+cacheAnnoConfig.getArea()+"   name="+cacheAnnoConfig.getKey()
                            +"  specified in "+cacheAnnoConfig.getDefineMethod();
                    throw new CacheConfigException(message);
                }
                cache=createCacheByCacheConfig(cacheInvokeContext,cacheInvokeConfig.getCachedAnnoConfig());
            }
            cacheAnnoConfig.setCache(cache);
            return cache;
        });
        return resultCacheInvokeContext;
    }

    protected Cache createCacheByCacheConfig(CacheInvokeContext cacheInvokeContext,CachedAnnoConfig cachedAnnoConfig){
        String area = cachedAnnoConfig.getArea();
        String cacheName = cachedAnnoConfig.getName();
        Cache cache = cacheManager.getCache(area,cacheName);
        if (cache != null){
            return cache;
        }
        synchronized (this){
            cache = cacheManager.getCache(area,cacheName);
            if(cache != null){
                return cache;
            }
            if(CacheType.LOCAL == cachedAnnoConfig.getCacheType()){
                cache = buildLocal(cachedAnnoConfig);
            } else if(CacheType.EXTERNAL == cachedAnnoConfig.getCacheType()){
                cache = buildExternal(cachedAnnoConfig,cacheName);
            }else {
                cache = buildBoth(cachedAnnoConfig,cacheName);
            }
            cacheManager.putCache(area,cacheName ,cache);
            return cache;
        }
    }
    private Cache buildLocal(CachedAnnoConfig cachedAnnoConfig){
        String area = cachedAnnoConfig.getArea();
        CacheBuilder cacheBuilder = globalCacheConfig.getLocalCacheBuilders().get(area);
        Cache cache = cacheBuilder.buildCache();
        return cache;
    }
    private Cache buildExternal(CachedAnnoConfig cachedAnnoConfig,String cacheName){
        AbstractExternalCacheBuilder cacheBuilder = (AbstractExternalCacheBuilder)globalCacheConfig.getExternalCacheBuilders().get(cachedAnnoConfig.getArea());


        if(cachedAnnoConfig.getExpire() > 0){
            cacheBuilder.expireAfterWriteInMillis(cachedAnnoConfig.getExpire(),cachedAnnoConfig.getTimeUnit());
        }

        if(cacheBuilder.getCacheConfig().getKeyPrefix() != null){
            cacheBuilder.keyPrefix(StringUtils.convertString(cacheBuilder.getCacheConfig().getKeyPrefix(),cacheName));
        } else {
            cacheBuilder.keyPrefix(cacheName);
        }

        if(!CacheConstants.isUndefined(cachedAnnoConfig.getKeyConvertor())){
            cacheBuilder.keyConvertor(globalCacheConfig.getConfigProvider().parseKeyConvertor(cachedAnnoConfig.getKeyConvertor()));
        }
        if(!CacheConstants.isUndefined(cachedAnnoConfig.getSerialPolicy())) {
            cacheBuilder.valueDecoder(globalCacheConfig.getConfigProvider().parseValueDecoder(cachedAnnoConfig.getSerialPolicy()));
            cacheBuilder.valueEncoder(globalCacheConfig.getConfigProvider().parseValueEncoder(cachedAnnoConfig.getSerialPolicy()));
        }
        Cache cache = cacheBuilder.buildCache();
        return cache;
    }
    private Cache buildBoth(CachedAnnoConfig cachedAnnoConfig,String cacheName){
        Cache localCache = buildLocal(cachedAnnoConfig);
        Cache remoteCache = buildExternal(cachedAnnoConfig,cacheName);
        Cache cache = MultiCacheBuilder.createMultiCacheBuider()
                .addCaches(localCache,remoteCache)
                .expireAfterWriteInMillis(remoteCache.getCacheConfig().getExpireAfterWriteInMillis(),TimeUnit.MILLISECONDS)
                .buildCache();
        return cache;
    }








    public CacheInvokeContext newCacheInvokeContext(){
        return new CacheInvokeContext();
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

}
