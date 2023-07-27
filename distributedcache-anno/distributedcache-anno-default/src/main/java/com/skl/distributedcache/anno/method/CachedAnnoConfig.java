package com.skl.distributedcache.anno.method;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CachedAnnoConfig extends CacheAnnoConfig{
    private String keyConvertor;
    private String serialPolicy;
    private long expire;
    private TimeUnit timeUnit;

    public String getKeyConvertor() {
        return keyConvertor;
    }

    public void setKeyConvertor(String keyConvertor) {
        this.keyConvertor = keyConvertor;
    }

    public String getSerialPolicy() {
        return serialPolicy;
    }

    public void setSerialPolicy(String serialPolicy) {
        this.serialPolicy = serialPolicy;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
