package com.skl.distributedcache.anno.method;

import org.springframework.context.ApplicationContext;

public class SpringCacheInvokeContext extends CacheInvokeContext{
    private ApplicationContext applicationContext ;
    public SpringCacheInvokeContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
}
