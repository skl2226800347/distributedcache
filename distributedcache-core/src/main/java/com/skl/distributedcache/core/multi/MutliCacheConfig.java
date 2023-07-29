package com.skl.distributedcache.core.multi;

import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.config.CacheConfig;

import java.util.ArrayList;
import java.util.List;

public class MutliCacheConfig extends CacheConfig {
    private List<Cache> caches = new ArrayList<>();

    public List<Cache> getCaches() {
        return caches;
    }


}
