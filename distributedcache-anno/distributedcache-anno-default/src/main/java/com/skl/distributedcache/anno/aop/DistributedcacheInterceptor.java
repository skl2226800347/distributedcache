package com.skl.distributedcache.anno.aop;

import com.skl.distributedcache.anno.method.CacheHandler;
import com.skl.distributedcache.anno.method.CacheInvokeConfig;
import com.skl.distributedcache.anno.method.CacheInvokeContext;
import com.skl.distributedcache.anno.support.CacheConfigMap;
import com.skl.distributedcache.anno.support.GlobalCacheConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DistributedcacheInterceptor implements MethodInterceptor ,ApplicationContextAware {
    private ApplicationContext applicationContext;
    private CacheConfigMap cacheConfigMap;
    private GlobalCacheConfig globalCacheConfig;
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if(globalCacheConfig == null) {
            globalCacheConfig = applicationContext.getBean(GlobalCacheConfig.class);
        }
        if(globalCacheConfig == null){
            return methodInvocation.proceed();
        }
        Object thisObject = methodInvocation.getThis();
        String key = CachePointcut.getKey(methodInvocation.getMethod(),thisObject.getClass());
        CacheInvokeConfig cacheInvokeConfig = cacheConfigMap.getByMethodInfo(key);
        if (cacheInvokeConfig == null) {
            return methodInvocation.proceed();
        }
        Object[] arguments = methodInvocation.getArguments();
        GlobalCacheConfig globalCacheConfig = (GlobalCacheConfig)applicationContext.getBean("globalCacheConfig");
        CacheInvokeContext cacheInvokeContext =  globalCacheConfig.getCacheContext().createCacheInvokeContext(cacheConfigMap);
        cacheInvokeContext.setClient(globalCacheConfig.getClient());
        cacheInvokeContext.setCacheInvokeConfig(cacheInvokeConfig);
        cacheInvokeContext.setArgs(arguments);
        cacheInvokeContext.setMethod(methodInvocation.getMethod());
        cacheInvokeContext.setInvoker(methodInvocation::proceed);
        cacheInvokeContext.setClientBuilder(globalCacheConfig.getClientBuilder());
        cacheInvokeContext.setClient(globalCacheConfig.getClient());
        cacheInvokeContext.setCacheConfigMap(cacheConfigMap);
        return CacheHandler.invoke(cacheInvokeContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setCacheConfigMap(CacheConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }
}
