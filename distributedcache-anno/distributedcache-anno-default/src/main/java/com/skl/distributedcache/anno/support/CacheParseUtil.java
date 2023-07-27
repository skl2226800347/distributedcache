package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.anno.api.CacheInvalidate;
import com.skl.distributedcache.anno.api.CacheUpdate;
import com.skl.distributedcache.anno.api.Cached;
import com.skl.distributedcache.anno.method.CacheInvalidateAnnoConfig;
import com.skl.distributedcache.anno.method.CacheUpdateAnnoConfig;
import com.skl.distributedcache.anno.method.CachedAnnoConfig;
import com.skl.distributedcache.anno.method.CacheInvokeConfig;

import java.lang.reflect.Method;

public class CacheParseUtil {
    public static final boolean parse(CacheInvokeConfig cacheInvokeConfig, Method method){
        CachedAnnoConfig cachedAnnoConfig =  parseCached(method);
        boolean parseFlag = false;
        if (cachedAnnoConfig != null) {
            cacheInvokeConfig.setCachedAnnoConfig(cachedAnnoConfig);
            parseFlag = true;
        }

        CacheUpdateAnnoConfig cacheUpdateAnnoConfig = parseCacheUpdate(method);
        if(cacheUpdateAnnoConfig != null){
            cacheInvokeConfig.setCacheUpdateAnnoConfig(cacheUpdateAnnoConfig);
        }

        CacheInvalidateAnnoConfig cacheInvalidateAnnoConfig = parseCacheInvalidate(method);
        if (cacheInvalidateAnnoConfig != null){
            cacheInvokeConfig.setCacheInvalidateAnnoConfig(cacheInvalidateAnnoConfig);
        }

        return parseFlag;
    }

    protected static final CacheInvalidateAnnoConfig parseCacheInvalidate(Method method){
        CacheInvalidate cacheInvalidate = method.getAnnotation(CacheInvalidate.class);
        if (cacheInvalidate == null){
            return null;
        }
        String area = cacheInvalidate.area();
        String name = cacheInvalidate.name();
        String key = cacheInvalidate.key();
        CacheInvalidateAnnoConfig cacheInvalidateAnnoConfig = new CacheInvalidateAnnoConfig();
        cacheInvalidateAnnoConfig.setArea(area);
        cacheInvalidateAnnoConfig.setName(name);
        cacheInvalidateAnnoConfig.setKey(key);
        cacheInvalidateAnnoConfig.setDefineMethod(method);
        return cacheInvalidateAnnoConfig;
    }

    protected static final CacheUpdateAnnoConfig parseCacheUpdate(Method method){
        CacheUpdate cacheUpdate = method.getAnnotation(CacheUpdate.class);
        if(cacheUpdate == null){
            return null;
        }
        String area = cacheUpdate.area();
        String name = cacheUpdate.name();
        String key = cacheUpdate.key();
        String value = cacheUpdate.value();
        CacheUpdateAnnoConfig cacheUpdateAnnoConfig = new CacheUpdateAnnoConfig();
        cacheUpdateAnnoConfig.setArea(area);
        cacheUpdateAnnoConfig.setName(name);
        cacheUpdateAnnoConfig.setKey(key);
        cacheUpdateAnnoConfig.setValue(value);
        cacheUpdateAnnoConfig.setDefineMethod(method);
        return cacheUpdateAnnoConfig;
    }
    public static final CachedAnnoConfig parseCached(Method method){
        Cached cached = method.getAnnotation(Cached.class);
        if(cached == null) {
            return null;
        }

        String area = cached.area();
        CachedAnnoConfig cacheAnnoConfig = new CachedAnnoConfig();
        cacheAnnoConfig.setArea(area);
        cacheAnnoConfig.setName(cached.name());
        cacheAnnoConfig.setCacheType(cached.cacheType());
        cacheAnnoConfig.setKey(cached.key());
        cacheAnnoConfig.setDefineMethod(method);
        cacheAnnoConfig.setKeyConvertor(cached.keyConvertor());
        cacheAnnoConfig.setSerialPolicy(cached.serialPolicy());
        cacheAnnoConfig.setExpire(cached.expire());
        cacheAnnoConfig.setTimeUnit(cached.timeUnit());
        return cacheAnnoConfig;
    }
}
