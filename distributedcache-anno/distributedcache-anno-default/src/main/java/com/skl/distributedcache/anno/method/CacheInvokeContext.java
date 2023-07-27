package com.skl.distributedcache.anno.method;

import com.skl.distributedcache.anno.support.CacheConfigMap;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.remoting.api.Client;
import com.skl.distributedcache.remoting.api.ClientBuilder;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

public class CacheInvokeContext {
    private BiFunction<CacheInvokeContext, CacheAnnoConfig, Cache> cacheFunction;
    private Client client;
    private ClientBuilder clientBuilder;
    CacheInvokeConfig cacheInvokeConfig;
    private Object[] args;
    private Object result;
    private Method method;
    private Object targetObject;
    private Invoker invoker;
    private CacheConfigMap cacheConfigMap;

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

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


    public CacheInvokeConfig getCacheInvokeConfig() {
        return cacheInvokeConfig;
    }

    public void setCacheInvokeConfig(CacheInvokeConfig cacheInvokeConfig) {
        this.cacheInvokeConfig = cacheInvokeConfig;
    }

    public BiFunction<CacheInvokeContext, CacheAnnoConfig, Cache> getCacheFunction() {
        return cacheFunction;
    }

    public void setCacheFunction(BiFunction<CacheInvokeContext, CacheAnnoConfig, Cache> cacheFunction) {
        this.cacheFunction = cacheFunction;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public CacheConfigMap getCacheConfigMap() {
        return cacheConfigMap;
    }

    public void setCacheConfigMap(CacheConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }
}
