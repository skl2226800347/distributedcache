package com.skl.distributedcache.anno.method;

import com.skl.distributedcache.anno.api.CacheType;
import com.skl.distributedcache.anno.support.DefaultCacheManager;
import com.skl.distributedcache.anno.support.DefaultCacheService;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.exception.CacheInvokeException;
import com.skl.distributedcache.core.loader.CacheLoader;
import com.skl.distributedcache.core.utils.CollectionUtil;
import com.skl.distributedcache.remoting.api.AbstractClientBuilder;
import com.skl.distributedcache.remoting.api.Client;
import com.skl.distributedcache.remoting.api.ClientBuilder;
import com.skl.distributedcache.remoting.api.DataEventType;
import com.skl.distributedcache.remoting.api.param.RemotingParam;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class CacheHandler implements InvocationHandler {
    public static final Object invoke( CacheInvokeContext cacheInvokeContext){
        return doInvoke(cacheInvokeContext);
    }

    public static Object doInvoke(CacheInvokeContext cacheInvokeContext){
        CacheInvokeConfig cacheInvokeConfig = cacheInvokeContext.getCacheInvokeConfig();
        if(cacheInvokeConfig.getCachedAnnoConfig() != null){
            return invokeWithCached(cacheInvokeContext);
        } else if(cacheInvokeConfig.getCacheUpdateAnnoConfig() != null || cacheInvokeConfig.getCacheInvalidateAnnoConfig() != null){
            return invokeUpdateOrInvalidateCache(cacheInvokeContext);
        }else {
            return invokeOrigin(cacheInvokeContext);
        }
    }
    public static Object invokeWithCached(CacheInvokeContext invokeContext){
        CacheInvokeConfig cacheInvokeConfig = invokeContext.getCacheInvokeConfig();
        CachedAnnoConfig cachedAnnoConfig = cacheInvokeConfig.getCachedAnnoConfig();
        Cache cache = invokeContext.getCacheFunction().apply(invokeContext,cachedAnnoConfig);
        Object key = ExpressionUtil.evalKey(cachedAnnoConfig,invokeContext);
        if(key == null){
            return invokeOrigin(invokeContext);
        }
        CacheLoader loader = new CacheLoader() {
            @Override
            public Object load(Object key) throws Throwable {
                return invokeOrigin(invokeContext);
            }
        };
        return cache.computeIfAbsent(key,loader);
    }
    public static Object invokeUpdateOrInvalidateCache(CacheInvokeContext cacheInvokeContext){
        Object result = invokeOrigin(cacheInvokeContext);
        cacheInvokeContext.setResult(result);

        CacheInvokeConfig cacheInvokeConfig = cacheInvokeContext.getCacheInvokeConfig();
        if(cacheInvokeConfig.getCacheUpdateAnnoConfig() != null){
            doUpdate(cacheInvokeContext,cacheInvokeConfig);
        }

        if(cacheInvokeConfig.getCacheInvalidateAnnoConfig() != null) {
            doInvalidate(cacheInvokeContext,cacheInvokeConfig);
        }
        return result;
    }
    public static void doUpdate(CacheInvokeContext cacheInvokeContext,CacheInvokeConfig cacheInvokeConfig){
        CacheUpdateAnnoConfig cacheUpdateAnnoConfig = cacheInvokeConfig.getCacheUpdateAnnoConfig();
        Cache cache = cacheInvokeContext.getCacheFunction().apply(cacheInvokeContext,cacheUpdateAnnoConfig);
        if(cache == null){
            return;
        }
        Object key = ExpressionUtil.evalKey(cacheUpdateAnnoConfig,cacheInvokeContext);
        Object value = ExpressionUtil.evalValue(cacheUpdateAnnoConfig,cacheInvokeContext);
        if(key == null || value == ExpressionUtil.EVAL_FAILURE){
            return;
        }
        cache.put(key,value);
        notify(key,value,cacheInvokeContext,cacheUpdateAnnoConfig);
    }
    public static void doInvalidate(CacheInvokeContext cacheInvokeContext,CacheInvokeConfig cacheInvokeConfig) {
        CacheInvalidateAnnoConfig cacheInvalidateAnnoConfig = cacheInvokeConfig.getCacheInvalidateAnnoConfig();

        Cache cache = cacheInvokeContext.getCacheFunction().apply(cacheInvokeContext,cacheInvalidateAnnoConfig);

        if (cache == null){
            return;
        }
        Object key = ExpressionUtil.evalKey(cacheInvalidateAnnoConfig,cacheInvokeContext);
        if(key == null){
            return;
        }
        cache.remove(key);
        notify(key,null,cacheInvokeContext,cacheInvalidateAnnoConfig);
    }
    private static void notify(Object key,Object value,CacheInvokeContext cacheInvokeContext,CacheAnnoConfig cacheAnnoConfig){
        if(cacheInvokeContext.getClientBuilder() == null){
            return;
        }

        CacheInvokeConfig cacheInvokeConfig = cacheInvokeContext.getCacheConfigMap().getByCacheName(cacheAnnoConfig.getArea(),cacheAnnoConfig.getName());
        if(cacheInvokeConfig == null || cacheInvokeConfig.getCachedAnnoConfig() == null) {
            return;
        }

        if(CacheType.EXTERNAL ==cacheInvokeConfig.getCachedAnnoConfig().getCacheType()){
            return;
        }
        AbstractClientBuilder clientBuilder = (AbstractClientBuilder)cacheInvokeContext.getClientBuilder();
        List<String> subscribePathList = clientBuilder.subscribePaths();
        if(CollectionUtil.isEmpty(subscribePathList)){
            return;
        }
        Client client = clientBuilder.builderClient();
        RemotingParam remotingParam = new RemotingParam();
        remotingParam.setArea(cacheAnnoConfig.getArea());
        remotingParam.setCacheName(cacheAnnoConfig.getName());
        if(cacheAnnoConfig instanceof CacheUpdateAnnoConfig){
            remotingParam.setDataEventType(DataEventType.UPDATE.getType());
            remotingParam.setKey(key);
            remotingParam.setValue(value);
        } else if (cacheAnnoConfig instanceof CacheInvalidateAnnoConfig) {
            remotingParam.setDataEventType(DataEventType.INVLIDATE.getType());
            remotingParam.setKey(key);
        }else{
            return;
        }
        for(String path:subscribePathList){
            client.publish(path,remotingParam);
        }
    }
    public static Object invokeOrigin(CacheInvokeContext cacheInvokeContext){
        try {
            return cacheInvokeContext.getInvoker().invoke();
        }catch (Throwable t){
            throw new CacheInvokeException(t.getMessage());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
