package com.skl.distributedcache.core;

public interface CacheBuilder {
    <K,V> Cache<K,V> buildCache();
}
