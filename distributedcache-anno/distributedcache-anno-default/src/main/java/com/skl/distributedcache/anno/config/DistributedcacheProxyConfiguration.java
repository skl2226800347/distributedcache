package com.skl.distributedcache.anno.config;

import com.skl.distributedcache.anno.aop.CacheAdvisor;
import com.skl.distributedcache.anno.aop.DistributedcacheInterceptor;
import com.skl.distributedcache.anno.support.CacheConfigMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class DistributedcacheProxyConfiguration implements ImportAware, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private AnnotationAttributes enableMethodCache;

    public static final String CACHE_CONFIG_NAME="cacheConfigMap";
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        this.enableMethodCache =AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableMethodCache.class.getName(),false));
        if(this.enableMethodCache == null) {
            throw new IllegalArgumentException("annotationAttributes is null");
        }
    }
    @Bean(name = CACHE_CONFIG_NAME)
    public CacheConfigMap cacheConfigMap(){
        CacheConfigMap cacheConfigMap = new CacheConfigMap();
        return cacheConfigMap;
    }
    @Bean(name=CacheAdvisor.CACHE_ADVISOR_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAdvisor initCacheAdvisor(CacheConfigMap cacheConfigMap){

        DistributedcacheInterceptor distributedcacheInterceptor = new DistributedcacheInterceptor();
        distributedcacheInterceptor.setApplicationContext(this.applicationContext);
        distributedcacheInterceptor.setCacheConfigMap(cacheConfigMap);

        CacheAdvisor cacheAdvisor = new CacheAdvisor();
        cacheAdvisor.setCacheConfigMap(cacheConfigMap);
        cacheAdvisor.setAdviceBeanName(CacheAdvisor.CACHE_ADVISOR_NAME);
        cacheAdvisor.setAdvice(distributedcacheInterceptor);
        cacheAdvisor.setBasePackages(enableMethodCache.getStringArray("basePackages"));
        cacheAdvisor.setOrder(enableMethodCache.<Integer>getNumber("order"));
        return cacheAdvisor;
    }
}
