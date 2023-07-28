package com.skl.distributedcache.core.local;

import com.skl.distributedcache.anno.api.CacheConstants;
import com.skl.distributedcache.core.config.CacheConfig;

public class LocalCacheConfig<K,V> extends CacheConfig<K,V> {
    private int limit = CacheConstants.DEFAULT_LOCAL_LIMIT ;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
