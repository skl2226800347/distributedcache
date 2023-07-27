package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.anno.api.KeyConvertor;
import com.skl.distributedcache.anno.api.SerialPolicy;
import com.skl.distributedcache.anno.method.CacheContext;
import com.skl.distributedcache.core.exception.CacheConfigException;
import com.skl.distributedcache.core.support.FastjsonKeyConvertor;
import com.skl.distributedcache.core.support.JavaValueDecoder;
import com.skl.distributedcache.core.support.JavaValueEncoder;

import java.util.function.Function;

public class ConfigProvider {
    public CacheContext newCacheContext(GlobalCacheConfig globalCacheConfig){
        return new CacheContext(globalCacheConfig);
    }

    public Function<Object,Object> parseKeyConvertor(String convertor){
        if(convertor == null){
            return null;
        }
        if(KeyConvertor.FASTJSON.equalsIgnoreCase(convertor)){
            return FastjsonKeyConvertor.INSTANCE;
        }else if (KeyConvertor.NONE.equalsIgnoreCase(convertor)){
            return null;
        }
        throw new CacheConfigException("not supported:"+convertor);
    }

    public Function<Object,byte[]> parseValueEncoder(String valueEncoder){
        if(valueEncoder == null){
            throw new CacheConfigException("no serialPolicy");
        }
        if (SerialPolicy.JAVA.equalsIgnoreCase(valueEncoder)){
            return new JavaValueEncoder(true);
        }else {
            throw new CacheConfigException("not supporty:"+valueEncoder);
        }
    }

    public Function<byte[],Object> parseValueDecoder(String valueDecoder){
        if(valueDecoder == null){
            throw new CacheConfigException("no serialPolicy");
        }
        if(SerialPolicy.JAVA.equalsIgnoreCase(valueDecoder)){
            return javaValueDecoder(true);
        }else {
            throw new CacheConfigException("not suppor:"+valueDecoder);
        }
    }

    JavaValueDecoder javaValueDecoder(boolean useIdentityNumber){
        return new JavaValueDecoder(useIdentityNumber);
    }
}
