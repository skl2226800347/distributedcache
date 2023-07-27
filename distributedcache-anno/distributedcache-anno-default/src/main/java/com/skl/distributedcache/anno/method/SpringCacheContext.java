package com.skl.distributedcache.anno.method;

import com.skl.distributedcache.anno.support.GlobalCacheConfig;
import org.springframework.context.ApplicationContext;

public class SpringCacheContext extends CacheContext{
    private ApplicationContext applicationContext;
    public SpringCacheContext(GlobalCacheConfig globalCacheConfig,ApplicationContext applicationContext){
        super(globalCacheConfig);
        this.applicationContext = applicationContext;
    }

    @Override
    public CacheInvokeContext newCacheInvokeContext() {
        return new SpringCacheInvokeContext(applicationContext);
    }
}
