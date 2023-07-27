package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.remoting.api.Client;
import com.skl.distributedcache.remoting.api.ClientBuilder;

import java.util.HashMap;
import java.util.Map;

public class AutoConfigureBeans {
    private Map<String, CacheBuilder> localCacheBuilders = new HashMap<>();
    private Map<String,CacheBuilder>  externalCacheBuilders = new HashMap<>();
    private Map<String, ClientBuilder> clientBuilders = new HashMap<>();
    private ClientBuilder clientBuilder;
    private Client client;

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

    public Map<String, ClientBuilder> getClientBuilders() {
        return clientBuilders;
    }

    public void setClientBuilders(Map<String, ClientBuilder> clientBuilders) {
        this.clientBuilders = clientBuilders;
    }

    public ClientBuilder getClientBuilder() {
        return clientBuilder;
    }

    public void setClientBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
