package com.skl.distributedcache.anno.aop;

import com.skl.distributedcache.anno.support.CacheConfigMap;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

public class CacheAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    public static final String CACHE_ADVISOR_NAME="distributedcacheAdvisor";
    private String[] basePackages;
    private CacheConfigMap cacheConfigMap;

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    @Override
    public Pointcut getPointcut() {
        CachePointcut cachePointcut = new CachePointcut(this.basePackages);
        cachePointcut.setCacheConfigMap(this.cacheConfigMap);
        return cachePointcut;
    }

    public void setCacheConfigMap(CacheConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }
}
