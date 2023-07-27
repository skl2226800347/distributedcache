package com.skl.distributedcache.core.local;

public interface InnerMap {
    Object getObject(Object key);

    void putObject(Object key,Object value);

    void removeObject(Object key);
}
