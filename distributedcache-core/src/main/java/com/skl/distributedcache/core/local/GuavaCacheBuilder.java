package com.skl.distributedcache.core.local;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.config.CacheConfig;
import java.util.function.Function;

public class GuavaCacheBuilder<T extends AbstractLocalCacheBuilder<T>> extends AbstractLocalCacheBuilder<T> {
    public static class GuavaCacheBuilderImpl extends GuavaCacheBuilder<GuavaCacheBuilderImpl>{

    }

    public static GuavaCacheBuilder createGuavaCacheBuilder() {
        GuavaCacheBuilderImpl builder = new GuavaCacheBuilderImpl();
        builder.buildFunc(c->{
            return new GuavaCache((LocalCacheConfig)c);
        });
        return builder;
    }
}
