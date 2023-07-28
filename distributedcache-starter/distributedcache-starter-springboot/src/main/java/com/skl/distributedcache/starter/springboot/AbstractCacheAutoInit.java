package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.anno.support.ConfigProvider;
import com.skl.distributedcache.core.AbstractCacheBuilder;
import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.utils.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractCacheAutoInit implements InitializingBean {

    private String[] cacheTypes;
    private boolean inited = false;
    @Autowired
    protected ConfigurableEnvironment environment;
    @Autowired
    private AutoConfigureBeans autoConfigurationBeans;
    @Autowired
    protected ConfigProvider configProvider;

    public AbstractCacheAutoInit(String ...cacheTypes){
        Objects.requireNonNull(cacheTypes,"cacheTypes is null");
        Assert.isTrue(cacheTypes.length>0,"cacheTypes is not null");
        this.cacheTypes = cacheTypes;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(!inited){
            synchronized (this){
                process("distributecache.local.",autoConfigurationBeans.getLocalCacheBuilders(),true);
                process("distributecache.external.",autoConfigurationBeans.getExternalCacheBuilders(),false);
                inited = true;
            }
        }
    }
    protected void process(String prefix, Map<String,CacheBuilder> cacheBuilderMap,boolean isLocal){
        ConfigTree configTree = new ConfigTree(environment,prefix);
        Map<String,Object> properties = configTree.getProperties();
        Set<String> areaNamems = configTree.getDirectChildrenKeys();
        for(String area : areaNamems) {
            Object areaValue = properties.get(StringUtils.convertString(area,".type"));
            boolean match = Arrays.stream(cacheTypes).anyMatch(ct->ct.equals(areaValue));
            if(!match){
                continue;
            }
            ConfigTree subTree = configTree.subTree(area+".");
            String cacheAreaWithPrefix=(isLocal ? StringUtils.convertString("local.",area):StringUtils.convertString("remote.",area));
            CacheBuilder cacheBuilder = initCacheBuilder(subTree,cacheAreaWithPrefix);
            cacheBuilderMap.put(area,cacheBuilder);
        }
    }

    protected void parseGenernalCacheConfig(ConfigTree configTree,CacheBuilder cacheBuilder){
        AbstractCacheBuilder acb =(AbstractCacheBuilder) cacheBuilder;
        acb.keyConvertor(configProvider.parseKeyConvertor(configTree.getProperty("keyConvertor")));

        String expireAfterWriteInMillis = configTree.getProperty("expireAfterWriteInMillis");
        if(expireAfterWriteInMillis == null){
            expireAfterWriteInMillis=configTree.getProperty("defaultExpireInMillis");
        }
        if(expireAfterWriteInMillis != null){
            acb.expireAfterWriteInMillis(Long.valueOf(expireAfterWriteInMillis));
        }
        String expireAfterAccessInMillis= configTree.getProperty("expireAfterAccessInMillis");
        if(expireAfterAccessInMillis != null){
            acb.expireAfterAccessInMillis(Long.parseLong(expireAfterAccessInMillis));
        }
    }

    protected abstract CacheBuilder initCacheBuilder(ConfigTree configTree,String cacheAreaWithPrefix);
}
