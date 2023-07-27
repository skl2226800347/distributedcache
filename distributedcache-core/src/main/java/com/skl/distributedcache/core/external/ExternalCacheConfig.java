package com.skl.distributedcache.core.external;

import com.skl.distributedcache.core.config.CacheConfig;
import com.skl.distributedcache.core.support.DecoderMap;
import com.skl.distributedcache.core.support.JavaValueEncoder;

import java.util.function.Function;

public class ExternalCacheConfig<K,V> extends CacheConfig<K,V> {
    private String keyPrefix;
    private Function<Object,byte[]> valueEncoder = JavaValueEncoder.INSTANCE;
    private Function<byte[],Object> valueDecoder = DecoderMap.defaultJavaDecoder();


    public Function<Object, byte[]> getValueEncoder() {
        return valueEncoder;
    }

    public void setValueEncoder(Function<Object, byte[]> valueEncoder) {
        this.valueEncoder = valueEncoder;
    }

    public Function<byte[], Object> getValueDecoder() {
        return valueDecoder;
    }

    public void setValueDecoder(Function<byte[], Object> valueDecoder) {
        this.valueDecoder = valueDecoder;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
