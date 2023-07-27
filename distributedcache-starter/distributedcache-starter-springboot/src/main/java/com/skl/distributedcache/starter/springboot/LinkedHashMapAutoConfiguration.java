package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.local.LinkedHashMapCacheBuilder;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(LinkedHashMapAutoConfiguration.LinkedHashMapCondition.class)
public class LinkedHashMapAutoConfiguration extends AbstractLocalCacheAutoInit{
    public static final String CACHE_TYPE = "linkedhashmap";

    public LinkedHashMapAutoConfiguration(){
        super(CACHE_TYPE);
    }
    @Override
    protected CacheBuilder initCacheBuilder(ConfigTree configTree, String cacheAreaWithPrefix) {
        LinkedHashMapCacheBuilder linkedHashMapCacheBuilder = LinkedHashMapCacheBuilder.createCacheBuilder();
        parseGenernalCacheConfig(configTree,linkedHashMapCacheBuilder);
        return linkedHashMapCacheBuilder;
    }

    public static class LinkedHashMapCondition extends DistributecacheCondition{
        public LinkedHashMapCondition(){
            super(CACHE_TYPE);
        }


    }



}
