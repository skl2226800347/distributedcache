package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.anno.method.CacheContext;
import com.skl.distributedcache.anno.method.SpringCacheContext;
import com.skl.distributedcache.core.support.JavaValueDecoder;
import com.skl.distributedcache.core.support.SpringJavaValueDecoder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.function.Function;

public class SpringConfigProvider extends ConfigProvider implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public CacheContext newCacheContext(GlobalCacheConfig globalCacheConfig) {
        return new SpringCacheContext(globalCacheConfig,applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Function<Object, Object> parseKeyConvertor(String convertor) {
        return super.parseKeyConvertor(convertor);
    }

    @Override
    public Function<Object, byte[]> parseValueEncoder(String valueEncoder) {
        return super.parseValueEncoder(valueEncoder);
    }

    @Override
    public Function<byte[], Object> parseValueDecoder(String valueDecoder) {
        return super.parseValueDecoder(valueDecoder);
    }

    @Override
    JavaValueDecoder javaValueDecoder(boolean useIdentityNumber) {
        return new SpringJavaValueDecoder(useIdentityNumber);
    }
}
