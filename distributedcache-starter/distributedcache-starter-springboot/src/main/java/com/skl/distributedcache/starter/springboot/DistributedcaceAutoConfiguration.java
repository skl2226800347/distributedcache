package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.anno.support.GlobalCacheConfig;
import com.skl.distributedcache.anno.support.SpringConfigProvider;
import com.skl.distributedcache.starter.springboot.remoting.zookeeper.CuratorZookeeperAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(DistributedcaceAutoConfiguration.class)
@ConditionalOnMissingBean(DistributedcaceAutoConfiguration.class)
@EnableConfigurationProperties(DistributecacheProperties.class)
@Import({
        LinkedHashMapAutoConfiguration.class,
        CaffeineAutoConfiguration.class,
        GuavaAutoConfiguration.class,
        RedisAutoConfiguration.class,
        CuratorZookeeperAutoConfiguration.class})
public class DistributedcaceAutoConfiguration {
    public static final String GLOBAL_CACHE_CONFIG_NAME="globalCacheConfig";

    private SpringConfigProvider springConfigProvider = new SpringConfigProvider();
    private AutoConfigureBeans autoConfigureBeans = new AutoConfigureBeans();


    @Bean
    public SpringConfigProvider springConfigProvider(){
        return springConfigProvider;
    }

    @Bean
    public AutoConfigureBeans autoConfigureBeans(){
        return autoConfigureBeans;
    }

    @Bean(name = GLOBAL_CACHE_CONFIG_NAME)
    @ConditionalOnClass
    public GlobalCacheConfig globalCacheConfig(SpringConfigProvider springConfigProvider, AutoConfigureBeans
                                               autoConfigurationBeans, DistributecacheProperties distributecacheProperties){
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setConfigProvider(springConfigProvider);
        globalCacheConfig.setExternalCacheBuilders(autoConfigurationBeans.getExternalCacheBuilders());
        globalCacheConfig.setLocalCacheBuilders(autoConfigurationBeans.getLocalCacheBuilders());
        globalCacheConfig.setClientBuilders(autoConfigurationBeans.getClientBuilders());
        globalCacheConfig.setClientBuilder(autoConfigurationBeans.getClientBuilder());
        globalCacheConfig.setClient(autoConfigurationBeans.getClient());
        return globalCacheConfig;
    }

}
