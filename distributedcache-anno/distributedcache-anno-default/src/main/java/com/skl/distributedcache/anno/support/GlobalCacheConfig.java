package com.skl.distributedcache.anno.support;

import com.skl.distributedcache.anno.method.CacheContext;
import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.remoting.api.Client;
import com.skl.distributedcache.remoting.api.ClientBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

public class GlobalCacheConfig implements InitializingBean, DisposableBean {


    private ConfigProvider configProvider;
    private Map<String, CacheBuilder> localCacheBuilders;
    private Map<String,CacheBuilder>  externalCacheBuilders;
    private Map<String, ClientBuilder> clientBuilders;
    private ClientBuilder clientBuilder;
    private Client client;
    private CacheContext cacheContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(cacheContext  == null){
            cacheContext =configProvider.newCacheContext(this);
            cacheContext.init();
        }
    }

    @Override
    public void destroy() throws Exception {

    }

    public ConfigProvider getConfigProvider() {
        return configProvider;
    }

    public void setConfigProvider(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    public Map<String, CacheBuilder> getLocalCacheBuilders() {
        return localCacheBuilders;
    }

    public void setLocalCacheBuilders(Map<String, CacheBuilder> localCacheBuilders) {
        this.localCacheBuilders = localCacheBuilders;
    }

    public Map<String, CacheBuilder> getExternalCacheBuilders() {
        return externalCacheBuilders;
    }

    public void setExternalCacheBuilders(Map<String, CacheBuilder> externalCacheBuilders) {
        this.externalCacheBuilders = externalCacheBuilders;
    }

    public CacheContext getCacheContext() {
        return cacheContext;
    }

    public Map<String, ClientBuilder> getClientBuilders() {
        return clientBuilders;
    }

    public void setClientBuilders(Map<String, ClientBuilder> clientBuilders) {
        this.clientBuilders = clientBuilders;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientBuilder getClientBuilder() {
        return clientBuilder;
    }

    public void setClientBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }
}
