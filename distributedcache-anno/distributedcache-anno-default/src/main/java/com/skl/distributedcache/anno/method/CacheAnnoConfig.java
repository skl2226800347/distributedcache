package com.skl.distributedcache.anno.method;

import com.skl.distributedcache.anno.api.CacheType;
import com.skl.distributedcache.core.Cache;

import java.lang.reflect.Method;
import java.util.function.Function;

public class CacheAnnoConfig {
    private Cache<?,?> cache;
    private String key;
    private String area;
    private String name;
    private CacheType cacheType;
    private Method defineMethod;

    private Function<CacheInvokeContext,Object> keyEvaluator;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cache<?, ?> getCache() {
        return cache;
    }

    public void setCache(Cache<?, ?> cache) {
        this.cache = cache;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

    public Method getDefineMethod() {
        return defineMethod;
    }

    public void setDefineMethod(Method defineMethod) {
        this.defineMethod = defineMethod;
    }

    public Function<CacheInvokeContext, Object> getKeyEvaluator() {
        return keyEvaluator;
    }

    public void setKeyEvaluator(Function<CacheInvokeContext, Object> keyEvaluator) {
        this.keyEvaluator = keyEvaluator;
    }
}
